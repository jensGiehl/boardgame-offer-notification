package de.agiehl.boardgame.BoardgameOffersFinder.persistent.spieleoffensive.product;

import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.product.SpieleOffensiveProductDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ProductHightlightServiceImpl implements ProductHightlightService {

    private ProductHighlightRepository repository;

    private ProductHighlightMapper mapper;

    @Override
    public boolean saveIfNew(SpieleOffensiveProductDto dto) {
        ProductHighlightEntity oldEntity = repository.findFirstByUrlOrderByCreateDateDesc(dto.getUrl());
        ProductHighlightEntity newEntity = mapper.toEntity(dto);

        if (newEntity.equals(oldEntity)) {
            log.debug("No changes for {}", dto);
            return false;
        }

        newEntity = repository.save(newEntity);
        log.info("New entity saved: {}", newEntity);

        return true;
    }

}
