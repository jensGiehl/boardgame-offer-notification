package de.agiehl.boardgame.BoardgameOffersFinder.web.bgg;

import de.agiehl.bgg.BggDataFetcher;
import de.agiehl.bgg.model.search.SearchItem;
import de.agiehl.bgg.model.search.SearchItems;
import de.agiehl.bgg.service.common.Type;
import de.agiehl.bgg.service.search.SearchQueryParameters;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class BoardgameGeekServiceImpl implements BoardgameGeekService {

    private final BggDataFetcher bggDataFetcher;

    private final BoardgameGeekSearchNameStrategy nameStrategy;

    @Override
    public Optional<BoardgameGeekDto> searchForBoardgame(String name) {
        String searchName = nameStrategy.getNameForSearch(name);
        log.debug("Searching on BGG for {}", searchName);
        SearchQueryParameters queryParameters = SearchQueryParameters.builder()
                .query(searchName)
                .exact(true)
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
                BoardgameGeekDto.builder()
                        .id(bestMatchingResult.getId())
                        .name(bestMatchingResult.getName().getValue())
                        .type(bestMatchingResult.getType())
                        .build());
    }

}
