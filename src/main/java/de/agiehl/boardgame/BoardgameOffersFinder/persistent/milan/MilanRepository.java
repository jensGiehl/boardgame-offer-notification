package de.agiehl.boardgame.BoardgameOffersFinder.persistent.milan;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MilanRepository  extends CrudRepository<MilanEntity, Long> {

    MilanEntity findFirstByUrlOrderByCreateDateDesc(String url);

    @Query("SELECT m.* FROM MILAN m LEFT JOIN BGG b ON m.ID = b.ID AND b.fk_type = 'MILAN' WHERE b.id is null")
    List<MilanEntity> findAllWithoutBggRating();

    @Query("SELECT m.* FROM MILAN m LEFT JOIN COMPARISON_PRICE c ON m.ID = c.ID AND c.fk_type = 'MILAN' LEFT JOIN BGG b ON m.ID = b.ID AND b.fk_type = 'MILAN' WHERE c.ID is null AND b.ID is not null")
    List<MilanEntity> findAllWithoutComparisonPrice();
}
