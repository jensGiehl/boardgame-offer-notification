package de.agiehl.boardgame.BoardgameOffersFinder.notify.milan;

import de.agiehl.boardgame.BoardgameOffersFinder.notify.sender.Notifier;
import de.agiehl.boardgame.BoardgameOffersFinder.notify.sender.NotifyResponse;
import de.agiehl.boardgame.BoardgameOffersFinder.notify.sender.text.TextFormatter;
import de.agiehl.boardgame.BoardgameOffersFinder.persistent.bgg.BggEntity;
import de.agiehl.boardgame.BoardgameOffersFinder.persistent.bgg.BggPersistenceService;
import de.agiehl.boardgame.BoardgameOffersFinder.persistent.brettspielangebote.BrettspielAngebotePriceService;
import de.agiehl.boardgame.BoardgameOffersFinder.persistent.brettspielangebote.ComparisonPriceEntity;
import de.agiehl.boardgame.BoardgameOffersFinder.persistent.milan.MilanEntity;
import de.agiehl.boardgame.BoardgameOffersFinder.persistent.notify.NotifyEntity;
import de.agiehl.boardgame.BoardgameOffersFinder.persistent.notify.NotifyPersistenceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static de.agiehl.boardgame.BoardgameOffersFinder.persistent.EntityType.MILAN;

@Service
@Slf4j
@AllArgsConstructor
public class MilanNotifyImpl implements MilanNotify {

    private static final String NON_NUMBER_REGEX = "[^\\d,.]";

    private Notifier notifier;

    private MessageSource messageSource;

    private NotifyPersistenceService notifyPersistenceService;

    private BggPersistenceService bggPersistenceService;

    private BrettspielAngebotePriceService brettspielAngebotePriceService;

    @Override
    public void notify(MilanEntity entity) {
        String textToSend = getMessage(entity);

        NotifyResponse response;
        Optional<NotifyEntity> oldNotification = notifyPersistenceService.findNotification(entity.getId(), MILAN);
        if(oldNotification.isPresent()) {
            NotifyEntity oldNotifyEntity = oldNotification.get();
            log.info("Updating message {}", oldNotifyEntity);

            if (Objects.isNull(entity.getImgUrl())) {
                response = notifier.sendText(oldNotifyEntity, textToSend);
            } else {
                response = notifier.sendImage(oldNotifyEntity, entity.getImgUrl(), textToSend);
            }
        } else {
            Optional<ComparisonPriceEntity> comparisonPrice = brettspielAngebotePriceService.findByFkIdAndFkType(entity.getId(), MILAN);
            if (comparisonPrice.isEmpty() && LocalDateTime.now().isBefore(entity.getCreateDate().plusSeconds(30))) {
                log.debug("No comparison price found. Still holding message.");
                return;
            }

            if (comparisonPrice.isPresent()) {
                String price = comparisonPrice.get().getPrice();
                if (Objects.nonNull(price)) {
                    try {
                        Double comparisonPriceAsDouble = convertPrice(price);
                        Double priceAsDouble = convertPrice(entity.getPrice());
                        if (Double.compare(comparisonPriceAsDouble, priceAsDouble) < 0) {
                            log.info("Comparison ({}) price is lower than current price: {}", comparisonPriceAsDouble, priceAsDouble);
                            return;
                        }

                    } catch (Exception ex) {
                        log.warn("Couldn't compare prices", ex);
                    }
                }
            }

            NotifyEntity.MessageType messageType;

            if (Objects.isNull(entity.getImgUrl())) {
                response = notifier.sendText(textToSend);
                messageType = NotifyEntity.MessageType.TEXT;
            } else {
                response = notifier.sendImage(entity.getImgUrl(), textToSend);
                messageType = NotifyEntity.MessageType.IMAGE;
            }

            notifyPersistenceService.saveCommunication(response, entity.getId(), MILAN, messageType);
        }
    }

    private Double convertPrice(String priceStr) {
        String price = priceStr.replaceAll(NON_NUMBER_REGEX, "")
                .replace(',', '.');

        return Double.parseDouble(price);
    }

    private String getMessage(MilanEntity entity) {
        String priceText = messageSource.getMessage("price", null, LocaleContextHolder.getLocale());
        String stockText = messageSource.getMessage("stock", null, LocaleContextHolder.getLocale());
        String editNumberText = messageSource.getMessage("edit-number", null, LocaleContextHolder.getLocale());
        String linkText = messageSource.getMessage("milan.link", null, LocaleContextHolder.getLocale());
        String bggRatingText = messageSource.getMessage("bgg-rating", null, LocaleContextHolder.getLocale());
        String bggWishText = messageSource.getMessage("bgg-wish", null, LocaleContextHolder.getLocale());
        String bggWantText = messageSource.getMessage("bgg-want", null, LocaleContextHolder.getLocale());
        String comparisonPriceText = messageSource.getMessage("comparison-price", null, LocaleContextHolder.getLocale());

        Optional<BggEntity> bggEntity = bggPersistenceService.findByFkIdAndFkType(entity.getId(), MILAN);

        TextFormatter textFormatter = notifier.getTextFormatter()
                .bold(entity.getName())
                .newLine()
                .keyValue(priceText, entity.getPrice())
                .newLine()
                .keyValue(stockText, entity.getStockText());

        if (bggEntity.isPresent()) {
            BggEntity bgg = bggEntity.get();
            if (bgg.getBggRating() != null) {
                textFormatter.newLine()
                        .newLine()
                        .keyValueLink(bggRatingText, bgg.getBggRating(), bgg.getBggLink());
            }

            if (bgg.getWishing() != null) {
                textFormatter.newLine().keyValue(bggWishText, bgg.getWishing());
            }

            if (bgg.getWanting() != null) {
                textFormatter.newLine().keyValue(bggWantText, bgg.getWanting());
            }
        }

        Optional<ComparisonPriceEntity> comparisonPrice = brettspielAngebotePriceService.findByFkIdAndFkType(entity.getId(), MILAN);
        if (comparisonPrice.isPresent()) {
            ComparisonPriceEntity priceEntity = comparisonPrice.get();
            if (priceEntity.getPrice() != null && !priceEntity.getPrice().isBlank()) {
                textFormatter.newLine()
                        .newLine()
                        .keyValueLink(comparisonPriceText, priceEntity.getPrice(), priceEntity.getUrl());
            }
        }

        textFormatter
                .newLine()
                .newLine()
                .link(linkText, entity.getUrl())
                .newLine()
                .keyValue(editNumberText, RandomStringUtils.randomAlphanumeric(4)) // Telegram wants an update of the message...
                ;

        return textFormatter.getText();
    }


}
