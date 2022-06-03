package de.agiehl.boardgame.BoardgameOffersFinder.notify.sender.telegram;

import de.agiehl.boardgame.BoardgameOffersFinder.notify.sender.Notifier;
import de.agiehl.boardgame.BoardgameOffersFinder.notify.sender.NotifyResponse;
import de.agiehl.boardgame.BoardgameOffersFinder.notify.sender.text.TelegramTextFormatter;
import de.agiehl.boardgame.BoardgameOffersFinder.notify.sender.text.TextFormatter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@Service
@AllArgsConstructor
@Slf4j
public class TelegramNotifier implements Notifier {

    private TelegramConfig config;

    private RestTemplate restTemplate;

    @Override
    public TextFormatter getTextFormatter() {
        return new TelegramTextFormatter();
    }

    @Override
    public NotifyResponse sendText(String text) {
        URI uri = getBaseUrlWithPath("/sendMessage")
                .queryParam("text", text)
                .encode(StandardCharsets.UTF_8)
                .build().toUri();

        return sendRequest(uri);
    }

    @Override
    public NotifyResponse sendImage(String imageUrl, String caption) {
        URI uri = getBaseUrlWithPath("/sendPhoto")
                .queryParam("photo", imageUrl)
                .queryParam("caption", caption)
                .encode(StandardCharsets.UTF_8)
                .build().toUri();

        return sendRequest(uri);
    }

    private NotifyResponse sendRequest(URI uri) {
        try {
            log.info("Notify URI: {}", uri);
            TelegramResponse response = restTemplate.getForObject(uri, TelegramResponse.class);

            if (!Boolean.TRUE.equals(response.getOk())) {
                throw new TelegramException("Response ist not OK");
            }

            return NotifyResponse.builder()
                    .chatId(response.getResult().getChat().getId())
                    .messageId(response.getResult().getMessageId())
                    .build();
        } catch (Exception e) {
            log.warn("Couldn't send notification", e);
            throw new TelegramException("Couldn't send notification", e);
        }
    }

    private UriComponentsBuilder getBaseUrlWithPath(String path) {
        return UriComponentsBuilder.fromHttpUrl(config.getUrl() + config.getBotId())
                .path(path)
                .queryParam("chat_id", config.getDefaultChatId())
                .queryParam("parse_mode", config.getParseMode())
                .queryParam("disable_web_page_preview", config.getDisableWebPagePreview());
    }

}
