package de.agiehl.boardgame.BoardgameOffersFinder.persistent.spieleoffensive.priceaction;

import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.priceaction.PriceActionDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class PriceActionPersistenceServiceImpl implements PriceActionPersistenceService {

    private PriceActionRepository repository;

    private PriceActionMapper mapper;

    @Override
    public boolean saveIfNew(PriceActionDto dto) {
        PriceActionEntity oldEntity = repository.findFirstByUrlOrderByCreateDateDesc(dto.getUrl());
        PriceActionEntity newEntity = mapper.toEntity(dto);

        if (newEntity.equals(oldEntity)) {
            log.debug("No changes for {}", dto);
            return false;
        }

        newEntity = repository.save(newEntity);
        log.info("New entity saved: {}", newEntity);

        return true;
    }

}
