package de.agiehl.boardgame.BoardgameOffersFinder.persistent.spieleoffensive.product;

import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.product.SpieleOffensiveProductDto;

public interface ProductHightlightService {
    boolean saveIfNew(SpieleOffensiveProductDto dto);
}
