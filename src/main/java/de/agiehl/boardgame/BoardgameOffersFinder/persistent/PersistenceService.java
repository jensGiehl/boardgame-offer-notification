package de.agiehl.boardgame.BoardgameOffersFinder.persistent;

import java.util.List;
import java.util.Optional;

public interface PersistenceService {
    boolean exists(DataEntity entity);

    boolean exists(String url);

    boolean notExists(DataEntity entity);

    boolean urlNotExists(String url);

    Optional<DataEntity> getLatestByUrl(String url);

    DataEntity save(DataEntity entity);

    List<DataEntity> findOldEntities(long getEntitiesWhichAreOlderThanSeconds);
}
