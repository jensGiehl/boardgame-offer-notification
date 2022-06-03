package de.agiehl.boardgame.BoardgameOffersFinder.persistent.unknowns;

import de.agiehl.boardgame.BoardgameOffersFinder.web.unknowns.UnknownsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UnknownsMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    UnknownsEntity toEntity(UnknownsDto dto);

}
