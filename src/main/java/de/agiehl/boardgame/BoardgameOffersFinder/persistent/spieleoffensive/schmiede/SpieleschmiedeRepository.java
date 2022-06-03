package de.agiehl.boardgame.BoardgameOffersFinder.persistent.spieleoffensive.schmiede;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpieleschmiedeRepository extends CrudRepository<SpieleschmiedeEntity, Long> {

    boolean existsByUrl(String url);
}
