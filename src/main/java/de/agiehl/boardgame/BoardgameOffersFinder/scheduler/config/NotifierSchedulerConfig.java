package de.agiehl.boardgame.BoardgameOffersFinder.scheduler.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "notify")
@Component
public class NotifierSchedulerConfig {

    private long maxAge;

    private Boolean enable;

}
