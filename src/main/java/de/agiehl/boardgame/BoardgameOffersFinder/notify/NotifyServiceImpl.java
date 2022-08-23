package de.agiehl.boardgame.BoardgameOffersFinder.notify;

import de.agiehl.boardgame.BoardgameOffersFinder.notify.sender.Notifier;
import de.agiehl.boardgame.BoardgameOffersFinder.notify.sender.NotifyResponse;
import de.agiehl.boardgame.BoardgameOffersFinder.notify.sender.NotifyUpdateInformation;
import de.agiehl.boardgame.BoardgameOffersFinder.notify.sender.text.TextFormatter;
import de.agiehl.boardgame.BoardgameOffersFinder.persistent.DataEntity;
import de.agiehl.boardgame.BoardgameOffersFinder.persistent.PersistenceService;
import de.agiehl.boardgame.BoardgameOffersFinder.web.brettspielangebote.BrettspielAngeboteConfig;
import de.agiehl.boardgame.BoardgameOffersFinder.web.brettspielangebote.BrettspielAngeboteNameStrategy;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
@AllArgsConstructor
public class NotifyServiceImpl implements NotifyService {

    public static final String NON_NUMBER_REGEX = "[^\\d,.]";

    private Notifier notifier;

    private MessageSource messageSource;

    private PersistenceService persistenceService;

    private NotifyConfig config;

    private BrettspielAngeboteConfig bestPriceConfig;

    private BrettspielAngeboteNameStrategy bestPriceNameStrategy;

    @Override
    public void notify(DataEntity entity) {
        if (Boolean.FALSE.equals(config.getEnable())) {
            return;
        }

        if (entity.getNotificationFailCount() > config.getMaxFailCount()) {
            disableNotificationForEntity(entity);
            return;
        }

        if (hasNotTheCheapestPrice(entity)) {
            disableNotificationForEntity(entity);
            return;
        }

        NotifyResponse notifyResponse = null;

        try {
            notifyResponse = sendNotification(entity);
        } catch (Exception ex) {
            log.warn("Couldn't send notification", ex);
        }

        if (Objects.nonNull(notifyResponse)) {
            entity.setChatId(String.valueOf(notifyResponse.getChatId()));
            entity.setMessageId(String.valueOf(notifyResponse.getMessageId()));
            entity.setEnableNotification(false);
            persistenceService.saveNotificationInformation(entity);
        } else {
            persistenceService.increaseNotificationFailCount(entity);
        }
    }

    private void disableNotificationForEntity(DataEntity entity) {
        entity.setEnableNotification(false);
        persistenceService.saveNotificationInformation(entity);
    }

    private NotifyResponse sendNotification(DataEntity entity) {
        String textToSend = getMessage(entity);
        boolean hasImage = Objects.nonNull(entity.getImageUrl());

        NotifyResponse notifyResponse;
        if (Objects.nonNull(entity.getMessageId())) {
            NotifyUpdateInformation updateInformation = NotifyUpdateInformation.builder()
                    .chatId(entity.getChatId())
                    .messageId(entity.getMessageId())
                    .build();

            if (hasImage) {
                notifyResponse = notifier.updateImage(updateInformation, entity.getImageUrl(), textToSend);
            } else {
                notifyResponse = notifier.updateText(updateInformation, textToSend);
            }
        } else {
            if (hasImage) {
                notifyResponse = notifier.sendImage(entity.getImageUrl(), textToSend);
            } else {
                notifyResponse = notifier.sendText(textToSend);
            }
        }
        return notifyResponse;
    }

    private boolean hasNotTheCheapestPrice(DataEntity entity) {
        if (isPricePresent(entity)) {
            try {
                Double comparisonPriceAsDouble = convertPrice(entity.getBestPrice());
                Double priceAsDouble = convertPrice(entity.getPrice());
                if (Double.compare(comparisonPriceAsDouble, priceAsDouble) < 0) {
                    log.info("Comparison ({}) price is lower than current price: {}", comparisonPriceAsDouble, priceAsDouble);
                    return true;
                }
            } catch (Exception e) {
                log.warn("Couldn't compare prices", e);
            }
        }

        return false;
    }

