package de.agiehl.boardgame.BoardgameOffersFinder.web;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Data
@ConfigurationProperties(prefix = "web")
@Component
public class WebClientConfig {

    private String userAgent;

    private Duration timeout;

    private String acceptLanguage;
}
