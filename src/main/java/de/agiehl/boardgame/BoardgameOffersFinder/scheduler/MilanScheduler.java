package de.agiehl.boardgame.BoardgameOffersFinder.scheduler;

import de.agiehl.boardgame.BoardgameOffersFinder.persistent.CrawlerName;
import de.agiehl.boardgame.BoardgameOffersFinder.persistent.DataEntity;
import de.agiehl.boardgame.BoardgameOffersFinder.persistent.PersistenceService;
import de.agiehl.boardgame.BoardgameOffersFinder.scheduler.config.MilanSchedulerConfig;
import de.agiehl.boardgame.BoardgameOffersFinder.web.milan.MilanDto;
import de.agiehl.boardgame.BoardgameOffersFinder.web.milan.MilanService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class MilanScheduler {

    private final MilanService webService;

    private final PersistenceService persistenceService;

    private final MilanSchedulerConfig config;

    @Scheduled(fixedRateString = "${milan.fixedRate.in.milliseconds}")
    public void proccessMilan() {
        if (Boolean.FALSE.equals(config.getEnable())) {
            return;
        }
        log.debug("Process Milan Scheduler");

        List<MilanDto> allOffersFromMilan = webService.parseSaleOffers();

        allOffersFromMilan.stream()
                .filter(dto -> persistenceService.urlNotExists(dto.getUrl()))
                .map(this::toEntity)
                .forEach(persistenceService::save);
    }

    private DataEntity toEntity(MilanDto dto) {
        return DataEntity.builder()
                .name(dto.getName())
                .url(dto.getUrl())
                .imageUrl(dto.getImgUrl())
                .price(dto.getPrice())
                .stockText(dto.getStockText())
                .crawlerName(CrawlerName.MILAN)
                .enableBestPrice(true)
                .enableBgg(true)
                .build();
    }

}
