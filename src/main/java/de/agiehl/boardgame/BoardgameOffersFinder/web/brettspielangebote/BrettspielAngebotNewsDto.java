package de.agiehl.boardgame.BoardgameOffersFinder.web.brettspielangebote;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BrettspielAngebotNewsDto {

    private String title;

    private String url;

    private String description;

}
