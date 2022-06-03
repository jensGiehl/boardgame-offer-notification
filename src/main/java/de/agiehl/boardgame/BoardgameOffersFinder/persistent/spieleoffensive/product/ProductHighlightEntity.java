package de.agiehl.boardgame.BoardgameOffersFinder.persistent.spieleoffensive.product;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@Table("SO_PRODUCT")
public class ProductHighlightEntity {

    @Id
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Long id;

    private String url;

    @ToString.Exclude
    private String imgUrl;

    private String name;

    @ToString.Exclude
    private String price;

    @ToString.Exclude
    private LocalDate endDate;

    @EqualsAndHashCode.Exclude
    @Builder.Default
    private LocalDateTime createDate = LocalDateTime.now();

}
