package de.agiehl.boardgame.BoardgameOffersFinder.web;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

public interface WebClient {
    Document loadDocumentFromUrl(String url);

    Document loadDocumentFromRelativePath(String rootUrl, String path);

    String getTextFromFirstElement(Element element, String selector);

    List<String> getTextFromAllElements(Element element, String selector);

    List<String> getAttributeFromAllElements(Element element, String selector, String attributeName);

    String getAttribute(Element element, String attributeName);

    String selectFirstChildAndGetAttributeValue(Element element, String selector, String attributeName);

    Elements getElements(Element element, String selector);

    Element getFirstElement(Element element, String selector);
}
