package de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.offer;

import de.agiehl.boardgame.BoardgameOffersFinder.web.WebClient;
import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.SpieleOffensiveCmsElementDto;
import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.SpieleOffensiveDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
@AllArgsConstructor
@Slf4j
public class OfferServiceImpl implements OfferService {

    private final OfferConfig  config;

    private final WebClient webClient;

    @Override
    public SpieleOffensiveDto getOfferData(SpieleOffensiveCmsElementDto dto) {
        Element element = dto.getElement();
        String productImage = webClient.selectFirstChildAndGetAttributeValue(element, config.getProductImageSelector(), "src");
        String productPrice = getPrice(element);
        String productTitle = webClient.getTextFromFirstElement(element, config.getProductTitleSelector());
        LocalDate productEndDate = getEndDate(element);

        return SpieleOffensiveDto.builder()
                .price(productPrice)
                .name(productTitle)
                .validUntil(productEndDate)
                .imgUrl(dto.getRootUrl() + productImage)
                .url(dto.getLink())
                .build();
    }

    private LocalDate getEndDate(Element element) {
        String endDateText = webClient.getTextFromFirstElement(element, config.getProductEndDateSelector());
        String cutEndDate = endDateText.replace(config.getProductEndDateReplaceString(), "").trim();

        try {
            return LocalDate.parse(cutEndDate, DateTimeFormatter.ofPattern(config.getProductEndDatePattern()));
        } catch (DateTimeParseException ex) {
            String message = String.format("Couldn't parse end date '%s' to date (%s)", cutEndDate, config.getProductEndDatePattern());
            log.warn(message, ex);

            return null;
        }
    }

    private String getPrice(Element element) {
        String priceRaw = webClient.getTextFromFirstElement(element, config.getProductPriceSelector());
        return priceRaw.replace(config.getProductPriceReplaceString(), "").trim();
    }

    @Override
    public boolean isOffer(SpieleOffensiveCmsElementDto dto) {
        return dto.getImageFrameUrl().contains(config.getImagePrefix());
    }

}
