package de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive;

public interface FurtherProcessing {
    SpieleOffensiveDto process(SpieleOffensiveCmsElementDto dto);

    boolean isProcessable(SpieleOffensiveCmsElementDto dto);
}

