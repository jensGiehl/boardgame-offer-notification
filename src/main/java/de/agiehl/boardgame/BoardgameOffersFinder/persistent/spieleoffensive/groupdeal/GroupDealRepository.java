package de.agiehl.boardgame.BoardgameOffersFinder.persistent.spieleoffensive.groupdeal;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupDealRepository extends CrudRepository<GroupDealEntity, Long> {

    boolean existsByUrl(String url);

}
