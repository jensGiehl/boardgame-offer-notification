package de.agiehl.boardgame.BoardgameOffersFinder.web.bgg;

import de.agiehl.bgg.BggDataFetcher;
import de.agiehl.bgg.config.BggConfig;
import de.agiehl.bgg.config.HttpConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class BoardgameGeekClientConfig {

    @Bean
    public BggDataFetcher getBggDataFetcher(BoardgameGeekConfig config) {
        BggConfig bggConfig = BggConfig.getDefault();

        HttpConfig httpConfig = HttpConfig.builder()
                .connectionTimeout(Duration.ofSeconds(config.getConnectionTimeoutInSeconds()))
                .requestTimeout(Duration.ofSeconds(config.getReadTimeoutInSeconds()))
                .maxRetries(config.getMaxRetries())
                .waitBetweenRetires(Duration.ofSeconds(config.getRetryDelayInSeconds()))
                .build();

        bggConfig.setHttpConfig(httpConfig);

        return new BggDataFetcher(bggConfig);
    }
}
