package de.agiehl.boardgame.BoardgameOffersFinder.web;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate getRestTemplate(RestTemplateBuilder builder, WebClientConfig config) {
        return builder
                .setReadTimeout(config.getTimeout())
                .setConnectTimeout(config.getTimeout())
                .defaultHeader("User-Agent", config.getUserAgent())
                .defaultHeader("Accept-Language", config.getAcceptLanguage())
                .build();
    }

}
