package de.agiehl.boardgame.BoardgameOffersFinder.notify.sender;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotifyResponse {

    private Long chatId;

    private Long messageId;


}
