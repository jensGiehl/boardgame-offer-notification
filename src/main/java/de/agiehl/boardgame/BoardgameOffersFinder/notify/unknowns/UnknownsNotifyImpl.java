package de.agiehl.boardgame.BoardgameOffersFinder.notify.unknowns;

import de.agiehl.boardgame.BoardgameOffersFinder.notify.sender.Notifier;
import de.agiehl.boardgame.BoardgameOffersFinder.web.unknowns.UnknownsDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class UnknownsNotifyImpl {

    private Notifier notifier;

    public void notify(UnknownsDto dto) {
        String textToSend = notifier.getTextFormatter()
                .bold(dto.getTitle())
                .newLine()
                .normal(dto.getUrl())
                .getText();
    }

}
