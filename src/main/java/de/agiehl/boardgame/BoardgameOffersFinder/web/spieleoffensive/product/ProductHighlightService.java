package de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.product;

import de.agiehl.boardgame.BoardgameOffersFinder.alert.AlertService;
import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.FurtherProcessing;
import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.SpieleOffensiveCmsElementDto;
import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.SpieleOffensiveDto;
import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.offer.OfferDto;
import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.offer.OfferService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Qualifier("product")
@AllArgsConstructor
@Slf4j
public class ProductHighlightService  implements FurtherProcessing {

    private final ProductHighlightConfig config;

    private final OfferService offerService;

    private final AlertService alertService;

    @Override
    public Optional<SpieleOffensiveDto> process(SpieleOffensiveCmsElementDto dto) {
        if (!isProcessable(dto)) {
            return Optional.empty();
        }

        SpieleOffensiveProductDto.SpieleOffensiveProductDtoBuilder builder = SpieleOffensiveProductDto.builder()
                .url(dto.getLink());

        if (offerService.isOffer(dto)) {
            OfferDto offerData = offerService.getOfferData(dto);

            builder.imgUrl(offerData.getImageUrl())
                    .endDate(offerData.getValidUntil())
                    .name(offerData.getTitle())
                    .price(offerData.getPrice());
        } else {
            builder.imgUrl(dto.getImageFrameUrl());
            // TODO
            alertService.sendAlert("TODO: Parse Path " + dto.getLink());
        }

        return Optional.of(builder.build());
    }

    @Override
    public boolean isProcessable(SpieleOffensiveCmsElementDto dto) {
        return dto.getLink().contains(config.getLinkMustContain());
    }
}
