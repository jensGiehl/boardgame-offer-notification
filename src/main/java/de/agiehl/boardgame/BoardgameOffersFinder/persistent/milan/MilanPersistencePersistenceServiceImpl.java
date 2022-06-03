package de.agiehl.boardgame.BoardgameOffersFinder.persistent.milan;

import de.agiehl.boardgame.BoardgameOffersFinder.web.milan.MilanDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MilanPersistencePersistenceServiceImpl implements MilanPersistencePersistenceService {

    private MilanRepository repository;

    private MilanMapper mapper;

    @Override
    public boolean saveIfNewOrModified(MilanDto dto) {
        MilanEntity oldEntity = repository.findFirstByUrlOrderByCreateDateDesc(dto.getUrl());
        MilanEntity newEntity = mapper.toEntity(dto);

        if (newEntity.equals(oldEntity)) {
            log.debug("No changes for {}", dto);
            return false;
        }

        newEntity = repository.save(newEntity);
        log.info("New entity saved: {}", newEntity);

        return true;
    }

}
