package de.agiehl.boardgame.BoardgameOffersFinder.notify.sender;

import de.agiehl.boardgame.BoardgameOffersFinder.notify.sender.text.TextFormatter;

public interface Notifier {

    TextFormatter getTextFormatter();

    NotifyResponse sendText(String text);

    NotifyResponse sendImage(String imageUrl, String caption);

    NotifyResponse updateText(NotifyUpdateInformation notifyInformation, String textToSend);

    NotifyResponse updateImage(NotifyUpdateInformation notifyInformation, String imgUrl, String textToSend);
}
