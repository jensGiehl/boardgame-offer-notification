package de.agiehl.boardgame.BoardgameOffersFinder.persistent.notify;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotifyRepository extends CrudRepository<NotifyEntity, Long> {


}
