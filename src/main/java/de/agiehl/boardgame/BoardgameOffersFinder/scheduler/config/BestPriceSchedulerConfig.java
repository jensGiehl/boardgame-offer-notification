package de.agiehl.boardgame.BoardgameOffersFinder.scheduler.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "brettspiel-angebote.scheduler")
@Component
public class BestPriceSchedulerConfig {

    private Boolean enable;

    private Integer maxFailCount;

}
