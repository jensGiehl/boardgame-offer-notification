package de.agiehl.boardgame.BoardgameOffersFinder.notify.sender.text;

public interface TextFormatter {

    TextFormatter bold(String text);

    TextFormatter italic(String text);

    TextFormatter link(String text, String link);

    TextFormatter normal(String text);

    TextFormatter keyValue(String key, String value);

    TextFormatter newLine();

    String getText();

}
