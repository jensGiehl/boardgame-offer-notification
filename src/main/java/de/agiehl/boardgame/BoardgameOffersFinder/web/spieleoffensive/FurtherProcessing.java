package de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive;

import java.util.Optional;

public interface FurtherProcessing {
    Optional<SpieleOffensiveDto> process(SpieleOffensiveCmsElementDto dto);

    boolean isProcessable(SpieleOffensiveCmsElementDto dto);
}

