package de.agiehl.boardgame.BoardgameOffersFinder.notify.sender.telegram;

public class TelegramException extends RuntimeException {
    public TelegramException(String message, Exception e) {
        super(message, e);
    }

    public TelegramException(String message) {
        super(message);
    }
}
