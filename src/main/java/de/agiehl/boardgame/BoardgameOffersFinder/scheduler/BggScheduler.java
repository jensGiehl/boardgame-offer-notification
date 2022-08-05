package de.agiehl.boardgame.BoardgameOffersFinder.scheduler;

import de.agiehl.boardgame.BoardgameOffersFinder.notify.NotifyService;
import de.agiehl.boardgame.BoardgameOffersFinder.persistent.DataEntity;
import de.agiehl.boardgame.BoardgameOffersFinder.persistent.PersistenceService;
import de.agiehl.boardgame.BoardgameOffersFinder.scheduler.config.BggSchedulerConfig;
import de.agiehl.boardgame.BoardgameOffersFinder.web.bgg.BoardgameGeekDto;
import de.agiehl.boardgame.BoardgameOffersFinder.web.bgg.BoardgameGeekService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class BggScheduler {

    private final NotifyService notity;

    private final PersistenceService persistenceService;

    private final BggSchedulerConfig config;

    private final BoardgameGeekService bggService;

    @Scheduled(fixedRateString = "${bgg.rating.fixedDelay.in.milliseconds}")
    public void fetchBggData() {
        if (Boolean.FALSE.equals(config.getEnable())) {
            return;
        }

        persistenceService.findPendingBggItems()
                .forEach(this::fetchData);
    }

    private void fetchData(DataEntity entity) {
        Optional<BoardgameGeekDto> boardgameGeekDto = bggService.searchAndGet(entity.getName());
        if (boardgameGeekDto.isPresent()) {
            BoardgameGeekDto geekDto = boardgameGeekDto.get();

            entity.setBggId(geekDto.getId());
            entity.setBggLink(geekDto.getBggLink());
            entity.setBggRating(geekDto.getRating());
            entity.setBggWanting(geekDto.getWanting());
            entity.setBggWishing(geekDto.getWishing());

            notity.notify(entity);
        }

        entity.setEnableBgg(false);
        persistenceService.saveBggInformation(entity);
    }

}
