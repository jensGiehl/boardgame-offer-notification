package de.agiehl.boardgame.BoardgameOffersFinder.web.milan;

import de.agiehl.boardgame.BoardgameOffersFinder.alert.AlertService;
import de.agiehl.boardgame.BoardgameOffersFinder.web.WebClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class MilanServiceImpl implements MilanService {

    private final MilanConfig config;

    private final WebClient webClient;

    private final AlertService alertService;

    @Override
    public List<MilanDto> parseSaleOffers() {
        Document rootDocument = webClient.loadDocumentFromUrl(config.getUrl());

        Elements products = rootDocument.select(config.getProductListSelector());
        if (products.isEmpty()) {
            String message = String.format("No products found on '%s' (Selector: %s)", config.getUrl(), config.getProductListSelector());
            alertService.sendAlert(message);
            return Collections.emptyList();
        }

        return products.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private MilanDto toDto(Element product) {
        String title = webClient.getTextFromFirstElement(product, config.getProductTitleSelector());
        String price = webClient.getTextFromFirstElement(product, config.getProductPriceSelector());
        String delivery = webClient.getTextFromFirstElement(product, config.getProductDeliverySelector());
        String link = webClient.selectFirstChildAndGetAttributeValue(product, config.getProductLinkSelector(), "href");
        String image = webClient.selectFirstChildAndGetAttributeValue(product, config.getProductImgSelector(), "src");

        if (image.contains(config.getProductImgReplaceStr())) {
            image = image.replace(config.getProductImgReplaceStr(), "");
        }

        if (image.isEmpty()) {
            image = null;
        } else {
            image = config.getImageUrlPrefix() + image;
        }

        return MilanDto.builder()
                .url(link)
                .stockText(delivery)
                .name(title)
                .imgUrl(image)
                .price(price)
                .build();
    }
}
