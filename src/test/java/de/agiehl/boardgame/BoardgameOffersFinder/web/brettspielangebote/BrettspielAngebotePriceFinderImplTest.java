package de.agiehl.boardgame.BoardgameOffersFinder.web.brettspielangebote;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BrettspielAngebotePriceFinderImplTest {

    @Test
    public void testStringFormat() {
        String price = "10.3";
        String currency = "EUR";

        String result = String.format("%,.2f %s", Double.parseDouble(price), currency);

        assertThat(result).isEqualTo("10,30 EUR");
    }

    @Test
    public void testStringWithoutDecimalFormat() {
        String price = "10";
        String currency = "EUR";

        String result = String.format("%,.2f %s", Double.parseDouble(price), currency);

        assertThat(result).isEqualTo("10,00 EUR");
    }

}