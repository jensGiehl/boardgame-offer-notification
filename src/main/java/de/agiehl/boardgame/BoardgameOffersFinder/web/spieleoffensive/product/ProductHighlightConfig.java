package de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.product;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "spiele-offensive.product-highlight")
public class ProductHighlightConfig {

    private String linkMustContain;

    private String priceSelector;

    private String nameSelector;

}
