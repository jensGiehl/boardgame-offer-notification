package de.agiehl.boardgame.BoardgameOffersFinder.persistent.unknowns;

import de.agiehl.boardgame.BoardgameOffersFinder.web.unknowns.UnknownsDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UnknownsPersistenceServiceImpl implements UnknownsPersistenceService {

    private UnknownsRepository repository;

    private UnknownsMapper mapper;

    @Override
    public boolean saveIfNewOrModified(UnknownsDto dto) {
        UnknownsEntity oldEntity = repository.findFirstByUrlOrderByCreateDateDesc(dto.getUrl());
        UnknownsEntity newEntity = mapper.toEntity(dto);

        if (newEntity.equals(oldEntity)) {
            log.debug("No changes for {}", dto);
            return false;
        }

        newEntity = repository.save(newEntity);
        log.info("New entity saved: {}", newEntity);

        return true;
    }

}
