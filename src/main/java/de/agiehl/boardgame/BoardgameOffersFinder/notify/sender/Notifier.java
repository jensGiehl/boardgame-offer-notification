package de.agiehl.boardgame.BoardgameOffersFinder.notify.sender;

import de.agiehl.boardgame.BoardgameOffersFinder.notify.sender.text.TextFormatter;
import de.agiehl.boardgame.BoardgameOffersFinder.persistent.notify.NotifyEntity;

public interface Notifier {

    TextFormatter getTextFormatter();

    NotifyResponse sendText(String text);

    NotifyResponse sendImage(String imageUrl, String caption);

    NotifyResponse sendText(NotifyEntity oldNotifyEntity, String textToSend);

    NotifyResponse sendImage(NotifyEntity oldNotifyEntity, String imgUrl, String textToSend);
}
