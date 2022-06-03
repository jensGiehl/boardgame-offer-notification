package de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.schmiede;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spiele-offensive.schmiede")
public class SpieleschmiedeConfig {

    private String linkMustContain;

    private String productTitleSelector;

    private String detailPageSelector;

    private String rootUrl;

}
