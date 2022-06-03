package de.agiehl.boardgame.BoardgameOffersFinder.persistent.notify;

import java.util.Optional;

public interface NotifyPersistenceService {
    Optional<Long> getMessageIdForUrl(String url);

    void saveCommunication(NotifyEntity entity);
}
