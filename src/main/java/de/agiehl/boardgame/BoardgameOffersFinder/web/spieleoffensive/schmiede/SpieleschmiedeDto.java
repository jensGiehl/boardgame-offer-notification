package de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.schmiede;

import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.SpieleOffensiveDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SpieleschmiedeDto implements SpieleOffensiveDto {

    private String imgUrl;

    private String url;

    private String name;

}
