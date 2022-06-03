package de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.offer;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class OfferDto {

    private String title;

    private String price;

    private LocalDate validUntil;

    private String imageUrl;

}
