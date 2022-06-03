package de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.groupdeal;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spiele-offensive.group-deal")
public class GroupDealConfig {

    private String linkMustContain;
}
