package de.agiehl.boardgame.BoardgameOffersFinder.notify.spieleoffensive;

import de.agiehl.boardgame.BoardgameOffersFinder.notify.sender.Notifier;
import de.agiehl.boardgame.BoardgameOffersFinder.notify.sender.text.TextFormatter;
import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.SpieleOffensiveDto;
import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.groupdeal.GroupDealDto;
import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.priceaction.PriceActionDto;
import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.product.SpieleOffensiveProductDto;
import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.schmiede.SpieleschmiedeDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
@Slf4j
@AllArgsConstructor
public class SpieleOffensiveNotifyImpl implements SpieleOffensiveNotify {

    private Notifier notifier;

    private MessageSource messageSource;

    private SpieleOffensiveNotifyConfig config;

    @Override
    public void notify(SpieleOffensiveDto dto) {
        if (dto instanceof GroupDealDto) {
            notifyGroupDeal((GroupDealDto) dto);
        }

        if (dto instanceof PriceActionDto) {
            notifyPriceAction((PriceActionDto) dto);
        }

        if (dto instanceof SpieleOffensiveProductDto) {
            notifyProduct((SpieleOffensiveProductDto) dto);
        }

        if (dto instanceof SpieleschmiedeDto) {
            notifySpieleschmiede((SpieleschmiedeDto) dto);
        }
    }

    private void notifySpieleschmiede(SpieleschmiedeDto dto) {
        String text = notifier.getTextFormatter()
                .bold(dto.getName())
                .newLine()
                .newLine()
                .link(dto.getUrl(), dto.getUrl())
                .getText();

        notifier.sendImage(dto.getImgUrl(),text);
    }

    private void notifyProduct(SpieleOffensiveProductDto dto) {
        String priceText = messageSource.getMessage("price", null, LocaleContextHolder.getLocale());
        String validUntilText =messageSource.getMessage("valid-until", null, LocaleContextHolder.getLocale());

        String text = notifier.getTextFormatter()
                .bold(dto.getName())
                .newLine()
                .newLine()
                .keyValue(priceText, dto.getPrice())
                .newLine()
                .newLine()
                .keyValue(validUntilText, dto.getEndDate().format(DateTimeFormatter.ofPattern(config.getDatePattern())))
                .newLine()
                .newLine()
                .link(dto.getUrl(), dto.getUrl())
                .getText();

        notifier.sendImage(dto.getImgUrl(),text);
    }

    private void notifyPriceAction(PriceActionDto dto) {
        String priceText = messageSource.getMessage("price", null, LocaleContextHolder.getLocale());
        String validUntilText =messageSource.getMessage("valid-until", null, LocaleContextHolder.getLocale());

        String text = notifier.getTextFormatter()
                .bold(dto.getName())
                .newLine()
                .newLine()
                .keyValue(priceText, dto.getPrice())
                .newLine()
                .newLine()
                .keyValue(validUntilText, dto.getValidUntil().format(DateTimeFormatter.ofPattern(config.getDatePattern())))
                .newLine()
                .newLine()
                .link(dto.getUrl(), dto.getUrl())
                .getText();

        notifier.sendImage(dto.getImgUrl(),text);
    }

    private void notifyGroupDeal(GroupDealDto dto) {
        String priceText = messageSource.getMessage("price", null, LocaleContextHolder.getLocale());
        String validUntilText =messageSource.getMessage("valid-until", null, LocaleContextHolder.getLocale());

        String text = notifier.getTextFormatter()
                .bold(dto.getName())
                .newLine()
                .newLine()
                .keyValue(priceText, dto.getPrice())
                .newLine()
                .newLine()
                .keyValue(validUntilText, dto.getValidUntil().format(DateTimeFormatter.ofPattern(config.getDatePattern())))
                .newLine()
                .newLine()
                .link(dto.getUrl(), dto.getUrl())
                .getText();

        notifier.sendImage(dto.getImgUrl(),text);
    }

}
