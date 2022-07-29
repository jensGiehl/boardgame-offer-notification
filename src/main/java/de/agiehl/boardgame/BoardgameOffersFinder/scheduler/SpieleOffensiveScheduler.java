package de.agiehl.boardgame.BoardgameOffersFinder.scheduler;

import de.agiehl.boardgame.BoardgameOffersFinder.notify.spieleoffensive.SpieleOffensiveNotify;
import de.agiehl.boardgame.BoardgameOffersFinder.persistent.spieleoffensive.SpieleOffensivePersistenceService;
import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.SpieleOffensiveCmsParser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class SpieleOffensiveScheduler {

    private final SpieleOffensiveCmsParser spieleOffensiveCmsParser;

    private final SpieleOffensivePersistenceService persistenceService;

    private final SpieleOffensiveNotify notify;

    @Scheduled(fixedRateString = "${spiele-offensive.fixedRate.in.milliseconds}")
    public void proccessSo() {
        log.info("Process Spiele-Offensive Scheduler");

        spieleOffensiveCmsParser.parseRootPage().stream()
                .filter(persistenceService::saveIfNewOrModified)
                .forEach(notify::notify);
    }
}
