package de.agiehl.boardgame.BoardgameOffersFinder.persistent.milan;

import de.agiehl.boardgame.BoardgameOffersFinder.web.milan.MilanDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class MilanMapperTest {

    private final MilanMapper classUnderTest = Mappers.getMapper(MilanMapper.class);

    @Test
    void toEntity() {
        MilanDto dto = MilanDto.builder()
                .price("42")
                .imgUrl("IMG")
                .name("Name")
                .stockText("Stock")
                .url("URL")
                .build();

        MilanEntity entity = classUnderTest.toEntity(dto);

        assertThat(entity).isNotNull();
        assertThat(entity.getUrl()).isEqualTo("URL");
        assertThat(entity.getImgUrl()).isEqualTo("IMG");
        assertThat(entity.getName()).isEqualTo("Name");
        assertThat(entity.getStockText()).isEqualTo("Stock");
        assertThat(entity.getUrl()).isEqualTo("URL");
    }
}