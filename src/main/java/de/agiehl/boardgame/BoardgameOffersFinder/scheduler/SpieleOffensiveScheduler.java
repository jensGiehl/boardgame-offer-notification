package de.agiehl.boardgame.BoardgameOffersFinder.scheduler;

import de.agiehl.boardgame.BoardgameOffersFinder.persistent.PersistenceService;
import de.agiehl.boardgame.BoardgameOffersFinder.scheduler.config.SpieleOffensiveSchedulerConfig;
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

    private final SpieleOffensiveSchedulerConfig config;

    private final PersistenceService persistenceService;

    @Scheduled(fixedRateString = "${spiele-offensive.fixedRate.in.milliseconds}")
    public void proccessSpieleOffensive() {
        if (Boolean.FALSE.equals(config.getEnable())) {
            return;
        }

        log.debug("Process Spiele-Offensive Scheduler");

    }
}
