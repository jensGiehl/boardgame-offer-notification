package de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.groupdeal;

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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Qualifier("groupdeal")
@Slf4j
@AllArgsConstructor
public class GroupDealService implements FurtherProcessing {

    private final GroupDealConfig config;

    private final OfferService offerService;

    private final WebClient webClient;

    @Override
    public SpieleOffensiveDto process(SpieleOffensiveCmsElementDto dto) {
        Document groupDealPage = webClient.loadDocumentFromUrl(dto.getLink());
        String stockText = webClient
                .getElements(groupDealPage, config.getStockSelector())
                .get(1)
                .text();

        stockText = removeLeadingStockText(stockText);

        if (offerService.isOffer(dto)) {
            SpieleOffensiveDto offerData = offerService.getOfferData(dto);
            offerData.setStock(stockText);

            return offerData;
        } else {
            log.debug("'{}' is not an offer, so no information can be extract from CMS element", dto.getLink());

            String description = webClient.getAttribute(dto.getImageElement(), "alt");
            String name = webClient.getTextFromFirstElement(groupDealPage, config.getNameSelector());
            String price = webClient.selectFirstChildAndGetAttributeValue(groupDealPage, config.getPriceSelector(), "value") + " EUR";

            return SpieleOffensiveDto.builder()
                    .url(dto.getLink())
                    .imgUrl(dto.getImageFrameUrl())
                    .name(name)
                    .description(description)
                    .stock(stockText)
                    .price(price)
                    .build();
        }
    }

    private String removeLeadingStockText(String stockText) {
        Matcher matcher = Pattern.compile("\\d+").matcher(stockText);
        matcher.find();

        return stockText.substring(stockText.indexOf(matcher.group()));
    }

    @Override
    public boolean isProcessable(SpieleOffensiveCmsElementDto dto) {
        return dto.getLink().contains(config.getLinkMustContain());
    }

}
