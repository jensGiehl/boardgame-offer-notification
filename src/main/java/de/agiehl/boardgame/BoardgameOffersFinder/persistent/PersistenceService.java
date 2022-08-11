package de.agiehl.boardgame.BoardgameOffersFinder.persistent;

import java.util.List;
import java.util.Optional;

public interface PersistenceService {
    boolean urlExists(String url);

    boolean notExists(DataEntity entity);

    boolean urlNotExists(String url);

    Optional<DataEntity> getLatestByUrl(String url);

    DataEntity save(DataEntity entity);

    List<DataEntity> findOldEntities(long minAgeInSeconds);

    List<DataEntity> findPendingBggItems();

    List<DataEntity> findPendingBestPriceItems();

    void saveBggInformation(DataEntity entity);

    void saveBestPriceInformation(DataEntity entity);

    void saveNotificationInformation(DataEntity entity);

    void increaseNotificationFailCount(DataEntity entity);
}
