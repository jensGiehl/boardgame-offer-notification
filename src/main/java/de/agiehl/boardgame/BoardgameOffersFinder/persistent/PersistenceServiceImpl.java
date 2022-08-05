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
    public List<DataEntity> findOldEntities(long getEntitiesWhichAreOlderThanSeconds) {
        LocalDateTime maxAge = LocalDateTime
                .now()
                .minusSeconds(getEntitiesWhichAreOlderThanSeconds);

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

}
