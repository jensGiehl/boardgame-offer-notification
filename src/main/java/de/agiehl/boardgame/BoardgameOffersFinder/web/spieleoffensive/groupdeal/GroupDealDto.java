package de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.groupdeal;

import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.SpieleOffensiveDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class GroupDealDto implements SpieleOffensiveDto {

    private String imgUrl;

    private String name;

    private String price;

    private LocalDate validUntil;

    private String url;

}
