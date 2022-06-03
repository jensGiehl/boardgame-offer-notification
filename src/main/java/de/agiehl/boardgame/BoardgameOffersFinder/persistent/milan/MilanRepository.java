package de.agiehl.boardgame.BoardgameOffersFinder.persistent.milan;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MilanRepository  extends CrudRepository<MilanEntity, Long> {

    MilanEntity findFirstByUrlOrderByCreateDateDesc(String url);

}
