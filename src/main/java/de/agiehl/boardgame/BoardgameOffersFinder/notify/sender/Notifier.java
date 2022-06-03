package de.agiehl.boardgame.BoardgameOffersFinder.notify.sender;

import de.agiehl.boardgame.BoardgameOffersFinder.notify.sender.text.TextFormatter;

public interface Notifier {

    TextFormatter getTextFormatter();

    NotifyResponse sendText(String text);

    NotifyResponse sendImage(String imageUrl, String caption);
}
