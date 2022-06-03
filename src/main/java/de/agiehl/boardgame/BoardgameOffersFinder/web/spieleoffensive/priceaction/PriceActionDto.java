package de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.priceaction;

import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.SpieleOffensiveDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PriceActionDto implements SpieleOffensiveDto {

    private String url;

    private String imgUrl;

    private String price;

    private String name;

    private LocalDate validUntil;

}
