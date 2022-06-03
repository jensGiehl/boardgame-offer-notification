package de.agiehl.boardgame.BoardgameOffersFinder.persistent.notify;

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

    @Override
    public Optional<Long> getMessageIdForUrl(String url) {
        Optional<NotifyEntity> result = Optional.empty(); // TODO: .findFirstByUrlOrderByCreateDateDesc(url);
        if (result.isEmpty()) {
            log.warn("No communication for '{}' found", url);
            return Optional.empty();
        }

        log.debug("Found following communication: {}", result);

        return Optional.of(result.get().getMessageId());
    }

    @Override
    public void saveCommunication(NotifyEntity entity) {
        if (entity.getCreateDate() == null) {
            entity.setCreateDate(LocalDateTime.now());
        }
        entity = repository.save(entity);
        log.info("New communication saved: {}", entity);
    }

}
