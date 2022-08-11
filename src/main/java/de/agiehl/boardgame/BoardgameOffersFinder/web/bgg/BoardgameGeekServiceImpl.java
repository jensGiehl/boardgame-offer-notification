package de.agiehl.boardgame.BoardgameOffersFinder.web.bgg;

import de.agiehl.bgg.BggDataFetcher;
import de.agiehl.bgg.httpclient.BggHttpClientException;
import de.agiehl.bgg.model.common.Rating;
import de.agiehl.bgg.model.search.SearchItem;
import de.agiehl.bgg.model.search.SearchItems;
import de.agiehl.bgg.model.thing.Item;
import de.agiehl.bgg.service.common.Type;
import de.agiehl.bgg.service.search.SearchQueryParameters;
import de.agiehl.bgg.service.thing.ThingQueryParameters;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class BoardgameGeekServiceImpl implements BoardgameGeekService {

    private final BggDataFetcher bggDataFetcher;

    private final BoardgameGeekSearchNameStrategy nameStrategy;

    private final BoardgameGeekConfig config;

    @Override
    public Optional<BoardgameGeekSearchDto> searchForBoardgame(String name) {
        List<SearchItem> searchResult = Collections.emptyList();
        try {
            search(name, true);

            if (nothingFound(searchResult)) {
                searchResult = search(name, false);

                if (nothingFound((searchResult))) {
                    String searchName = nameStrategy.getNameForSearch(name);
                    searchResult = search(searchName, true);

                    if (nothingFound((searchResult))) {
                        searchResult = search(searchName, false);
                    }
                }
            }
        } catch (BggHttpClientException ex) {
            log.warn(String.format("Exception while searching for '%s'", name), ex);
            // TODO
            return Optional.empty();
        }

        if (nothingFound((searchResult))) {
            return Optional.empty();
        }

        log.debug("Found {} entries for '{}'", searchResult.size(), name);
        SearchItem bestMatchingResult = searchResult.get(0);

        return Optional.of(
                BoardgameGeekSearchDto.builder()
                        .id(bestMatchingResult.getId())
                        .name(bestMatchingResult.getName().getValue())
                        .type(bestMatchingResult.getType())
                        .build());
    }

    private boolean nothingFound(List<SearchItem> searchResult) {
        return Objects.isNull(searchResult) || searchResult.isEmpty();
    }

    protected List<SearchItem> search(String searchName, boolean exact) {
        SearchQueryParameters queryParameters = SearchQueryParameters.builder()
                .query(searchName)
                .exact(exact)
                .type(Type.BOARDGAME)
                .build();

        log.info("Searching on BGG for '{}' (exact?: {})", queryParameters, exact);

        SearchItems searchResult = bggDataFetcher.search(queryParameters);

        int found = searchResult.getItem() != null ? searchResult.getItem().size() : 0;
        log.debug("Found {} results for '{}'", found, searchName);

        return searchResult.getItem();
    }

    @Override
    public Optional<BoardgameGeekDto> getBoardgameInfos(Long id) {
        ThingQueryParameters queryParameters = ThingQueryParameters.builder()
                .id(id)
                .stats(true)
                .type(Type.BOARDGAME)
                .type(Type.BOARDGAMEEXPANSION)
                .build();

        List<Item> foundItems = bggDataFetcher.loadThings(queryParameters);
        if (foundItems.isEmpty()) {
            log.error("No Boardgamegeek Infos found for ID {}", id);
            return Optional.empty();
        }

        if (foundItems.size() > 1) {
            log.warn("Found {} Boardgamegeek Infos for ID {}", foundItems.size(), id);
        }

        Item bggItem = foundItems.get(0);

        String bggUrl = UriComponentsBuilder.fromHttpUrl(config.getBaseurl())
                .path("/" + bggItem.getType().toLowerCase())
                .path("/" + bggItem.getId())
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUriString();


        Rating ratings = bggItem.getStatistics().getRatings();
        String bggRating = ratings.getAverage().getValue();
        if (NumberUtils.isParsable(bggRating)) {
            bggRating = String.format("%,.1f", Double.parseDouble(bggRating));
        }

        BoardgameGeekDto result = BoardgameGeekDto.builder()
                .rating(bggRating)
                .wanting(ratings.getWanting().getValue())
                .wishing(ratings.getWishing().getValue())
                .id(bggItem.getId())
                .bggLink(bggUrl)
                .build();

        log.info("Found Boardgamegeek Info {}", result);


        return Optional.of(result);
    }

    @Override
    public Optional<BoardgameGeekDto> searchAndGet(String name) {
        Optional<BoardgameGeekSearchDto> searchResult = searchForBoardgame(name);
        if (searchResult.isEmpty()) {
            return Optional.empty();
        }

        return  getBoardgameInfos(searchResult.get().getId());
    }

}
