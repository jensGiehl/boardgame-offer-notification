package de.agiehl.boardgame.BoardgameOffersFinder.persistent.spieleoffensive.priceaction;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceActionRepository extends CrudRepository<PriceActionEntity, Long> {

    PriceActionEntity findFirstByUrlOrderByCreateDateDesc(String url);

}
