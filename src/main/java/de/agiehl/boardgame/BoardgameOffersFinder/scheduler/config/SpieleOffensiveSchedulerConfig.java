package de.agiehl.boardgame.BoardgameOffersFinder.scheduler.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "spiele-offensive.scheduler")
@Component
public class SpieleOffensiveSchedulerConfig {

    private Boolean enable;

}
