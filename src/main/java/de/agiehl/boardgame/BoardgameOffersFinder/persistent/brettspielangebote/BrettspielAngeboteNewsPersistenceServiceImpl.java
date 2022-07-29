package de.agiehl.boardgame.BoardgameOffersFinder.persistent.brettspielangebote;

import de.agiehl.boardgame.BoardgameOffersFinder.web.brettspielangebote.BrettspielAngebotNewsDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class BrettspielAngeboteNewsPersistenceServiceImpl implements BrettspielAngeboteNewsPersistenceService {

    private BrettspielAngeboteNewsMapper mapper;

    private BrettspielAngeboteNewsRepository repository;

    @Override
    public boolean saveIfNewOrModified(BrettspielAngebotNewsDto dto) {
        BrettspielAngeboteNewsEntity oldEntity = repository.findFirstByUrlOrderByCreateDateDesc(dto.getUrl());
        BrettspielAngeboteNewsEntity newEntity = mapper.toEntity(dto);

        if (newEntity.equals(oldEntity)) {
            log.debug("No chnages for {}", dto);
            return false;
        }

        newEntity = repository.save(newEntity);
        log.info("New entity saved: {}", newEntity );

        return true;
    }

}
