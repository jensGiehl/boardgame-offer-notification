package de.agiehl.boardgame.BoardgameOffersFinder.persistent.milan;

import de.agiehl.boardgame.BoardgameOffersFinder.web.milan.MilanDto;

public interface MilanPersistencePersistenceService {
    boolean saveIfNewOrModified(MilanDto dto);
}
