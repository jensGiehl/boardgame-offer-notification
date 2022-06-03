package de.agiehl.boardgame.BoardgameOffersFinder.web.brettspielangebote;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BrettspielAngeboteDto {

    private String currentPrice;

    private String name;

    private String bestPrice;

    private String provider;

    private String bggLink;

    private String bggRating;

}
