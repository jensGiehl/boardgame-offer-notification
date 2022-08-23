package de.agiehl.boardgame.BoardgameOffersFinder.web.bgg;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BoardgameGeekDto {

    private Long id;

    private String bggLink;

    private String rating;

    private Integer wanting;

    private Integer wishing;

    private Long userRated;

    private Long bggRank;

}
