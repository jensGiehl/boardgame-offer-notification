package de.agiehl.boardgame.BoardgameOffersFinder.persistent.spieleoffensive.priceaction;

import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.priceaction.PriceActionDto;

public interface PriceActionPersistenceService {
    boolean saveIfNew(PriceActionDto dto);
}
