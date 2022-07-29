package de.agiehl.boardgame.BoardgameOffersFinder.scheduler;

import de.agiehl.boardgame.BoardgameOffersFinder.notify.brettspielangebote.BrettspielAngeboteNewsNotity;
import de.agiehl.boardgame.BoardgameOffersFinder.persistent.brettspielangebote.BrettspielAngeboteNewsPersistenceService;
import de.agiehl.boardgame.BoardgameOffersFinder.persistent.brettspielangebote.BrettspielAngeboteNewsRepository;
import de.agiehl.boardgame.BoardgameOffersFinder.web.brettspielangebote.BrettspielAngebotNewsDto;
import de.agiehl.boardgame.BoardgameOffersFinder.web.brettspielangebote.BrettspielAngebotNewsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class BrettspielAngeboteNewsScheduler {

    private final BrettspielAngeboteNewsNotity notity;

    private final BrettspielAngebotNewsService newsService;

    private final BrettspielAngeboteNewsPersistenceService persistenceService;


//    @Scheduled(fixedRateString = "${brettspiel-angebote.fixedRate.in.milliseconds}")
    public void proccessNews() {
        log.info("Process Brettspiel-Angebote News");

        newsService.getNews().stream()
                .filter(persistenceService::saveIfNewOrModified)
                .forEach(notity::notify);

    }

}
