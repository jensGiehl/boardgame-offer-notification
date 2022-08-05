package de.agiehl.boardgame.BoardgameOffersFinder.notify.milan;

import de.agiehl.boardgame.BoardgameOffersFinder.notify.NotifyServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class NotifyServiceImplTest {

    @Test
    public void testRegex() {
        String regex = NotifyServiceImpl.NON_NUMBER_REGEX;

        Assertions.assertThat("10.30 EUR".replaceAll(regex, "")).isEqualTo("10.30");
        Assertions.assertThat("10,30 EUR".replaceAll(regex, "")).isEqualTo("10,30");
    }

}