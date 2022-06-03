package de.agiehl.boardgame.BoardgameOffersFinder.notify.brettspielangebote;

import de.agiehl.boardgame.BoardgameOffersFinder.notify.sender.Notifier;
import de.agiehl.boardgame.BoardgameOffersFinder.web.brettspielangebote.BrettspielAngebotNewsDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class BrettspielAngeboteNewsNotityImpl {

    private Notifier notifier;

    public void notify(BrettspielAngebotNewsDto dto) {
        String textToSend = notifier.getTextFormatter()
                .bold(dto.getTitle())
                .newLine()
                .newLine()
                .normal(dto.getDescription())
                .newLine()
                .normal(dto.getUrl())
                .getText();


    }

}
