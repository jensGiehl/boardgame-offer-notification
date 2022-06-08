package de.agiehl.boardgame.BoardgameOffersFinder.web.bgg;

import de.agiehl.bgg.BggDataFetcher;
import de.agiehl.bgg.config.BggConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BoardgameGeekClientConfig {

    @Bean
    public BggDataFetcher getBggDataFetcher() {
        BggConfig bggConfig = BggConfig.getDefault();

        return new BggDataFetcher(bggConfig);
    }
}
