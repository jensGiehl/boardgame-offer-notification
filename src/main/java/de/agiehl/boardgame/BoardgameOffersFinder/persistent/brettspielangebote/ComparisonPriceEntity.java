package de.agiehl.boardgame.BoardgameOffersFinder.persistent.brettspielangebote;

import de.agiehl.boardgame.BoardgameOffersFinder.persistent.EntityType;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder
@Table("COMPARISON_PRICE")
public class ComparisonPriceEntity {

    @Id
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Long id;

    @EqualsAndHashCode.Exclude
    @Builder.Default
    private LocalDateTime createDate = LocalDateTime.now();

    private EntityType fkType;

    private Long fkId;

    private String price;

    private String url;
}