    private boolean isPricePresent(DataEntity entity) {
        return Objects.nonNull(entity.getBestPrice()) &&
                !entity.getBestPrice().isBlank() &&
                Objects.nonNull(entity.getPrice()) &&
                !entity.getPrice().isBlank();
    }

    private Double convertPrice(String priceStr) {
        String price = priceStr.replaceAll(NON_NUMBER_REGEX, "")
                .replace(',', '.');

        return Double.parseDouble(price);
    }

    private String getMessage(DataEntity entity) {
        String priceText = messageSource.getMessage("price", null, LocaleContextHolder.getLocale());
        String stockText = messageSource.getMessage("stock", null, LocaleContextHolder.getLocale());
        String linkText = messageSource.getMessage(entity.getCrawlerName().name().toLowerCase() + ".link-text", null, LocaleContextHolder.getLocale());
        String bggRatingText = messageSource.getMessage("bgg-rating", null, LocaleContextHolder.getLocale());
        String bggWishText = messageSource.getMessage("bgg-wish", null, LocaleContextHolder.getLocale());
        String bggWantText = messageSource.getMessage("bgg-want", null, LocaleContextHolder.getLocale());
        String comparisonPriceText = messageSource.getMessage("comparison-price", null, LocaleContextHolder.getLocale());
        String noBestPriceText = messageSource.getMessage("best-price-not-found-text", null, LocaleContextHolder.getLocale());
        String noBestPriceLinkText = messageSource.getMessage("best-price-not-found-link-text", null, LocaleContextHolder.getLocale());
        String bggRank = messageSource.getMessage("bgg-rank", null, LocaleContextHolder.getLocale());

        TextFormatter textFormatter = notifier.getTextFormatter()
                .bold(entity.getName())
                .newLine();

        if (Objects.nonNull(entity.getDescription())) {
            textFormatter.
                    italic(entity.getDescription())
                    .newLine();
        }

        if (Objects.nonNull(entity.getPrice())) {
            textFormatter.keyValue(priceText, entity.getPrice())
                    .newLine();
        }

        if (Objects.nonNull(entity.getBestPrice())) {
            if (entity.getBestPrice().isBlank()) {
                textFormatter.keyValueLink(comparisonPriceText, noBestPriceLinkText, entity.getBestPriceUrl())
                        .newLine();
            } else {
                textFormatter
                        .keyValueLink(comparisonPriceText, entity.getBestPrice(), entity.getBestPriceUrl())
                        .newLine();
            }
        } else {
            if (entity.isBestPricePossible()) {
                textFormatter.normal(noBestPriceText)
                        .normal(" ")
                        .link(noBestPriceLinkText, bestPriceConfig.getSearchUrl(bestPriceNameStrategy.getNameForSearch(entity.getName())))
                        .newLine();
            }
        }

        if (Objects.nonNull(entity.getStockText())) {
            textFormatter.keyValue(stockText, entity.getStockText())
                    .newLine();
        }

        if (Objects.nonNull(entity.getBggId())) {
            textFormatter
                    .newLine()
                    .keyValueLink(bggRatingText, entity.getBggRating(), entity.getBggLink())
                    .normal(" (" + entity.getBggUserRated() + ")")
                    .newLine()
                    .keyValue(bggRank, entity.getBggRank())
                    .newLine()
                    .keyValue(bggWishText, entity.getBggWishing())
                    .newLine()
                    .keyValue(bggWantText, entity.getBggWanting())
                    .newLine();
        }

        textFormatter
                .newLine()
                .newLine()
                .link(linkText, entity.getUrl());

        return textFormatter.getText();
    }


}
