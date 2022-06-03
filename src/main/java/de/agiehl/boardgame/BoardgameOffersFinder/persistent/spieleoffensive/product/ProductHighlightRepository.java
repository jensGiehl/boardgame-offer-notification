package de.agiehl.boardgame.BoardgameOffersFinder.persistent.spieleoffensive.product;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductHighlightRepository extends CrudRepository<ProductHighlightEntity, Long> {

    ProductHighlightEntity findFirstByUrlOrderByCreateDateDesc(String url);

}
