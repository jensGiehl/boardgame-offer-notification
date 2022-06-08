package de.agiehl.boardgame.BoardgameOffersFinder.persistent.notify;

import de.agiehl.boardgame.BoardgameOffersFinder.notify.sender.NotifyResponse;
import de.agiehl.boardgame.BoardgameOffersFinder.persistent.EntityType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class NotifyPersistenceServiceImpl implements NotifyPersistenceService {

    private final NotifyRepository repository;

    public void saveCommunication(NotifyResponse notifyResponse, Long entityId, EntityType entityType, NotifyEntity.MessageType messageType) {
        Optional<NotifyEntity> oldEntity = repository.findByFkIdAndFkType(entityId, entityType);

        NotifyEntity entityToSave = null;

        if (oldEntity.isPresent()) {
            log.info("Overriding old {} with new notification {}", oldEntity, notifyResponse);
            entityToSave = oldEntity.get();
            entityToSave.setChatId(notifyResponse.getChatId());
            entityToSave.setMessageId(notifyResponse.getMessageId());
            entityToSave.setMessageType(messageType);
        } else {
            entityToSave = NotifyEntity.builder()
                    .chatId(notifyResponse.getChatId())
                    .messageId(notifyResponse.getMessageId())
                    .messageType(messageType)
                    .fkId(entityId)
                    .fkType(entityType)
                    .build();
        }

        entityToSave = repository.save(entityToSave);
        log.info("Communication saved: {}", entityToSave);
    }

    @Override
    public Optional<NotifyEntity> findNotification(Long entityId, EntityType entityType) {
        return repository.findByFkIdAndFkType(entityId, entityType);
    }

}
