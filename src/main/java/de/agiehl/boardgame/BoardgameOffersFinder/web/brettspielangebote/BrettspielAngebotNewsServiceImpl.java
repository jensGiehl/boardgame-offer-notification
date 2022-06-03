package de.agiehl.boardgame.BoardgameOffersFinder.web.brettspielangebote;

import de.agiehl.boardgame.BoardgameOffersFinder.web.WebClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class BrettspielAngebotNewsServiceImpl implements BrettspielAngebotNewsService {

    private BrettspielAngeboteConfig config;

    private WebClient webClient;

    @Override
    public List<BrettspielAngebotNewsDto> getNews() {
        Document document = webClient.loadDocumentFromUrl(config.getNewsUrl());

        Elements news = webClient.getElements(document, config.getNewsSelector());

        return news.stream().map(this::toDto).collect(Collectors.toList());
    }

    private BrettspielAngebotNewsDto toDto(Element element) {
        Elements newsTitle = webClient.getElements(element, config.getNewsTitleSelector());
        if (newsTitle.isEmpty()) {
            log.warn("News Title not found!");
            return null;
        }

        String link = webClient.getAttribute(newsTitle.first(), "href");
        String title = newsTitle.first().text();

        String summary = webClient.getTextFromFirstElement(element, config.getNewsSummarySelector());

        return BrettspielAngebotNewsDto.builder()
                .description(summary)
                .title(title)
                .url(link)
                .build();
    }

}
