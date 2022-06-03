package de.agiehl.boardgame.BoardgameOffersFinder.web.bgg;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BoardgameGeekDto {

    private Long id;

    private String type;

    private String name;

}
