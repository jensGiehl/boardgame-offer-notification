package de.agiehl.boardgame.BoardgameOffersFinder.notify.sender.text;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TelegramTextFormatter implements TextFormatter {

    private final StringBuilder str = new StringBuilder();

    @Override
    public TextFormatter bold(String text) {
        str.append("*").append(text).append("*");
        return this;
    }

    @Override
    public TextFormatter italic(String text) {
        str.append("_").append(text).append("_");
        return this;
    }

    @Override
    public TextFormatter link(String text, String link) {
        str.append("[").append(text).append("](").append(link).append(")");
        return this;
    }

    @Override
    public TextFormatter normal(String text) {
        str.append(text);
        return this;
    }

    @Override
    public TextFormatter keyValue(String key, Object value) {
        if (value == null) {
            log.warn("Value for key {} is NULL", key);
            return this;
        }
        return normal(key).normal(": ").bold(value.toString());
    }

    @Override
    public TextFormatter keyValueLink(String key, Object value, String link) {
        return normal(key).normal(": ").link(value.toString(), link);
    }

    @Override
    public TextFormatter newLine() {
        str.append("\n");
        return this;
    }

    @Override
    public String getText() {
        return str.toString();
    }
}
