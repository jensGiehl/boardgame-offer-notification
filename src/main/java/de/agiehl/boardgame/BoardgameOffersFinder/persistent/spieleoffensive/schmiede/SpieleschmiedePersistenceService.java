package de.agiehl.boardgame.BoardgameOffersFinder.persistent.spieleoffensive.schmiede;

import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.schmiede.SpieleschmiedeDto;

public interface SpieleschmiedePersistenceService {
    boolean saveIfNew(SpieleschmiedeDto dto);
}
