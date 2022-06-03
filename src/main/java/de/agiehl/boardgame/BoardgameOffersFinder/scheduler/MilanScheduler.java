package de.agiehl.boardgame.BoardgameOffersFinder.scheduler;

import de.agiehl.boardgame.BoardgameOffersFinder.notify.milan.MilanNotify;
import de.agiehl.boardgame.BoardgameOffersFinder.persistent.milan.MilanPersistencePersistenceService;
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

    private final MilanPersistencePersistenceService persistenceService;

    private final MilanNotify notifier;

    @Scheduled(fixedRateString = "${milan.fixedRate.in.milliseconds}")
    public void proccessMilan() {
        List<MilanDto> allOffersFromMilan = webService.parseSaleOffers();

        allOffersFromMilan.stream()
                .filter(persistenceService::saveIfNewOrModified)
                .forEach(notifier::notify);
    }

}
