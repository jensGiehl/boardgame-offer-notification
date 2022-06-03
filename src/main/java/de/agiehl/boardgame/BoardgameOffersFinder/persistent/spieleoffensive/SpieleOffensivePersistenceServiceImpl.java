package de.agiehl.boardgame.BoardgameOffersFinder.persistent.spieleoffensive;

import de.agiehl.boardgame.BoardgameOffersFinder.persistent.spieleoffensive.groupdeal.GroupDealPersistenceService;
import de.agiehl.boardgame.BoardgameOffersFinder.persistent.spieleoffensive.priceaction.PriceActionPersistenceService;
import de.agiehl.boardgame.BoardgameOffersFinder.persistent.spieleoffensive.product.ProductHightlightService;
import de.agiehl.boardgame.BoardgameOffersFinder.persistent.spieleoffensive.schmiede.SpieleschmiedePersistenceService;
import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.SpieleOffensiveDto;
import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.groupdeal.GroupDealDto;
import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.priceaction.PriceActionDto;
import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.product.SpieleOffensiveProductDto;
import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.schmiede.SpieleschmiedeDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class SpieleOffensivePersistenceServiceImpl implements SpieleOffensivePersistenceService {

    private GroupDealPersistenceService groupDealPersistenceService;

    private PriceActionPersistenceService priceActionPersistenceService;

    private ProductHightlightService productHightlightService;

    private SpieleschmiedePersistenceService spieleschmiedePersistenceService;

    @Override
    public boolean saveIfNewOrModified(SpieleOffensiveDto dto) {
        if (dto instanceof GroupDealDto) {
            return groupDealPersistenceService.saveIfNew((GroupDealDto) dto);
        }

        if (dto instanceof PriceActionDto) {
            return priceActionPersistenceService.saveIfNew((PriceActionDto) dto);
        }

        if (dto instanceof SpieleOffensiveProductDto) {
            return productHightlightService.saveIfNew((SpieleOffensiveProductDto) dto);
        }

        if (dto instanceof SpieleschmiedeDto) {
            return spieleschmiedePersistenceService.saveIfNew((SpieleschmiedeDto) dto);
        }

        log.error("Unknown class: {}", dto.getClass().getName());
        return false;
    }

}
