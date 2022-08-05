package de.agiehl.boardgame.BoardgameOffersFinder.notify.sender;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotifyUpdateInformation {

    private final String chatId;

    private final String messageId;

}
