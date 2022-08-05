package de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.offer;

import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.SpieleOffensiveCmsElementDto;
import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.SpieleOffensiveDto;

public interface OfferService {
    SpieleOffensiveDto getOfferData(SpieleOffensiveCmsElementDto dto);

    boolean isOffer(SpieleOffensiveCmsElementDto dto);
}
