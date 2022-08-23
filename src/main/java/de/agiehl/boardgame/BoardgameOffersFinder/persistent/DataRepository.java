package de.agiehl.boardgame.BoardgameOffersFinder.persistent;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
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

    @Modifying
    @Query("update DATA set UPDATE_DATE = :date, MESSAGE_ID = :messageId, CHAT_ID = :chatId, ENABLE_NOTIFICATION = :enableNotification WHERE ID = :id")
    void updateNotificationInformation(@Param("date") LocalDateTime updateDate,
                                       @Param("messageId") String messageId,
                                       @Param("chatId") String chatId,
                                       @Param("enableNotification") boolean enableNotification,
                                       @Param("id") Long id
    );

    @Modifying
    @Query("update DATA set UPDATE_DATE = :date, BEST_PRICE = :bestPrice, BEST_PRICE_URL = :bestPriceUrl, ENABLE_BEST_PRICE = :enableBestPrice, BEST_PRICE_POSSIBLE = :bestPricePossible WHERE ID = :id")
    void updateBestPriceInformation(@Param("date") LocalDateTime updateDate,
                                    @Param("bestPrice") String bestPrice,
                                    @Param("bestPriceUrl") String bestPriceUrl,
                                    @Param("enableBestPrice") boolean enableBestPrice,
                                    @Param("bestPricePossible") boolean bestPricePossible,
                                    @Param("id") Long id);

    @Modifying
    @Query("update DATA set UPDATE_DATE = :date, BGG_ID = :bggId, BGG_RATING = :bggRating, BGG_WISHING = :bggWishing, BGG_WANTING = :bggWanting, BGG_LINK = :bggLink, ENABLE_BGG = :enableBgg, BGG_USER_RATED = :bggUserRated, BGG_NAME_COUNTER = :bggNameCounter, BGG_FAIL_COUNT = :bggFailCount, BGG_RANK = :bggRank WHERE ID = :id")
    void updateBggInformation(@Param("date") LocalDateTime updateDate,
                              @Param("bggId") Long bggId,
                              @Param("bggRating") String bggRating,
                              @Param("bggWishing") Integer bggWishing,
                              @Param("bggWanting") Integer bggWanting,
                              @Param("bggLink") String bggLink,
                              @Param("enableBgg") boolean enableBgg,
                              @Param("bggUserRated") Long bggUserRated,
                              @Param("bggNameCounter") Integer bggNameCounter,
                              @Param("bggFailCount") int bggFailCount,
                              @Param("bggRank") Long bggRank,
                              @Param("id") Long id
    );

    @Modifying
    @Query("update DATA set NOTIFICATION_FAIL_COUNT = :failCount WHERE id = :id")
    void updateNotificationFailCount(@Param("failCount") int failCount, @Param("id") Long id);

    @Modifying
    @Query("update DATA set BEST_PRICE_FAIL_COUNT = :failCount WHERE id = :id")
    void updateBestPriceFailCount(@Param("failCount") int failCount, @Param("id") Long id);

}
