package de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.groupdeal;

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
@Qualifier("groupdeal")
@Slf4j
@AllArgsConstructor
public class GroupDealService implements FurtherProcessing {

    private final GroupDealConfig config;

    private final OfferService offerService;

    @Override
    public Optional<SpieleOffensiveDto> process(SpieleOffensiveCmsElementDto dto) {
        if (!isProcessable(dto)) {
            log.debug("{} is not an group deal", dto);
            return Optional.empty();
        }

        if (offerService.isOffer(dto)) {
            return Optional.ofNullable(offerService.getOfferData(dto));
        } else {
            log.debug("'{}' is not an offer, so no information can be extract from CMS element", dto.getLink());
            return Optional.ofNullable(SpieleOffensiveDto.builder()
                    .url(dto.getLink())
                    .imgUrl(dto.getImageFrameUrl())
                    .build());
        }
    }

    @Override
    public boolean isProcessable(SpieleOffensiveCmsElementDto dto) {
        return dto.getLink().contains(config.getLinkMustContain());
    }

}
