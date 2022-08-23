package de.agiehl.boardgame.BoardgameOffersFinder.web.brettspielangebote;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Data
@ConfigurationProperties(prefix = "brettspiel-angebote")
@Component
public class BrettspielAngeboteConfig {

    private String newsUrl;

    private String newsSelector;

    private String newsSummarySelector;

    private String newsTitleSelector;

    private String searchUrl;

    private String resultTableSelector;

    private String priceSelector;

    private String priceReplaceString;

    private String nameSelector;

    private String bestPriceSelector;

    private String providerSelector;

    private String bggSelector;

    private String bggLinkUrl;

    private String bggLinkPriceSelector;

    private String bggLinkCurrencySelector;

    private String urlSelector;

    private String noResultsSelector;

    public String getSearchUrl(String searchWord) {
        return searchUrl + URLEncoder.encode(searchWord, StandardCharsets.UTF_8);
    }

}
