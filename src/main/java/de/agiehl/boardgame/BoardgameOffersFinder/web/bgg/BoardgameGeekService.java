package de.agiehl.boardgame.BoardgameOffersFinder.web.bgg;

import de.agiehl.boardgame.BoardgameOffersFinder.persistent.EntityType;

import java.util.Optional;

public interface BoardgameGeekService {
    Optional<BoardgameGeekSearchDto> searchForBoardgame(String name);

    Optional<BoardgameGeekDto> getBoardgameInfos(Long id);

    Optional<BoardgameGeekDto> searchAndGet(String name);
}
