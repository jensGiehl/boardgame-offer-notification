package de.agiehl.boardgame.BoardgameOffersFinder.notify.sender.telegram;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "telegram")
@Component
public class TelegramConfig {

    private String url;

    private String botId;

    private Long defaultChatId;

    private String parseMode;

    private Boolean disableWebPagePreview;

}
