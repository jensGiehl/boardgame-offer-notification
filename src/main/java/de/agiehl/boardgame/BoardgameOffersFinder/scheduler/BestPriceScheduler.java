package de.agiehl.boardgame.BoardgameOffersFinder.scheduler;

import de.agiehl.boardgame.BoardgameOffersFinder.notify.NotifyService;
import de.agiehl.boardgame.BoardgameOffersFinder.persistent.DataEntity;
import de.agiehl.boardgame.BoardgameOffersFinder.persistent.PersistenceService;
import de.agiehl.boardgame.BoardgameOffersFinder.scheduler.config.BestPriceSchedulerConfig;
import de.agiehl.boardgame.BoardgameOffersFinder.web.brettspielangebote.BrettspielAngeboteBggDto;
import de.agiehl.boardgame.BoardgameOffersFinder.web.brettspielangebote.BrettspielAngeboteDto;
import de.agiehl.boardgame.BoardgameOffersFinder.web.brettspielangebote.BrettspielAngebotePriceFinder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class BestPriceScheduler {

    private final NotifyService notity;

    private final PersistenceService persistenceService;

    private final BestPriceSchedulerConfig config;

    private final BrettspielAngebotePriceFinder brettspielAngebotePriceFinder;

    @Scheduled(fixedRateString = "${brettspiel-angebote.fixedRate.in.milliseconds}")
    public void fetchBggData() {
        if (Boolean.FALSE.equals(config.getEnable())) {
            return;
        }

        persistenceService.findPendingBestPriceItems()
                .forEach(this::fetchData);
    }

    private void fetchData(DataEntity entity) {
        if (Objects.nonNull(entity.getBggId())) {
            Optional<BrettspielAngeboteBggDto> currentPriceForBggItem = brettspielAngebotePriceFinder.getCurrentPriceForBggItem(entity.getBggId());
            if (currentPriceForBggItem.isPresent()) {
                BrettspielAngeboteBggDto brettspielAngeboteBggDto = currentPriceForBggItem.get();
                entity.setBestPrice(brettspielAngeboteBggDto.getCurrentPrice());
                entity.setBestPriceUrl(brettspielAngeboteBggDto.getUrl());

                notity.notify(entity);
            }
        } else {
            Optional<BrettspielAngeboteDto> currentPrice = brettspielAngebotePriceFinder.getCurrentPriceFor(entity.getName());
            if (currentPrice.isPresent()) {
                BrettspielAngeboteDto brettspielAngeboteDto = currentPrice.get();
                entity.setBestPrice(brettspielAngeboteDto.getCurrentPrice());
                entity.setBestPriceUrl(brettspielAngeboteDto.getUrl());

                notity.notify(entity);
            }
        }

        entity.setEnableBestPrice(false);
        persistenceService.saveBestPriceInformation(entity);
    }

}
