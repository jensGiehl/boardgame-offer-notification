package de.agiehl.boardgame.BoardgameOffersFinder.notify.spieleoffensive;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "spiele-offensive.notify")
public class SpieleOffensiveNotifyConfig {

    private String datePattern;
}
