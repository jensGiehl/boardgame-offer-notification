package de.agiehl.boardgame.BoardgameOffersFinder.web.milan;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "milan")
@Component
public class MilanConfig {

    private String url;

    private String imageUrlPrefix;

    private String productListSelector;

    private String productTitleSelector;

    private String productPriceSelector;

    private String productDeliverySelector;

    private String productLinkSelector;

    private String productImgSelector;

}
