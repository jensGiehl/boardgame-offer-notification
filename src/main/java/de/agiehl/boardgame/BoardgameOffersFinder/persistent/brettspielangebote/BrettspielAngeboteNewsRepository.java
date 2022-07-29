package de.agiehl.boardgame.BoardgameOffersFinder.persistent.brettspielangebote;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrettspielAngeboteNewsRepository extends CrudRepository<BrettspielAngeboteNewsEntity, Long> {

    BrettspielAngeboteNewsEntity findFirstByUrlOrderByCreateDateDesc(String url);

}
