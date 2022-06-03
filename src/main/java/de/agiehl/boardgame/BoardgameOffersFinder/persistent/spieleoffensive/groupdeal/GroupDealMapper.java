package de.agiehl.boardgame.BoardgameOffersFinder.persistent.spieleoffensive.groupdeal;

import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.groupdeal.GroupDealDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface GroupDealMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "failCount", ignore = true)
    @Mapping(target = "quantity", ignore = true)
    GroupDealEntity toEntity(GroupDealDto dto);
}
