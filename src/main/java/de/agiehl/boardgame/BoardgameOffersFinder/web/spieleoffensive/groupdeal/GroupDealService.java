package de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.groupdeal;

import de.agiehl.boardgame.BoardgameOffersFinder.web.WebClient;
import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.FurtherProcessing;
import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.SpieleOffensiveCmsElementDto;
import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.SpieleOffensiveDto;
import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.offer.OfferService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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
        if (offerService.isOffer(dto)) {
            return offerService.getOfferData(dto);
        } else {
            log.debug("'{}' is not an offer, so no information can be extract from CMS element", dto.getLink());

            String name = webClient.getAttribute(dto.getImageElement(), "alt");

            return SpieleOffensiveDto.builder()
                    .url(dto.getLink())
                    .imgUrl(dto.getImageFrameUrl())
                    .name(name)
                    .build();
        }
    }

    @Override
    public boolean isProcessable(SpieleOffensiveCmsElementDto dto) {
        return dto.getLink().contains(config.getLinkMustContain());
    }

}
