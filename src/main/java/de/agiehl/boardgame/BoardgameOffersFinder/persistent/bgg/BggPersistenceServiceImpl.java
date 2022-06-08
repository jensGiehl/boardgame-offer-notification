package de.agiehl.boardgame.BoardgameOffersFinder.persistent.bgg;

import de.agiehl.boardgame.BoardgameOffersFinder.persistent.EntityType;
import de.agiehl.boardgame.BoardgameOffersFinder.web.bgg.BoardgameGeekDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class BggPersistenceServiceImpl implements BggPersistenceService {

    private BggRepository repository;

    @Override
    public BggEntity save(Long id, EntityType entityType, Optional<BoardgameGeekDto> boardgameInfo) {
        String rating = null;
        Integer wanting = null, wishing = null;
        Long bggId = null;
        String bggLink = null;

        if (boardgameInfo.isPresent()) {
            BoardgameGeekDto geekDto = boardgameInfo.get();
            rating = geekDto.getRating();
            wanting = geekDto.getWanting();
            wishing = geekDto.getWishing();
            bggId = geekDto.getId();
            bggLink = geekDto.getBggLink();
        }

        BggEntity entity = BggEntity.builder()
                .fkId(id)
                .fkType(entityType)
                .bggRating(rating)
                .wanting(wanting)
                .wishing(wishing)
                .bggId(bggId)
                .bggLink(bggLink)
                .build();

        entity = repository.save(entity);
        log.info("BGG Entity saved: {}", entity);

        return entity;
    }

    @Override
    public Optional<BggEntity> findByFkIdAndFkType(Long id, EntityType type) {
        return repository.findFirstByFkIdAndFkTypeOrderByCreateDate(id, type);
    }

}
