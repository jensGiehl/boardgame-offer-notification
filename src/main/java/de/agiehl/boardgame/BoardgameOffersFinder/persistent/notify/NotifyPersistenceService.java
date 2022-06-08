package de.agiehl.boardgame.BoardgameOffersFinder.persistent.notify;

import de.agiehl.boardgame.BoardgameOffersFinder.notify.sender.NotifyResponse;
import de.agiehl.boardgame.BoardgameOffersFinder.persistent.EntityType;

import java.util.Optional;

public interface NotifyPersistenceService {

    void saveCommunication(NotifyResponse notifyResponse, Long entityId, EntityType entityType, NotifyEntity.MessageType messageType);

    Optional<NotifyEntity> findNotification(Long entityId, EntityType entityType);
}
