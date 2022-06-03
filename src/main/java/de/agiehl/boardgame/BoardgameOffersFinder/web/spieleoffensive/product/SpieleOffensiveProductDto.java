package de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.product;

import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.SpieleOffensiveDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class SpieleOffensiveProductDto implements SpieleOffensiveDto {

    private String url;

    private String imgUrl;

    private LocalDate endDate;

    private String name;

    private String price;

}
