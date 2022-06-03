package de.agiehl.boardgame.BoardgameOffersFinder.persistent.spieleoffensive.groupdeal;

import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.groupdeal.GroupDealDto;

public interface GroupDealPersistenceService {

     boolean saveIfNew(GroupDealDto dto);
}
