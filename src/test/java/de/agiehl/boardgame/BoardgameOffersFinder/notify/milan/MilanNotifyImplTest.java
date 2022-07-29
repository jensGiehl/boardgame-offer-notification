package de.agiehl.boardgame.BoardgameOffersFinder.notify.milan;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class MilanNotifyImplTest {

    @Test
    public void testRegex() {
        String regex = "[^\\d,.]";

        Assertions.assertThat("10.30 EUR".replaceAll(regex,"")).isEqualTo("10.30");
        Assertions.assertThat("10,30 EUR".replaceAll(regex,"")).isEqualTo("10,30");
    }

}