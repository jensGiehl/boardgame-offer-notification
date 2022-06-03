package de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.offer;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spiele-offensive.offer")
public class OfferConfig {

    private String imagePrefix;

    private String productImageSelector;

    private String productPriceSelector;

    private String productPriceReplaceString;

    private String productTitleSelector;

    private String productEndDateSelector;

    private String productEndDateReplaceString;

    private String productEndDatePattern;

}
