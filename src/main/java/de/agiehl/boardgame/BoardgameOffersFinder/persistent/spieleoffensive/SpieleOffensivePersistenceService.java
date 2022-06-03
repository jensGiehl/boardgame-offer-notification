package de.agiehl.boardgame.BoardgameOffersFinder.persistent.spieleoffensive;

import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.SpieleOffensiveDto;

public interface SpieleOffensivePersistenceService {
    boolean saveIfNewOrModified(SpieleOffensiveDto dto);
}
