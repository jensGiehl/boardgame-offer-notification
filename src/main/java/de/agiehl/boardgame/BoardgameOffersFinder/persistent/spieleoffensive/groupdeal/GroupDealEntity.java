package de.agiehl.boardgame.BoardgameOffersFinder.persistent.spieleoffensive.groupdeal;

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
@Table("SO_GROUPDEAL")
public class GroupDealEntity {

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

    private String price;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private String quantity;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private LocalDate validUntil;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private int failCount;

}
