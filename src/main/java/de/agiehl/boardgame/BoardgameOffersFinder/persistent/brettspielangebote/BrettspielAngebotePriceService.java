package de.agiehl.boardgame.BoardgameOffersFinder.persistent.brettspielangebote;

import de.agiehl.boardgame.BoardgameOffersFinder.persistent.EntityType;
import de.agiehl.boardgame.BoardgameOffersFinder.web.brettspielangebote.BrettspielAngeboteBggDto;

import java.util.Optional;

public interface BrettspielAngebotePriceService {
    ComparisonPriceEntity save(Long id, EntityType entityType, BrettspielAngeboteBggDto dto);

    Optional<ComparisonPriceEntity> findByFkIdAndFkType(Long id, EntityType type);

}
