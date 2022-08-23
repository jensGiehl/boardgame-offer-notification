package de.agiehl.boardgame.BoardgameOffersFinder.scheduler;

import de.agiehl.boardgame.BoardgameOffersFinder.notify.NotifyService;
import de.agiehl.boardgame.BoardgameOffersFinder.persistent.CrawlerName;
import de.agiehl.boardgame.BoardgameOffersFinder.persistent.DataEntity;
import de.agiehl.boardgame.BoardgameOffersFinder.persistent.PersistenceService;
import de.agiehl.boardgame.BoardgameOffersFinder.scheduler.config.UnknownsSchedulerConfig;
import de.agiehl.boardgame.BoardgameOffersFinder.web.unknowns.UnknownsDto;
import de.agiehl.boardgame.BoardgameOffersFinder.web.unknowns.UnknownsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UnknownsScheduler {

    private final NotifyService notity;

    private final UnknownsService service;

    private final UnknownsSchedulerConfig config;

    private final PersistenceService persistenceService;

    @Scheduled(fixedRateString = "${unknowns.scheduler.rating.fixedDelay.in.milliseconds}")
    public void proccess() {
        if (Boolean.FALSE.equals(config.getEnable())) {
            return;
        }

        log.debug("Process Unknowns Scheduler");

        service.getAllThreads().stream()
                .filter(dto -> persistenceService.urlNotExists(dto.getUrl()))
                .map(this::toEntity)
                .map(persistenceService::save)
                .forEach(notity::notify);
    }

    private DataEntity toEntity(UnknownsDto dto) {
        return DataEntity.builder()
                .url(dto.getUrl())
                .name(dto.getTitle())
                .crawlerName(CrawlerName.UNKNOWNS)
                .enableBestPrice(false)
                .enableBgg(false)
                .bestPricePossible(false)
                .build();
    }

}