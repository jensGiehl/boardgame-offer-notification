package de.agiehl.boardgame.BoardgameOffersFinder.web.milan;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MilanDto {

    private String url;

    private String imgUrl;

    private String name;

    private String price;

    private String stockText;
}
