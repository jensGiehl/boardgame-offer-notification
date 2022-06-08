package de.agiehl.boardgame.BoardgameOffersFinder.persistent.milan;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder
@Table("MILAN")
public class MilanEntity {

    @Id
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Long id;

    private String url;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private String imgUrl;

    private String name;

    @ToString.Exclude
    private String price;

    @ToString.Exclude
    private String stockText;

    @EqualsAndHashCode.Exclude
    @Builder.Default
    private LocalDateTime createDate = LocalDateTime.now();


}
