package de.agiehl.boardgame.BoardgameOffersFinder.notify.brettspielangebote;

import de.agiehl.boardgame.BoardgameOffersFinder.web.brettspielangebote.BrettspielAngebotNewsDto;

public interface BrettspielAngeboteNewsNotity {
    void notify(BrettspielAngebotNewsDto dto);
}
