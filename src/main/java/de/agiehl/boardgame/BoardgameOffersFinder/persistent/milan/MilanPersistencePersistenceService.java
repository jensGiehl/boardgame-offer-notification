package de.agiehl.boardgame.BoardgameOffersFinder.persistent.milan;

import de.agiehl.boardgame.BoardgameOffersFinder.web.milan.MilanDto;

import java.util.List;
import java.util.Optional;

public interface MilanPersistencePersistenceService {
    Optional<MilanEntity> saveIfNewOrModified(MilanDto dto);

    List<MilanEntity> findAllWithoutBggRating();

    List<MilanEntity> finAllWithoutComparisonPrice();

}
