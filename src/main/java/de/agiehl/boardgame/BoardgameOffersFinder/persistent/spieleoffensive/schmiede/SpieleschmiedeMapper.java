package de.agiehl.boardgame.BoardgameOffersFinder.persistent.spieleoffensive.schmiede;

import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.schmiede.SpieleschmiedeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface SpieleschmiedeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    SpieleschmiedeEntity toEntity(SpieleschmiedeDto dto);

}
