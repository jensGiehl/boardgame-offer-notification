package de.agiehl.boardgame.BoardgameOffersFinder.persistent.brettspielangebote;

import de.agiehl.boardgame.BoardgameOffersFinder.persistent.EntityType;
import de.agiehl.boardgame.BoardgameOffersFinder.web.brettspielangebote.BrettspielAngeboteBggDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class BrettspielAngebotePriceServiceImpl implements BrettspielAngebotePriceService {

    private BrettspielAngebotePriceRepository repository;

    @Override
    public ComparisonPriceEntity save(Long id, EntityType entityType, BrettspielAngeboteBggDto dto) {
        ComparisonPriceEntity newEntity = ComparisonPriceEntity.builder()
                .fkId(id)
                .fkType(entityType)
                .price(dto.getCurrentPrice())
                .url(dto.getUrl())
                .build();

        return repository.save(newEntity);
    }

    @Override
    public Optional<ComparisonPriceEntity> findByFkIdAndFkType(Long id, EntityType type) {
        return repository.findFirstByFkIdAndFkTypeOrderByCreateDate(id, type);
    }

}
