package de.agiehl.boardgame.BoardgameOffersFinder.notify;

import de.agiehl.boardgame.BoardgameOffersFinder.persistent.DataEntity;

public interface NotifyService {

    void notify(DataEntity entity);
}
