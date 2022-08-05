package de.agiehl.boardgame.BoardgameOffersFinder.scheduler;

import de.agiehl.boardgame.BoardgameOffersFinder.notify.NotifyService;
import de.agiehl.boardgame.BoardgameOffersFinder.persistent.DataEntity;
import de.agiehl.boardgame.BoardgameOffersFinder.persistent.PersistenceService;
import de.agiehl.boardgame.BoardgameOffersFinder.scheduler.config.NotifierSchedulerConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class NotifierScheduler {

    private final NotifyService notifier;

    private final PersistenceService persistenceService;

    private final NotifierSchedulerConfig config;

    @Scheduled(fixedDelayString = "${notify.fixedRate.in.milliseconds}")
    public void sendNotificationForAllOldEntities() {
        if (Boolean.FALSE.equals(config.getEnable())) {
            return;
        }

        log.debug("Process Notification");

        List<DataEntity> timeoutEntities = persistenceService.findOldEntities(config.getMaxAge());

        if (!timeoutEntities.isEmpty()) {
            log.info("Found {} old entities", timeoutEntities.size());
        }

        timeoutEntities.forEach(notifier::notify);
    }
}
