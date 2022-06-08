package de.agiehl.boardgame.BoardgameOffersFinder.persistent.bgg;

import de.agiehl.boardgame.BoardgameOffersFinder.persistent.EntityType;
import de.agiehl.boardgame.BoardgameOffersFinder.web.bgg.BoardgameGeekDto;

import java.util.Optional;

public interface BggPersistenceService {
    BggEntity save(Long id, EntityType entityType, Optional<BoardgameGeekDto> boardgameInfo);

    Optional<BggEntity> findByFkIdAndFkType(Long id, EntityType type);
}
