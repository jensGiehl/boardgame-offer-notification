package de.agiehl.boardgame.BoardgameOffersFinder.persistent.brettspielangebote;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrettspielAngeboteRepository extends CrudRepository<BrettspielAngeboteNewsEntity, Long> {

    BrettspielAngeboteNewsEntity findFirstByUrlOrderByCreateDateDesc(String url);

}
