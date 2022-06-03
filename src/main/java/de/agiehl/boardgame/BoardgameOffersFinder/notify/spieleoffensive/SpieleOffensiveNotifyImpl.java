package de.agiehl.boardgame.BoardgameOffersFinder.notify.spieleoffensive;

import de.agiehl.boardgame.BoardgameOffersFinder.notify.sender.Notifier;
import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.SpieleOffensiveDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class SpieleOffensiveNotifyImpl {

    private Notifier notifier;

    private MessageSource messageSource;

    public void notify(SpieleOffensiveDto dto) {

    }

}
