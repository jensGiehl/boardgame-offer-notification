package de.agiehl.boardgame.BoardgameOffersFinder.notify.sender.telegram;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class TelegramResponse {

    private Boolean ok;

    private TelegramResponseResult result;

    @Data
    public static class TelegramResponseResult {
        private Long messageId;

        private Long date;

        private String text;

        private From from;

        private Chat chat;

        @Data
        public static class From {
            private Long id;

            private Boolean isBot;

            private String firstName;

            private String username;
        }

        @Data
        public static class Chat {
            private Long id;

            private String firstName;

            private String type;
        }
    }
}
