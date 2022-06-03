package de.agiehl.boardgame.BoardgameOffersFinder.persistent.unknowns;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnknownsRepository extends CrudRepository<UnknownsEntity, Long> {

    UnknownsEntity findFirstByUrlOrderByCreateDateDesc(String url);

}
