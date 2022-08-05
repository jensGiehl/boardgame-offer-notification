package de.agiehl.boardgame.BoardgameOffersFinder.web.bgg;

import java.util.Optional;

public interface BoardgameGeekService {
    Optional<BoardgameGeekSearchDto> searchForBoardgame(String name);

    Optional<BoardgameGeekDto> getBoardgameInfos(Long id);

    Optional<BoardgameGeekDto> searchAndGet(String name);
}
