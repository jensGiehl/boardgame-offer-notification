package de.agiehl.boardgame.BoardgameOffersFinder.persistent.brettspielangebote;

import de.agiehl.boardgame.BoardgameOffersFinder.web.brettspielangebote.BrettspielAngebotNewsDto;

public interface BrettspielAngeboteNewsPersistenceService {
    boolean saveIfNewOrModified(BrettspielAngebotNewsDto dto);
}
