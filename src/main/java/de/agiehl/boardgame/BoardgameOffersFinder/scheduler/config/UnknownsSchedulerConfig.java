package de.agiehl.boardgame.BoardgameOffersFinder.scheduler.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "unknowns.scheduler")
@Component
public class UnknownsSchedulerConfig {

    private Boolean enable;

}
