package de.agiehl.boardgame.BoardgameOffersFinder.web.bgg;

import de.agiehl.bgg.BggDataFetcher;
import de.agiehl.bgg.httpclient.BggHttpClientException;
import de.agiehl.bgg.model.common.Rank;
import de.agiehl.bgg.model.common.Ranks;
import de.agiehl.bgg.model.common.Rating;
import de.agiehl.bgg.model.search.SearchItem;
import de.agiehl.bgg.model.search.SearchItems;
import de.agiehl.bgg.model.thing.Item;
import de.agiehl.bgg.service.common.Type;
import de.agiehl.bgg.service.search.SearchQueryParameters;
import de.agiehl.bgg.service.thing.ThingQueryParameters;
import de.agiehl.boardgame.BoardgameOffersFinder.web.WebResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class BoardgameGeekServiceImpl implements BoardgameGeekService {

    private final BggDataFetcher bggDataFetcher;

    private final BoardgameGeekSearchNameStrategy nameStrategy;

    private final BoardgameGeekConfig config;

    @Override
    public WebResult<BoardgameGeekSearchDto> searchForBoardgame(String name, Integer nameCounter) {
        List<SearchItem> searchResult = Collections.emptyList();

        if (nameCounter == null) {
            nameCounter = 0;
        }

        try {
            // Exact search with full name
            if (nameCounter == 0) {
                searchResult = search(name, true);
                if (nothingFound(searchResult)) {
                    nameCounter++;
                }
            }

            // Non-exact search with full name
            if (nameCounter == 1) {
                searchResult = search(name, false);
                if (nothingFound(searchResult)) {
                    nameCounter++;
                }
            }

            if (nameCounter >= 2) {
                String searchName = nameStrategy.getNameForSearch(name);

                // Exact search with extracted name
                if (nameCounter == 2) {
                    searchResult = search(searchName, true);
                    if (nothingFound(searchResult)) {
                        nameCounter++;
                    }
                }

                // Non-exact search with extracted name
                if (nameCounter == 3) {
                    searchResult = search(searchName, false);
                    if (nothingFound(searchResult)) {
                        nameCounter++;
                    }
                }
            }
        } catch (BggHttpClientException ex) {
            log.warn("Exception while searching for '{}': {}", name, ex);
            return WebResult.<BoardgameGeekSearchDto>builder()
                    .status(WebResult.SearchStatus.ERROR)
                    .errorCounter(nameCounter)
                    .build();
        }

        if (nothingFound((searchResult))) {
            return WebResult.<BoardgameGeekSearchDto>builder()
                    .status(WebResult.SearchStatus.NOT_FOUND)
                    .build();
        }

        log.debug("Found {} entries for '{}'", searchResult.size(), name);
        SearchItem bestMatchingResult = searchResult.get(0);

        BoardgameGeekSearchDto dto = BoardgameGeekSearchDto.builder()
                .id(bestMatchingResult.getId())
                .name(bestMatchingResult.getName().getValue())
                .type(bestMatchingResult.getType())
                .searchStatus(BoardgameGeekSearchDto.SearchStatus.FINISHED)
                .build();

        return WebResult.<BoardgameGeekSearchDto>builder()
                .status(WebResult.SearchStatus.FOUND)
                .result(dto)
                .build();
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

        log.info("Searching on BGG for '{}'", queryParameters);

        SearchItems searchResult = bggDataFetcher.search(queryParameters);

        int found = searchResult.getItem() != null ? searchResult.getItem().size() : 0;
        log.debug("Found {} results for '{}'", found, searchName);

        return searchResult.getItem();
    }

    @Override
    public WebResult<BoardgameGeekDto> getBoardgameInfos(Long id) {
        ThingQueryParameters queryParameters = ThingQueryParameters.builder()
                .id(id)
                .stats(true)
                .type(Type.BOARDGAME)
                .type(Type.BOARDGAMEEXPANSION)
                .build();

        List<Item> foundItems;
        try {
            foundItems = bggDataFetcher.loadThings(queryParameters);
        } catch (BggHttpClientException ex) {
            log.warn("Exception while getting boardgame for ID '{}: {}", id, ex);
            return WebResult.<BoardgameGeekDto>builder()
                    .status(WebResult.SearchStatus.ERROR)
                    .build();
        }

        if (foundItems.isEmpty()) {
            log.error("No Boardgamegeek Infos found for ID {}", id);
            return WebResult.<BoardgameGeekDto>builder()
                    .status(WebResult.SearchStatus.NOT_FOUND)
                    .build();
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

        Long bggRank = getBggRank(ratings);

        BoardgameGeekDto result = BoardgameGeekDto.builder()
                .rating(bggRating)
                .userRated(Long.valueOf(ratings.getUsersrated().getValue()))
                .wanting(ratings.getWanting().getValue())
                .wishing(ratings.getWishing().getValue())
                .id(bggItem.getId())
                .bggLink(bggUrl)
                .bggRank(bggRank)
                .build();

        log.info("Found Boardgamegeek Info {}", result);


        return WebResult.<BoardgameGeekDto>builder()
                .status(WebResult.SearchStatus.FOUND)
                .result(result)
                .build();
    }

    private Long getBggRank(Rating ratings) {
        if (Objects.isNull(ratings)) {
            return null;
        }

        Ranks ranks = ratings.getRanks();
        if (Objects.isNull(ranks)) {
            return null;
        }

        List<Rank> rankList = ranks.getRank();
        if (Objects.isNull(rankList)) {
            return null;
        }

        return rankList.stream()
                .filter(r -> r.getName().equalsIgnoreCase("boardgame"))
                .filter(r -> Objects.nonNull(r.getValue()))
                .filter(r -> NumberUtils.isParsable(r.getValue()))
                .map(r -> Long.parseLong(r.getValue()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public WebResult<BoardgameGeekDto> searchAndGet(String name, Integer nameCounter) {
        WebResult<BoardgameGeekSearchDto> searchResult = searchForBoardgame(name, nameCounter);

        if (searchResult.getStatus() != WebResult.SearchStatus.FOUND) {
            return WebResult.<BoardgameGeekDto>builder()
                    .status(searchResult.getStatus())
                    .errorCounter(searchResult.getErrorCounter())
                    .build();
        }

        return getBoardgameInfos(searchResult.getResult().getId());
    }

}
