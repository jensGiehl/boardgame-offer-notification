package de.agiehl.boardgame.BoardgameOffersFinder.web.bgg;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties(prefix = "bgg")
public class BoardgameGeekConfig {

    private String baseurl;

}
