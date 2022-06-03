package de.agiehl.boardgame.BoardgameOffersFinder.notify.milan;

import de.agiehl.boardgame.BoardgameOffersFinder.notify.sender.Notifier;
import de.agiehl.boardgame.BoardgameOffersFinder.notify.sender.NotifyResponse;
import de.agiehl.boardgame.BoardgameOffersFinder.persistent.notify.NotifyEntity;
import de.agiehl.boardgame.BoardgameOffersFinder.persistent.notify.NotifyPersistenceService;
import de.agiehl.boardgame.BoardgameOffersFinder.web.milan.MilanDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
@AllArgsConstructor
public class MilanNotifyImpl implements MilanNotify {

    private Notifier notifier;

    private MessageSource messageSource;

    private NotifyPersistenceService notifyPersistenceService;

    @Override
    public void notify(MilanDto dto) {
        String priceText = messageSource.getMessage("price", null, LocaleContextHolder.getLocale());
        String stockText = messageSource.getMessage("stock", null, LocaleContextHolder.getLocale());
        String linkText = messageSource.getMessage("milan.link", null, LocaleContextHolder.getLocale());

        String textToSend = notifier.getTextFormatter()
                .bold(dto.getName())
                .newLine()
                .keyValue(priceText, dto.getPrice())
                .newLine()
                .keyValue(stockText, dto.getStockText())
                .newLine()
                .newLine()
                .link(linkText, dto.getUrl())
                .getText();

        NotifyResponse response;
        if (Objects.isNull(dto.getImgUrl())) {
            response = notifier.sendText(textToSend);
        } else {
            response = notifier.sendImage(dto.getImgUrl(), textToSend);
        }
    }

}
