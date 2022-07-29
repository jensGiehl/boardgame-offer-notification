package de.agiehl.boardgame.BoardgameOffersFinder.scheduler;

import de.agiehl.boardgame.BoardgameOffersFinder.notify.milan.MilanNotify;
import de.agiehl.boardgame.BoardgameOffersFinder.persistent.EntityType;
import de.agiehl.boardgame.BoardgameOffersFinder.persistent.bgg.BggEntity;
import de.agiehl.boardgame.BoardgameOffersFinder.persistent.bgg.BggPersistenceService;
import de.agiehl.boardgame.BoardgameOffersFinder.persistent.brettspielangebote.BrettspielAngebotePriceRepository;
import de.agiehl.boardgame.BoardgameOffersFinder.persistent.brettspielangebote.ComparisonPriceEntity;
import de.agiehl.boardgame.BoardgameOffersFinder.persistent.milan.MilanEntity;
import de.agiehl.boardgame.BoardgameOffersFinder.persistent.milan.MilanPersistencePersistenceService;
import de.agiehl.boardgame.BoardgameOffersFinder.web.bgg.BoardgameGeekDto;
import de.agiehl.boardgame.BoardgameOffersFinder.web.bgg.BoardgameGeekService;
import de.agiehl.boardgame.BoardgameOffersFinder.web.brettspielangebote.BrettspielAngeboteBggDto;
import de.agiehl.boardgame.BoardgameOffersFinder.web.brettspielangebote.BrettspielAngebotePriceFinder;
import de.agiehl.boardgame.BoardgameOffersFinder.web.milan.MilanDto;
import de.agiehl.boardgame.BoardgameOffersFinder.web.milan.MilanService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class MilanScheduler {

    private final MilanService webService;

    private final MilanPersistencePersistenceService persistenceService;

    private final MilanNotify notifier;

    private final BoardgameGeekService bggService;

    private final BggPersistenceService bggPersistence;

    private final BrettspielAngebotePriceFinder priceFinder;

    private final BrettspielAngebotePriceRepository angebotePriceRepository;

//    @Scheduled(fixedRateString = "${milan.fixedRate.in.milliseconds}")
    public void proccessMilan() {
        log.info("Process Milan Scheduler");

        List<MilanDto> allOffersFromMilan = webService.parseSaleOffers();

        allOffersFromMilan.stream()
                .map(persistenceService::saveIfNewOrModified)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(notifier::notify);
    }

    @Scheduled(fixedDelayString = "${bgg.rating.fixedDelay.in.milliseconds}")
    public void proccessBggRatings() {
        List<MilanEntity> withoutBggRating = persistenceService.findAllWithoutBggRating();
        log.info("Found {} entities without a bgg rating", withoutBggRating.size());

        for (MilanEntity entity : withoutBggRating) {
            Optional<BoardgameGeekDto> boardgameInfo = bggService.searchAndGet(entity.getName());
            BggEntity bggEntity = bggPersistence.save(entity.getId(), EntityType.MILAN, boardgameInfo);
            notifier.notify(entity);
        }
    }

    @Scheduled(fixedDelayString = "${compare-price.rating.fixedDelay.in.milliseconds}")
    public void proccessComparisingPrices() {
        List<MilanEntity> withoutComparisonPrice = persistenceService.finAllWithoutComparisonPrice();
        log.info("Found {} entities without a comparison price", withoutComparisonPrice.size());

        for (MilanEntity entity : withoutComparisonPrice) {
            Optional<BggEntity> bgg = bggPersistence.findByFkIdAndFkType(entity.getId(), EntityType.MILAN);
            if (bgg.isEmpty()) {
                log.warn("No BGG Entry is present");
                continue;
            }

            Optional<BrettspielAngeboteBggDto> currentPriceForBggItem = priceFinder.getCurrentPriceForBggItem(bgg.get().getBggId());
            if (currentPriceForBggItem.isPresent()) {
                BrettspielAngeboteBggDto currentPriceDto = currentPriceForBggItem.get();
                ComparisonPriceEntity priceEntity = ComparisonPriceEntity.builder()
                        .price(currentPriceDto.getCurrentPrice())
                        .fkType(EntityType.MILAN)
                        .fkId(entity.getId())
                        .url(currentPriceDto.getUrl())
                        .build();

                priceEntity = angebotePriceRepository.save(priceEntity);

                notifier.notify(entity);
            } else {
                ComparisonPriceEntity priceEntity = ComparisonPriceEntity.builder()
                        .fkType(EntityType.MILAN)
                        .fkId(entity.getId())
                        .url(entity.getUrl())
                        .build();

                priceEntity = angebotePriceRepository.save(priceEntity);
            }
        }
    }

}
