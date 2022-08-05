package de.agiehl.boardgame.BoardgameOffersFinder.scheduler;

import de.agiehl.boardgame.BoardgameOffersFinder.notify.NotifyService;
import de.agiehl.boardgame.BoardgameOffersFinder.persistent.CrawlerName;
import de.agiehl.boardgame.BoardgameOffersFinder.persistent.DataEntity;
import de.agiehl.boardgame.BoardgameOffersFinder.persistent.PersistenceService;
import de.agiehl.boardgame.BoardgameOffersFinder.scheduler.config.BrettspielAngeboteNewsSchedulerConfig;
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

    private final NotifyService notity;

    private final BrettspielAngebotNewsService newsService;

    private final PersistenceService persistenceService;

    private final BrettspielAngeboteNewsSchedulerConfig config;

    @Scheduled(fixedRateString = "${brettspiel-angebote.fixedRate.in.milliseconds}")
    public void proccessNews() {
        if (Boolean.FALSE.equals(config.getEnable())) {
            return;
        }
        log.debug("Process Brettspiel-Angebote News");

        newsService.getNews().stream()
                .filter(dto -> persistenceService.urlNotExists(dto.getUrl()))
                .map(this::toEntity)
                .forEach(this::saveAndNotify);

    }

    private void saveAndNotify(DataEntity entity) {
        DataEntity savedEntity = persistenceService.save(entity);

        notity.notify(savedEntity);
    }

    private DataEntity toEntity(BrettspielAngebotNewsDto dto) {
        return DataEntity.builder()
                .crawlerName(CrawlerName.BRETTSPIELANGEBOTE_NEWS)
                .description(dto.getDescription())
                .name(dto.getTitle())
                .url(dto.getUrl())
                .build();
    }

}
