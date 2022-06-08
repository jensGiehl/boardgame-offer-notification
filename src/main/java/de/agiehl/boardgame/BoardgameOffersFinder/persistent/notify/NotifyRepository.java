package de.agiehl.boardgame.BoardgameOffersFinder.persistent.notify;

import de.agiehl.boardgame.BoardgameOffersFinder.persistent.EntityType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotifyRepository extends CrudRepository<NotifyEntity, Long> {

    Optional<NotifyEntity> findByFkIdAndFkType(Long fkId, EntityType fkType);

}
