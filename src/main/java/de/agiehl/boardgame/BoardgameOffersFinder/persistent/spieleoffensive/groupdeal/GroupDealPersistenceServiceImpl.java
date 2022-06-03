package de.agiehl.boardgame.BoardgameOffersFinder.persistent.spieleoffensive.groupdeal;

import de.agiehl.boardgame.BoardgameOffersFinder.web.spieleoffensive.groupdeal.GroupDealDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class GroupDealPersistenceServiceImpl implements GroupDealPersistenceService {

    private GroupDealRepository repository;

    private GroupDealMapper mapper;

    @Override
    public boolean saveIfNew(GroupDealDto dto) {
        if(repository.existsByUrl(dto.getUrl())) {
            log.debug("Group deal already exists: {}", dto);
            return false;
        }

        GroupDealEntity newEntity = mapper.toEntity(dto);
        newEntity = repository.save(newEntity);
        log.info("New entity saved: {}", newEntity);

        return true;
    }
}
