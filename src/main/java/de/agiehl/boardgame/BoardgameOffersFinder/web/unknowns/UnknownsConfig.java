package de.agiehl.boardgame.BoardgameOffersFinder.web.unknowns;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "unknowns")
@Component
public class UnknownsConfig {

    private String url;

    private String articleSelector;

}
