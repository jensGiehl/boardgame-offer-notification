package de.agiehl.boardgame.BoardgameOffersFinder.web;

import de.agiehl.boardgame.BoardgameOffersFinder.alert.AlertService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Slf4j
public class WebClientImpl implements WebClient {

    private WebClientConfig config;

    private AlertService alertService;

    @Override
    public Document loadDocumentFromUrl(String url) {
        log.info("Loading URL '{}'", url);

        try {
            long startTime = System.currentTimeMillis();

            Document document = Jsoup.connect(url)
                    .userAgent(config.getUserAgent())
                    .timeout(Long.valueOf(config.getTimeout().toMillis()).intValue())
                    .followRedirects(true)
                    .header("Accept-Language", config.getAcceptLanguage())
                    .get();

            long duration = System.currentTimeMillis() - startTime;
            log.debug("Document from URL '{}' loaded in {} ms", url, duration);

            return document;
        } catch (IOException e) {
            throw new WebClientException("Couldn't load URL '" + url + "'", e);
        }
    }

    @Override
    public Document loadDocumentFromRelativePath(String rootUrl, String path) {
        if (rootUrl.endsWith("/")) {
            rootUrl = rootUrl.substring(0, rootUrl.length() - 1);
        }

        if (!path.startsWith("/")) {
            path = "/" + path;
        }

        return loadDocumentFromUrl(rootUrl + path);
    }

    @Override
    public String getTextFromFirstElement(Element element, String selector) {
        List<String> allTexts = getTextFromAllElements(element, selector);
        if (allTexts.isEmpty()) {
            return "";
        }

        return allTexts.get(0);
    }

    @Override
    public List<String> getTextFromAllElements(Element element, String selector) {
        Elements selectedElement = element.select(selector);
        if (selectedElement.isEmpty()) {
            String message = String.format("No element found to extract text. Selector: '%s'. Element: %s", selector, element);
            alertService.sendAlert(message);
            return Collections.emptyList();
        }

        return selectedElement.stream()
                .map(el -> el.text().trim())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAttributeFromAllElements(Element element, String selector, String attributeName) {
        Elements selectedElement = element.select(selector);
        if (selectedElement.isEmpty()) {
            String message = String.format("Failed to select '%s'. Element: %s",
                    selector,
                    element);
            alertService.sendAlert(message);

            return Collections.emptyList();
        }

        return selectedElement.stream()
                .map(el -> getAttribute(el, attributeName))
                .collect(Collectors.toList());
    }

    @Override
    public String getAttribute(Element element, String attributeName) {
        String attributeValue = element.attr(attributeName);

        if (attributeValue.isEmpty()) {
            String message = String.format("No value found for attribute '%s' on element '%s'",
                    attributeName,
                    element);

            alertService.sendAlert(message);
        }

        return attributeValue;
    }

    @Override
    public String selectFirstChildAndGetAttributeValue(Element element, String selector, String attributeName) {
        List<String> allAttributes = getAttributeFromAllElements(element, selector, attributeName);
        if (allAttributes.isEmpty()) {
            return "";
        }

        return allAttributes.get(0);
    }

    @Override
    public Elements getElements(Element element, String selector) {
        Elements selectedElements = element.select(selector);
        if (selectedElements.isEmpty()) {
            String message = String.format("Failed to select '%s'. Element: %s",
                    selector,
                    element);
            alertService.sendAlert(message);
        }

        return selectedElements;
    }

}
