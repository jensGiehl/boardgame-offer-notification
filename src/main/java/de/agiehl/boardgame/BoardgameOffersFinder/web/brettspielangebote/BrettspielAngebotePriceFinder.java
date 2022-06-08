package de.agiehl.boardgame.BoardgameOffersFinder.web.brettspielangebote;

import java.util.Optional;

public interface BrettspielAngebotePriceFinder {
    Optional<BrettspielAngeboteDto> getCurrentPriceFor(String name);

    Optional<BrettspielAngeboteBggDto> getCurrentPriceForBggItem(Long id);
}
