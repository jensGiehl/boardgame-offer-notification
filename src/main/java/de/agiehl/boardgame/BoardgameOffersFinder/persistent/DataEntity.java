package de.agiehl.boardgame.BoardgameOffersFinder.persistent;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("DATA")
@Data
@Builder
public class DataEntity {

    @Id
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Long id;

    @EqualsAndHashCode.Exclude
    @Builder.Default
    private LocalDateTime createDate = LocalDateTime.now();

    @EqualsAndHashCode.Exclude
    @Builder.Default
    private LocalDateTime updateDate = LocalDateTime.now();

    private String url;

    @Column("IMG_URL")
    private String imageUrl;

    private String name;

    private String description;

    private String price;

    @Column("STOCK")
    private String stockText;

    private String validUntil;

    @Builder.Default
    private boolean enableNotification = true;

    private String messageId;

    private String chatId;

    private Long bggId;

    private String bggRating;

    private Integer bggWishing;

    private Integer bggWanting;

    private String bggLink;

    private boolean enableBgg;

    private String bestPrice;

    private String bestPriceUrl;

    private boolean enableBestPrice;

    private CrawlerName crawlerName;

}
