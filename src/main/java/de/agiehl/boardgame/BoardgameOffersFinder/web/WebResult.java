package de.agiehl.boardgame.BoardgameOffersFinder.web;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WebResult<T> {

    private SearchStatus status;

    private Integer errorCounter;

    private T result;

    public static enum SearchStatus {
        FOUND,
        NOT_FOUND,
        ERROR
    }

}
