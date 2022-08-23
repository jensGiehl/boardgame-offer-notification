package de.agiehl.boardgame.BoardgameOffersFinder.web.brettspielangebote;

import de.agiehl.boardgame.BoardgameOffersFinder.web.WebClient;
import de.agiehl.boardgame.BoardgameOffersFinder.web.WebClientException;
import de.agiehl.boardgame.BoardgameOffersFinder.web.WebResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;

@Service
@Slf4j
@AllArgsConstructor
public class BrettspielAngebotePriceFinderImpl implements BrettspielAngebotePriceFinder {

    private WebClient webClient;

    private BrettspielAngeboteConfig config;

    private BrettspielAngeboteNameStrategy nameStrategy;

    @Override
    public WebResult<BrettspielAngeboteDto> getCurrentPriceFor(String name) {
        name = nameStrategy.getNameForSearch(name);

        String url = config.getSearchUrl(name);
        Document document = null;

        try {
            document = webClient.loadDocumentFromUrl(url);
        } catch (WebClientException ex) {
            return WebResult.<BrettspielAngeboteDto>builder()
                    .status(WebResult.SearchStatus.ERROR)
                    .build();
        }

        if (!document.select(config.getNoResultsSelector()).isEmpty()) {
            log.debug("No results found for {}", name);
            return WebResult.<BrettspielAngeboteDto>builder()
                    .status(WebResult.SearchStatus.NOT_FOUND)
                    .build();
        }

        Elements allResults = webClient.getElements(document, config.getResultTableSelector());
        for (Element resultRow : allResults) {
            String currentPrice = webClient.getTextFromFirstElement(resultRow, config.getPriceSelector())
                    .replace(config.getPriceReplaceString(), "");

            String baName = webClient.getTextFromFirstElement(resultRow, config.getNameSelector());

            String bestPriceUrl = webClient.selectFirstChildAndGetAttributeValue(resultRow, config.getUrlSelector(), "href");

            String bestPrice = webClient.getTextFromFirstElement(resultRow, config.getBestPriceSelector());

            String providerName = webClient.selectFirstChildAndGetAttributeValue(resultRow, config.getProviderSelector(), "title");

            String bggLink = webClient.selectFirstChildAndGetAttributeValue(resultRow, config.getBggSelector(), "href");

            String bggRating = webClient.getTextFromFirstElement(resultRow, config.getBggSelector());

            BrettspielAngeboteDto dto = BrettspielAngeboteDto.builder()
                    .currentPrice(currentPrice)
                    .name(baName)
                    .bestPrice(bestPrice)
                    .provider(providerName)
                    .bggLink(bggLink)
                    .bggRating(bggRating)
                    .url(bestPriceUrl)
                    .build();

            log.debug("Found {}", dto);

            if (baName.equalsIgnoreCase(name) || allResults.size() == 1) {
                return WebResult.<BrettspielAngeboteDto>builder()
                        .status(WebResult.SearchStatus.FOUND)
                        .result(dto)
                        .build();
            }
        }

        if (!allResults.isEmpty()) {
            log.info("Found {} results but nothing matches to '{}'", allResults.size(), name);
        }

        return WebResult.<BrettspielAngeboteDto>builder()
                .status(WebResult.SearchStatus.NOT_FOUND)
                .build();
    }

    @Override
    public WebResult<BrettspielAngeboteBggDto> getCurrentPriceForBggItem(Long id) {
        String url = UriComponentsBuilder
                .fromHttpUrl(config.getBggLinkUrl())
                .path("/" + id)
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUriString();

        try {
            Document document = webClient.loadDocumentFromUrl(url);
            String price = webClient.selectFirstChildAndGetAttributeValue(document, config.getBggLinkPriceSelector(), "content");
            String currency = webClient.selectFirstChildAndGetAttributeValue(document, config.getBggLinkCurrencySelector(), "content");
            if (NumberUtils.isParsable(price)) {
                price = String.format("%,.2f %s", Double.parseDouble(price), currency);
            }

            BrettspielAngeboteBggDto dto = BrettspielAngeboteBggDto.builder().currentPrice(price).url(url).build();

            return WebResult.<BrettspielAngeboteBggDto>builder()
                    .status(WebResult.SearchStatus.FOUND)
                    .result(dto)
                    .build();
        } catch (WebClientException e) {
            return WebResult.<BrettspielAngeboteBggDto>builder()
                    .status(WebResult.SearchStatus.ERROR)
                    .build();
        }
    }

}
