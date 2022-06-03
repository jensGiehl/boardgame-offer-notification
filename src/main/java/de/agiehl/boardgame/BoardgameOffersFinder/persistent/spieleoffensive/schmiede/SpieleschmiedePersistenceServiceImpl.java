package de.agiehl.boardgame.BoardgameOffersFinder.persistent.spieleoffensive.schmiede;

import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.schmiede.SpieleschmiedeDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class SpieleschmiedePersistenceServiceImpl implements SpieleschmiedePersistenceService {

    private SpieleschmiedeRepository repository;

    private SpieleschmiedeMapper mapper;

    @Override
    public boolean saveIfNew(SpieleschmiedeDto dto) {
        if(repository.existsByUrl(dto.getUrl())) {
            log.debug("{} already exists", dto);
           return false;
        }

        SpieleschmiedeEntity newEntity = mapper.toEntity(dto);
        repository.save(newEntity);
        log.info("New entity saved: {}", newEntity);

        return true;
    }

}
