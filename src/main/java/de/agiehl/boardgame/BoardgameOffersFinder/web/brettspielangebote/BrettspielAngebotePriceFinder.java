package de.agiehl.boardgame.BoardgameOffersFinder.web.brettspielangebote;

import de.agiehl.boardgame.BoardgameOffersFinder.web.WebResult;

public interface BrettspielAngebotePriceFinder {
    WebResult<BrettspielAngeboteDto> getCurrentPriceFor(String name);

    WebResult<BrettspielAngeboteBggDto> getCurrentPriceForBggItem(Long id);
}
