package de.agiehl.boardgame.BoardgameOffersFinder.alert;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class AlertServiceImpl implements AlertService {

    @Override
    public void sendAlert(String message, Throwable exception) {
        log.warn(message, exception);
    }

    @Override
    public void sendAlert(String message) {
        sendAlert(message, null);
    }
}
