package de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@ConfigurationProperties(prefix = "spiele-offensive.root")
public class SpieleOffensiveRootConfig {

    private String url;

    private String cmsSelector;

    private String cmsImageFrameSelector;

    private List<String> ignoreImg;

    private List<String> ignoreUrl;

}
