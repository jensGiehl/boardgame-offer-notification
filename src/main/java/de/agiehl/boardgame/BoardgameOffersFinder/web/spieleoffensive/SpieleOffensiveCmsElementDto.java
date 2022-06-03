package de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive;

import lombok.Builder;
import lombok.Data;
import org.jsoup.nodes.Element;

@Data
@Builder
public class SpieleOffensiveCmsElementDto {

    private Element element;

    private String link;

    private String imageFrameUrl;

}
