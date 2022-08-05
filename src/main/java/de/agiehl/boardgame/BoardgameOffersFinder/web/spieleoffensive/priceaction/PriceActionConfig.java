package de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.priceaction;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spiele-offensive.price-action")
public class PriceActionConfig {

    private String linkMustContain;

}
