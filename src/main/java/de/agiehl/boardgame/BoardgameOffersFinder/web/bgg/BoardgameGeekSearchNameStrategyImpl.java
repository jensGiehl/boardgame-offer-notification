package de.agiehl.boardgame.BoardgameOffersFinder.web.bgg;

import org.springframework.stereotype.Component;

@Component
public class BoardgameGeekSearchNameStrategyImpl implements BoardgameGeekSearchNameStrategy {

    @Override
    public String getNameForSearch(String name) {
        if (name.contains("(")) {
            name = name.substring(0, name.indexOf('('));
        }

        name = name.replace('-', ' ');

        return name.trim();
    }

}
