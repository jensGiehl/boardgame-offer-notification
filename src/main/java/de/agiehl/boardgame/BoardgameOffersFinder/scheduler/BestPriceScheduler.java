package de.agiehl.boardgame.BoardgameOffersFinder.scheduler;

import de.agiehl.boardgame.BoardgameOffersFinder.notify.NotifyService;
import de.agiehl.boardgame.BoardgameOffersFinder.persistent.DataEntity;
import de.agiehl.boardgame.BoardgameOffersFinder.persistent.PersistenceService;
import de.agiehl.boardgame.BoardgameOffersFinder.scheduler.config.BestPriceSchedulerConfig;
import de.agiehl.boardgame.BoardgameOffersFinder.web.WebResult;
import de.agiehl.boardgame.BoardgameOffersFinder.web.brettspielangebote.BrettspielAngeboteBggDto;
import de.agiehl.boardgame.BoardgameOffersFinder.web.brettspielangebote.BrettspielAngeboteDto;
import de.agiehl.boardgame.BoardgameOffersFinder.web.brettspielangebote.BrettspielAngebotePriceFinder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class BestPriceScheduler {

    private final NotifyService notity;

    private final PersistenceService persistenceService;

    private final BestPriceSchedulerConfig config;

    private final BrettspielAngebotePriceFinder brettspielAngebotePriceFinder;

    private final CircuitBreakerFactory circuitBreakerFactory;

    @Scheduled(fixedRateString = "${brettspiel-angebote.fixedRate.in.milliseconds}")
    public void fetchBggData() {
        if (Boolean.FALSE.equals(config.getEnable())) {
            return;
        }

        persistenceService.findPendingBestPriceItems()
                .forEach(this::fetchData);
    }

    private void fetchData(DataEntity entity) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("bestPrice");

        if (entity.getBestPriceFailCount() > config.getMaxFailCount()) {
            log.warn("Max fail count exceeded for {}", entity);
            entity.setEnableBestPrice(false);
            persistenceService.saveBestPriceInformation(entity);
            notity.notify(entity);
            return;
        }

        if (Objects.nonNull(entity.getBggId())) {
            WebResult<BrettspielAngeboteBggDto> currentPriceForBggItem = circuitBreaker.run(
                    () -> brettspielAngebotePriceFinder.getCurrentPriceForBggItem(entity.getBggId()),
                    throwable -> WebResult.<BrettspielAngeboteBggDto>builder().status(WebResult.SearchStatus.ERROR).build());

            if (currentPriceForBggItem.getStatus() == WebResult.SearchStatus.FOUND) {
                BrettspielAngeboteBggDto brettspielAngeboteBggDto = currentPriceForBggItem.getResult();
                entity.setBestPrice(brettspielAngeboteBggDto.getCurrentPrice());
                entity.setBestPriceUrl(brettspielAngeboteBggDto.getUrl());
            }

            if (currentPriceForBggItem.getStatus() == WebResult.SearchStatus.ERROR) {
                persistenceService.increaseBestPriceFailCount(entity);
            } else {
                entity.setEnableBestPrice(false);
                notity.notify(entity);
            }
        } else {
            WebResult<BrettspielAngeboteDto> currentPrice = circuitBreaker.run(
                    () -> brettspielAngebotePriceFinder.getCurrentPriceFor(entity.getName()),
                    throwable -> WebResult.<BrettspielAngeboteDto>builder().status(WebResult.SearchStatus.ERROR).build());

            if (currentPrice.getStatus() == WebResult.SearchStatus.FOUND) {
                BrettspielAngeboteDto brettspielAngeboteDto = currentPrice.getResult();
                entity.setBestPrice(brettspielAngeboteDto.getCurrentPrice());
                entity.setBestPriceUrl(brettspielAngeboteDto.getUrl());
            }

            if (currentPrice.getStatus() == WebResult.SearchStatus.ERROR) {
                persistenceService.increaseBestPriceFailCount(entity);
            } else {
                entity.setEnableBestPrice(false);
                notity.notify(entity);
            }
        }

        persistenceService.saveBestPriceInformation(entity);
    }

}
