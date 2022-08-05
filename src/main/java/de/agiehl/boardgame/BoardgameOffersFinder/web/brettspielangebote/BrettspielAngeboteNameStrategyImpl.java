package de.agiehl.boardgame.BoardgameOffersFinder.web.brettspielangebote;

import org.springframework.stereotype.Component;

@Component
public class BrettspielAngeboteNameStrategyImpl implements BrettspielAngeboteNameStrategy {

    @Override
    public String getNameForSearch(String name) {
        if (name.contains("(")) {
            name = name.substring(0, name.indexOf('('));
        }

        if (name.contains("-")) {
            name = name.substring(0, name.indexOf('-'));
        }

        return name.trim();
    }

}
