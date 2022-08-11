package de.agiehl.boardgame.BoardgameOffersFinder.persistent;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class PersistenceServiceImpl implements PersistenceService {

    private final DataRepository repository;

    @Override
    public boolean urlExists(String url) {
        return repository.findByUrlOrderByCreateDateDesc(url).isPresent();
    }

    @Override
    public boolean notExists(DataEntity entity) {
        return urlNotExists(entity.getUrl());
    }

    @Override
    public boolean urlNotExists(String url) {
        return !urlExists(url);
    }

    @Override
    public Optional<DataEntity> getLatestByUrl(String url) {
        return repository.findByUrlOrderByCreateDateDesc(url);
    }

    @Override
    public DataEntity save(DataEntity entity) {
        entity.setUpdateDate(LocalDateTime.now());
        DataEntity savedEntity = repository.save(entity);

        log.info("Entity saved: {}", entity);

        return savedEntity;
    }

    @Override
    public List<DataEntity> findOldEntities(long minAgeInSeconds) {
        LocalDateTime maxAge = LocalDateTime
                .now()
                .minusSeconds(minAgeInSeconds);

        return repository.findByEnableNotificationTrueAndCreateDateLessThan(maxAge);
    }

    @Override
    public List<DataEntity> findPendingBggItems() {
        return repository.findByEnableBggTrue();
    }

    @Override
    public List<DataEntity> findPendingBestPriceItems() {
        return repository.findByEnableBestPriceTrue();
    }

    @Override
    public void saveBggInformation(DataEntity entity) {
        repository.updateBggInformation(
                LocalDateTime.now(),
                entity.getBggId(),
                entity.getBggRating(),
                entity.getBggWishing(),
                entity.getBggWanting(),
                entity.getBggLink(),
                entity.isEnableBgg(),
                entity.getId()
        );

        log.info("BGG Information saved: {}", entity);
    }

    @Override
    public void saveBestPriceInformation(DataEntity entity) {
        repository.updateBestPriceInformation(
                LocalDateTime.now(),
                entity.getBestPrice(),
                entity.getBestPriceUrl(),
                entity.isEnableBestPrice(),
                entity.getId()
        );

        log.info("Best price Information saved: {}", entity);
    }

    @Override
    public void saveNotificationInformation(DataEntity entity) {
        repository.updateNotificationInformation(
                LocalDateTime.now(),
                entity.getMessageId(),
                entity.getChatId(),
                entity.isEnableNotification(),
                entity.getId()
        );

        log.info("Notification Information saved: {}", entity);
    }

    @Override
    public void increaseNotificationFailCount(DataEntity entity) {
        repository.findById(entity.getId()).ifPresent(currentEntity -> {
            int newFailCount = currentEntity.getNotificationFailCount() + 1;

            repository.updateNotificationFailCount(newFailCount, entity.getId());
            log.warn("Setting notification fail count for {} to {}", entity, newFailCount);
        });
    }

}
