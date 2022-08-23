package de.agiehl.boardgame.BoardgameOffersFinder.web.bgg;

import de.agiehl.boardgame.BoardgameOffersFinder.web.WebResult;

public interface BoardgameGeekService {
    WebResult<BoardgameGeekSearchDto> searchForBoardgame(String name, Integer nameCounter);

    WebResult<BoardgameGeekDto> getBoardgameInfos(Long id);

    WebResult<BoardgameGeekDto> searchAndGet(String name, Integer nameCounter);
}
