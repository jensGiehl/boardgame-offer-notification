package de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.product;

import de.agiehl.boardgame.BoardgameOffersFinder.web.WebClient;
import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.FurtherProcessing;
import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.SpieleOffensiveCmsElementDto;
import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.SpieleOffensiveDto;
import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.offer.OfferService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("product")
@AllArgsConstructor
@Slf4j
public class ProductHighlightService  implements FurtherProcessing {

    private final ProductHighlightConfig config;

    private final OfferService offerService;

    private final WebClient webClient;

    @Override
    public SpieleOffensiveDto process(SpieleOffensiveCmsElementDto dto) {
        if (offerService.isOffer(dto)) {
            return offerService.getOfferData(dto);
        } else {
            String description = webClient.getAttribute(dto.getImageElement(), "alt");

            Document productPage = webClient.loadDocumentFromUrl(dto.getLink());
            String name = webClient.getTextFromFirstElement(productPage, config.getNameSelector());
            String price = webClient.getTextFromFirstElement(productPage, config.getPriceSelector());

            return SpieleOffensiveDto.builder()
                    .url(dto.getLink())
                    .imgUrl(dto.getImageFrameUrl())
                    .description(description)
                    .name(name)
                    .price(price)
                    .build();
        }
    }

    @Override
    public boolean isProcessable(SpieleOffensiveCmsElementDto dto) {
        return dto.getLink().contains(config.getLinkMustContain());
    }
}
