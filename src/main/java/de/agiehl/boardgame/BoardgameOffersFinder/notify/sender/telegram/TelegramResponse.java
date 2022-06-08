package de.agiehl.boardgame.BoardgameOffersFinder.notify.sender.telegram;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class TelegramResponse {

    private Boolean ok;

    private TelegramResponseResult result;

    @Data
    public static class TelegramResponseResult {
        @JsonProperty("message_id")
        private Long messageId;

        private Long date;

        private String text;

        private From from;

        private Chat chat;

        @Data
        public static class From {
            private Long id;

            @JsonProperty("is_bot")
            private Boolean isBot;

            @JsonProperty("first_name")
            private String firstName;

            private String username;
        }

        @Data
        public static class Chat {
            private Long id;

            @JsonProperty("first_name")
            private String firstName;

            private String type;
        }
    }
}
