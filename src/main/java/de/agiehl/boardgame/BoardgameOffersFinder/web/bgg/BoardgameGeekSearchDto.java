package de.agiehl.boardgame.BoardgameOffersFinder.web.bgg;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BoardgameGeekSearchDto {

    private Long id;

    private String type;

    private String name;

    private SearchStatus searchStatus;

    private Integer nameCounter;

    public static enum SearchStatus {
        FINISHED,
        NOT_FOUND,
        ERROR
    }

}
