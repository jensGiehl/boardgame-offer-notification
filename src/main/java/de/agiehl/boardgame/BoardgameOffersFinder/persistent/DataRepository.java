package de.agiehl.boardgame.BoardgameOffersFinder.persistent;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DataRepository extends CrudRepository<DataEntity, Long> {

    Optional<DataEntity> findByUrlOrderByCreateDateDesc(String url);

    List<DataEntity> findByEnableNotificationTrueAndCreateDateLessThan(LocalDateTime maxWaitTime);

    List<DataEntity> findByEnableBggTrue();

    List<DataEntity> findByEnableBestPriceTrue();

}
