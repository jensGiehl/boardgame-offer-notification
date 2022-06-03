package de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.offer;

import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.SpieleOffensiveCmsElementDto;

public interface OfferService {
    OfferDto getOfferData(SpieleOffensiveCmsElementDto dto);

    boolean isOffer(SpieleOffensiveCmsElementDto dto);
}
