package de.agiehl.boardgame.BoardgameOffersFinder.scheduler;

import de.agiehl.boardgame.BoardgameOffersFinder.notify.NotifyService;
import de.agiehl.boardgame.BoardgameOffersFinder.persistent.DataEntity;
import de.agiehl.boardgame.BoardgameOffersFinder.persistent.PersistenceService;
import de.agiehl.boardgame.BoardgameOffersFinder.scheduler.config.BggSchedulerConfig;
import de.agiehl.boardgame.BoardgameOffersFinder.web.WebResult;
import de.agiehl.boardgame.BoardgameOffersFinder.web.bgg.BoardgameGeekDto;
import de.agiehl.boardgame.BoardgameOffersFinder.web.bgg.BoardgameGeekService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class BggScheduler {

    private final NotifyService notity;

    private final PersistenceService persistenceService;

    private final BggSchedulerConfig config;

    private final BoardgameGeekService bggService;

    private final CircuitBreakerFactory circuitBreakerFactory;

    @Scheduled(fixedRateString = "${bgg.rating.fixedDelay.in.milliseconds}")
    public void fetchBggData() {
        if (Boolean.FALSE.equals(config.getEnable())) {
            return;
        }

        persistenceService.findPendingBggItems()
                .forEach(this::fetchData);
    }

    private void fetchData(DataEntity entity) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("bgg");

        WebResult<BoardgameGeekDto> boardgameGeekDto = circuitBreaker.run(
                () -> bggService.searchAndGet(entity.getName(), entity.getBggNameCounter()),
                throwable -> WebResult.<BoardgameGeekDto>builder().status(WebResult.SearchStatus.ERROR).build()
        );

        switch (boardgameGeekDto.getStatus()) {
            case ERROR -> entity.setBggNameCounter(boardgameGeekDto.getErrorCounter());
            case FOUND -> {
                BoardgameGeekDto geekDto = boardgameGeekDto.getResult();
                entity.setBggId(geekDto.getId());
                entity.setBggLink(geekDto.getBggLink());
                entity.setBggRating(geekDto.getRating());
                entity.setBggWanting(geekDto.getWanting());
                entity.setBggWishing(geekDto.getWishing());
                entity.setBggUserRated(geekDto.getUserRated());
                entity.setBggRank(geekDto.getBggRank());
                entity.setEnableBgg(false);
                notity.notify(entity);
            }
            case NOT_FOUND -> entity.setEnableBgg(false);
            default -> log.error("Unknown status: {}", boardgameGeekDto.getStatus());
        }

        persistenceService.saveBggInformation(entity);
    }

}
