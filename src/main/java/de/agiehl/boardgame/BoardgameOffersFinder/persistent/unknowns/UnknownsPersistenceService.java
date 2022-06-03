package de.agiehl.boardgame.BoardgameOffersFinder.persistent.unknowns;

import de.agiehl.boardgame.BoardgameOffersFinder.web.unknowns.UnknownsDto;

public interface UnknownsPersistenceService {
    boolean saveIfNewOrModified(UnknownsDto dto);
}
