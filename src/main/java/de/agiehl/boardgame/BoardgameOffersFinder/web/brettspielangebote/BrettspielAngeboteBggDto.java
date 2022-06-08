package de.agiehl.boardgame.BoardgameOffersFinder.web.brettspielangebote;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BrettspielAngeboteBggDto {

    private String currentPrice;

    private String url;
}
