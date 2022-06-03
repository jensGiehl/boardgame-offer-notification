package de.agiehl.boardgame.BoardgameOffersFinder.persistent.brettspielangebote;

import de.agiehl.boardgame.BoardgameOffersFinder.web.brettspielangebote.BrettspielAngebotNewsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BrettspielAngeboteNewsMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    BrettspielAngeboteNewsEntity toEntity(BrettspielAngebotNewsDto dto);

}
