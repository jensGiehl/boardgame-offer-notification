package de.agiehl.boardgame.BoardgameOffersFinder.persistent.brettspielangebote;

import de.agiehl.boardgame.BoardgameOffersFinder.persistent.EntityType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrettspielAngebotePriceRepository extends CrudRepository<ComparisonPriceEntity, Long> {

    Optional<ComparisonPriceEntity> findFirstByFkIdAndFkTypeOrderByCreateDate(Long id, EntityType type);
}
