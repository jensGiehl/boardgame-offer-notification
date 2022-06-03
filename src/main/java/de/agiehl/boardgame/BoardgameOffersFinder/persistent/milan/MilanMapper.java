package de.agiehl.boardgame.BoardgameOffersFinder.persistent.milan;

import de.agiehl.boardgame.BoardgameOffersFinder.web.milan.MilanDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface MilanMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    MilanEntity toEntity(MilanDto dto);

}
