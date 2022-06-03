package de.agiehl.boardgame.BoardgameOffersFinder.web.brettspielangebote;

import de.agiehl.boardgame.BoardgameOffersFinder.web.WebClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class BrettspielAngebotePriceFinderImpl implements BrettspielAngebotePriceFinder {

    private WebClient webClient;

    private BrettspielAngeboteConfig config;

    @Override
    public Optional<BrettspielAngeboteDto> getCurrentPriceFor(String name) {
        String url = config.getSearchUrl() + URLEncoder.encode(name, StandardCharsets.UTF_8);
        Document document = webClient.loadDocumentFromUrl(url);

        Elements allResults = webClient.getElements(document, config.getResultTableSelector());
        for (Element resultRow : allResults) {
            String currentPrice = webClient.getTextFromFirstElement(resultRow, config.getPriceSelector())
                    .replace(config.getPriceReplaceString(), "");

            String baName = webClient.getTextFromFirstElement(resultRow, config.getNameSelector());

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
                    .build();

            log.debug("Found {}", dto);

            if (baName.equalsIgnoreCase(name)) {
                return Optional.of(dto);
            }
        }

        if (!allResults.isEmpty()) {
            log.info("Found {} results but nothing matches to '{}'", allResults.size(), name);
        }

        return Optional.empty();
    }

}
