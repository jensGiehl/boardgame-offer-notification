package de.agiehl.boardgame.BoardgameOffersFinder.web.bgg;

import java.util.Optional;

public interface BoardgameGeekService {
    Optional<BoardgameGeekDto> searchForBoardgame(String name);
}
