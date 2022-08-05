package de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class SpieleOffensiveDto {

    private String imgUrl;

    private String name;

    private String price;

    private LocalDate validUntil;

    private String url;

}
