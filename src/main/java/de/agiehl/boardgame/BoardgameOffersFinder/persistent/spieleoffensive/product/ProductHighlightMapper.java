package de.agiehl.boardgame.BoardgameOffersFinder.persistent.spieleoffensive.product;

import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.product.SpieleOffensiveProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ProductHighlightMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    ProductHighlightEntity toEntity(SpieleOffensiveProductDto dto);

}
