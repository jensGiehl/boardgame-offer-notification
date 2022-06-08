package de.agiehl.boardgame.BoardgameOffersFinder.web.bgg;

import de.agiehl.bgg.BggDataFetcher;
import de.agiehl.bgg.model.common.Rating;
import de.agiehl.bgg.model.search.SearchItem;
import de.agiehl.bgg.model.search.SearchItems;
import de.agiehl.bgg.model.thing.Item;
import de.agiehl.bgg.service.common.Type;
import de.agiehl.bgg.service.search.SearchQueryParameters;
import de.agiehl.bgg.service.thing.ThingQueryParameters;
import de.agiehl.boardgame.BoardgameOffersFinder.persistent.EntityType;
import de.agiehl.boardgame.BoardgameOffersFinder.persistent.bgg.BggEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
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
        String searchName = nameStrategy.getNameForSearch(name);
        log.debug("Searching on BGG for {}", searchName);
        SearchQueryParameters queryParameters = SearchQueryParameters.builder()
                .query(searchName)
                .exact(false)
                .type(Type.BOARDGAME)
                .build();

        SearchItems searchResult = bggDataFetcher.search(queryParameters);
        List<SearchItem> items = searchResult.getItem();
        if (Objects.isNull(items) || items.isEmpty()) {
            log.debug("No result found for '{}'", name);
            return Optional.empty();
        }

        log.debug("Found {} entries for '{}'", items.size(), name);
        SearchItem bestMatchingResult = items.get(0);

        return Optional.of(
                BoardgameGeekSearchDto.builder()
                        .id(bestMatchingResult.getId())
                        .name(bestMatchingResult.getName().getValue())
                        .type(bestMatchingResult.getType())
                        .build());
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
