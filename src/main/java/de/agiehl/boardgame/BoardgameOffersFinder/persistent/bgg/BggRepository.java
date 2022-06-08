package de.agiehl.boardgame.BoardgameOffersFinder.persistent.bgg;

import de.agiehl.boardgame.BoardgameOffersFinder.persistent.EntityType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BggRepository extends CrudRepository<BggEntity,Long> {

    Optional<BggEntity> findFirstByFkIdAndFkTypeOrderByCreateDate(Long id, EntityType type);
}
