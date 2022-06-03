package de.agiehl.boardgame.BoardgameOffersFinder.persistent.spieleoffensive.priceaction;

import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.priceaction.PriceActionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PriceActionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    PriceActionEntity toEntity(PriceActionDto dto);

}
