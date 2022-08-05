package de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.priceaction;

import de.agiehl.boardgame.BoardgameOffersFinder.alert.AlertService;
import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.FurtherProcessing;
import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.SpieleOffensiveCmsElementDto;
import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.SpieleOffensiveDto;
import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.offer.OfferService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Qualifier("priceAction")
@AllArgsConstructor
@Slf4j
public class PriceActionService implements FurtherProcessing {

    private final PriceActionConfig config;

    private final OfferService offerService;

    private AlertService alertService;

    @Override
    public Optional<SpieleOffensiveDto> process(SpieleOffensiveCmsElementDto dto) {
        if (!isProcessable(dto)) {
            return Optional.empty();
        }

        SpieleOffensiveDto.SpieleOffensiveDtoBuilder builder = SpieleOffensiveDto.builder()
                .url(dto.getLink());
        if (offerService.isOffer(dto)) {
            return Optional.ofNullable(offerService.getOfferData(dto));
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
