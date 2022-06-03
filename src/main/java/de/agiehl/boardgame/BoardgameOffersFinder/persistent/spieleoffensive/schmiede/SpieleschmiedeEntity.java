package de.agiehl.boardgame.BoardgameOffersFinder.persistent.spieleoffensive.schmiede;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("SO_SCHMIEDE")
@Data
@Builder
public class SpieleschmiedeEntity {

    @Id
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Long id;

    private String url;

    @ToString.Exclude
    private String imgUrl;

    private String name;

    @EqualsAndHashCode.Exclude
    @Builder.Default
    private LocalDateTime createDate = LocalDateTime.now();

}
