package de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.schmiede;

import de.agiehl.boardgame.BoardgameOffersFinder.alert.AlertService;
import de.agiehl.boardgame.BoardgameOffersFinder.web.WebClient;
import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.FurtherProcessing;
import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.SpieleOffensiveCmsElementDto;
import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.SpieleOffensiveDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("schmiede")
@AllArgsConstructor
@Slf4j
public class SpieleschmiedeService implements FurtherProcessing {

    private final SpieleschmiedeConfig config;

    private final WebClient webClient;

    private final AlertService alertService;

    @Override
    public SpieleOffensiveDto process(SpieleOffensiveCmsElementDto dto) {
        Document spieleschmiedePage = webClient.loadDocumentFromUrl(dto.getLink());

        String contentUrl = webClient.selectFirstChildAndGetAttributeValue(spieleschmiedePage, config.getDetailPageSelector(), "src");
        if (contentUrl.isEmpty()) {
            log.error("Couldn't get content iframe for '{}'", dto.getLink());
            return SpieleOffensiveDto.builder()
                    .url(dto.getLink())
                    .imgUrl(dto.getImageFrameUrl())
                    .build();
        }

        Document contentPage = webClient.loadDocumentFromUrl(contentUrl);
        String title = webClient.getTextFromFirstElement(contentPage, config.getProductTitleSelector());

        return SpieleOffensiveDto.builder()
                .imgUrl(dto.getImageFrameUrl())
                .url(dto.getLink())
                .name(title)
                .build();
    }

    @Override
    public boolean isProcessable(SpieleOffensiveCmsElementDto dto) {
        return dto.getLink().contains(config.getLinkMustContain());
    }
}
