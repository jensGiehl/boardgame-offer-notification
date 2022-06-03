package de.agiehl.boardgame.BoardgameOffersFinder.web.unknowns;

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
public class UnknownsServiceImpl implements UnknownsService {

    private WebClient webClient;

    private UnknownsConfig config;

    @Override
    public List<UnknownsDto> getAllThreads() {
        Document document = webClient.loadDocumentFromUrl(config.getUrl());

        Elements articles = webClient.getElements(document, config.getArticleSelector());

        return articles.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private UnknownsDto toDto(Element element) {
        String name = element.text();
        String link = webClient.getAttribute(element, "href");

        return UnknownsDto.builder()
                .title(name)
                .url(link)
                .build();
    }


}
