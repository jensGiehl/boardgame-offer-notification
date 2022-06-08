package de.agiehl.boardgame.BoardgameOffersFinder.persistent.bgg;

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
@Table("BGG")
public class BggEntity {

    @Id
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Long id;

    @EqualsAndHashCode.Exclude
    @Builder.Default
    private LocalDateTime createDate = LocalDateTime.now();

    private Long fkId;

    private EntityType fkType;

    private Long bggId;

    private String bggRating;

    private Integer wanting;

    private Integer wishing;

    private String bggLink;

}
