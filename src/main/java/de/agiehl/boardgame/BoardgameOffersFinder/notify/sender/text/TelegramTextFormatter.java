package de.agiehl.boardgame.BoardgameOffersFinder.notify.sender.text;

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
    public TextFormatter keyValue(String key, String value) {
        return normal(key).normal(": ").bold(value);
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
