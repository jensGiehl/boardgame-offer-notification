package de.agiehl.boardgame.BoardgameOffersFinder.notify;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "notify")
@Component
public class NotifyConfig {

    private Boolean enable;

    private Integer maxFailCount;

}
