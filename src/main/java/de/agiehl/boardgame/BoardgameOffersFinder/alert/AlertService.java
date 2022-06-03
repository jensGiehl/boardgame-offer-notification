package de.agiehl.boardgame.BoardgameOffersFinder.alert;

/**
 * The idea behind this service is that if there is a problem it will be reported.
 */
public interface AlertService {
    void sendAlert(String message, Throwable exception);

    void sendAlert(String message);
}
