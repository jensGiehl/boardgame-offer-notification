package de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.groupdeal;

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

        GroupDealDto.GroupDealDtoBuilder builder = GroupDealDto.builder()
                .url(dto.getLink());
        if (offerService.isOffer(dto)) {
            OfferDto offerData = offerService.getOfferData(dto);

            builder
                    .imgUrl(offerData.getImageUrl())
                    .name(offerData.getTitle())
                    .price(offerData.getPrice())
                    .validUntil(offerData.getValidUntil());
        } else {
            log.debug("'{}' is not an offer, so no information can be extract from CMS element", dto.getLink());
            builder.imgUrl(dto.getImageFrameUrl());
        }

        GroupDealDto groupDealDto = builder.build();
        return Optional.of(groupDealDto);
    }

    @Override
    public boolean isProcessable(SpieleOffensiveCmsElementDto dto) {
        return dto.getLink().contains(config.getLinkMustContain());
    }

}
