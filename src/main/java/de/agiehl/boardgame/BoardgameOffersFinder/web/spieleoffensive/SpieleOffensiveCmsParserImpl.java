package de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive;

import de.agiehl.boardgame.BoardgameOffersFinder.alert.AlertService;
import de.agiehl.boardgame.BoardgameOffersFinder.web.WebClient;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class SpieleOffensiveCmsParserImpl implements SpieleOffensiveCmsParser {

    private final WebClient webClient;

    private final SpieleOffensiveRootConfig config;

    private final AlertService alertService;

    private final FurtherProcessing priceActionService;

    private final FurtherProcessing schmiedeService;

    private final FurtherProcessing groupDealService;

    private final FurtherProcessing productHighlightService;

    public SpieleOffensiveCmsParserImpl(WebClient webClient,
                                        SpieleOffensiveRootConfig config,
                                        AlertService alertService,
                                        @Qualifier("priceAction") FurtherProcessing priceActionService,
                                        @Qualifier("schmiede") FurtherProcessing schmiedeService,
                                        @Qualifier("groupdeal") FurtherProcessing groupDealService,
                                        @Qualifier("product") FurtherProcessing productHighlightService) {
        this.webClient = webClient;
        this.config = config;
        this.alertService = alertService;
        this.priceActionService = priceActionService;
        this.schmiedeService = schmiedeService;
        this.groupDealService = groupDealService;
        this.productHighlightService = productHighlightService;
    }

    @Override
    public List<SpieleOffensiveDto> parseRootPage() {
        Document document = webClient.loadDocumentFromUrl(config.getUrl());

        List<SpieleOffensiveDto> result = new ArrayList<>();
        Elements cmsElements = getCmsElements(document);
        for (Element cmsElement : cmsElements) {
            String link = webClient.getAttribute(cmsElement, "href");
            String imageFrameUrl = webClient.selectFirstChildAndGetAttributeValue(cmsElement, config.getCmsImageFrameSelector(), "src");

            if (shouldBeIgnored(link, imageFrameUrl)) {
                continue;
            }

            SpieleOffensiveCmsElementDto dto = SpieleOffensiveCmsElementDto.builder()
                    .element(cmsElement)
                    .link(link)
                    .imageFrameUrl(imageFrameUrl)
                    .build();

            Optional<SpieleOffensiveDto> spieleOffensiveDto = process(link, dto);
            spieleOffensiveDto.ifPresent(result::add);
        }

        return result;
    }

    private Optional<SpieleOffensiveDto> processIfAbsent(Optional<SpieleOffensiveDto> lastResult,
                                                         SpieleOffensiveCmsElementDto dto,
                                                         FurtherProcessing processor) {
        if (lastResult.isPresent()) {
            String message = String.format("CMS Element (URL: '%s') is multiple processed!", dto.getLink());
            alertService.sendAlert(message);
        }

        return processor.process(dto);
    }

    private Optional<SpieleOffensiveDto> process(String link, SpieleOffensiveCmsElementDto dto) {
        Optional<SpieleOffensiveDto> spieleOffensiveDto = Optional.empty();

        if (groupDealService.isProcessable(dto)) {
            spieleOffensiveDto = processIfAbsent(spieleOffensiveDto, dto, groupDealService);
        }

        if (priceActionService.isProcessable(dto)) {
            spieleOffensiveDto = processIfAbsent(spieleOffensiveDto, dto, priceActionService);
        }

        if (productHighlightService.isProcessable(dto)) {
            spieleOffensiveDto = processIfAbsent(spieleOffensiveDto, dto, productHighlightService);
        }

        if (schmiedeService.isProcessable(dto)) {
            spieleOffensiveDto = processIfAbsent(spieleOffensiveDto, dto, schmiedeService);
        }

        if (spieleOffensiveDto.isEmpty()) {
            alertService.sendAlert(String.format("Unknown CMS type. URL '%s'", link));
        }

        return spieleOffensiveDto;
    }

    private boolean shouldBeIgnored(String link, String imgUrl) {
        if (link.isEmpty() || imgUrl.isEmpty()) {
            log.warn("Ignore CMS content because link/img is empty");
            return true;
        }

        if (config.getIgnoreImg().contains(imgUrl)) {
            log.debug("Ignore CMS content because image '{}' should be ignored", imgUrl);
            return true;
        }

        if (config.getIgnoreUrl().contains(link)) {
            log.debug("Ignore CMS content because link '{}' should be ignored", link);
            return true;
        }

        log.debug("CMS content with link '{}' and image '{}' should not be ignored", link, imgUrl);
        return false;
    }

    private Elements getCmsElements(Document document) {
        Elements cmsContent = document.select(config.getCmsSelector());

        if (cmsContent.isEmpty()) {
            String message = String.format("No CMS Elements found on URL '%s'. Using selector: '%s'", config.getUrl(), config.getCmsSelector());
            alertService.sendAlert(message);
        } else {
            log.debug("Found {} CMS Elements on URL '{}'", cmsContent.size(), config.getUrl());
        }

        return cmsContent;
    }

}
