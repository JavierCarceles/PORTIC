package net.portic.gestorComprobacionNotificacionMMPP.domain.Mappers;

import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.ExpedientTerrestreDto;
import net.portic.gestorComprobacionNotificacionMMPP.domain.mappers.ExpedientTerrestreMapper;
import net.portic.gestorComprobacionNotificacionMMPP.domain.models.ExpedientTerrestre;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ExpedientTerrestreMapperTest {

    private final ExpedientTerrestreMapper mapper = ExpedientTerrestreMapper.INSTANCE;

    @Test
    void shouldMapDtoToEntity() {
        ExpedientTerrestreDto expedientTerrestreDto = new ExpedientTerrestreDto();
        expedientTerrestreDto.setIdExp(1L);
        expedientTerrestreDto.setVersioExp(1);
        expedientTerrestreDto.setTipusExp("TYPE");
        expedientTerrestreDto.setEstatExp("STAT");
        expedientTerrestreDto.setDataInici(LocalDateTime.now());
        expedientTerrestreDto.setDataUltMod(LocalDateTime.now());
        expedientTerrestreDto.setIdExpOrig(2L);
        expedientTerrestreDto.setUsuari("USER1");
        expedientTerrestreDto.setCopia("Y");

        ExpedientTerrestre result = mapper.fromDtoToEntity(expedientTerrestreDto);

        assertThat(result).isNotNull();
        assertThat(result.getIdExp()).isEqualTo(expedientTerrestreDto.getIdExp());
        assertThat(result.getVersioExp()).isEqualTo(expedientTerrestreDto.getVersioExp());
        assertThat(result.getTipusExp()).isEqualTo(expedientTerrestreDto.getTipusExp());
        assertThat(result.getEstatExp()).isEqualTo(expedientTerrestreDto.getEstatExp());
        assertThat(result.getDataInici()).isEqualTo(expedientTerrestreDto.getDataInici());
        assertThat(result.getDataUltMod()).isEqualTo(expedientTerrestreDto.getDataUltMod());
        assertThat(result.getIdExpOrig()).isEqualTo(expedientTerrestreDto.getIdExpOrig());
        assertThat(result.getUsuari()).isEqualTo(expedientTerrestreDto.getUsuari());
        assertThat(result.getCopia()).isEqualTo(expedientTerrestreDto.getCopia());
    }

    @Test
    void shouldMapEntityToDto() {
        ExpedientTerrestre expedientTerrestre = new ExpedientTerrestre();
        expedientTerrestre.setIdExp(1L);
        expedientTerrestre.setVersioExp(1);
        expedientTerrestre.setTipusExp("TYPE");
        expedientTerrestre.setEstatExp("STAT");
        expedientTerrestre.setDataInici(LocalDateTime.now());
        expedientTerrestre.setDataUltMod(LocalDateTime.now());
        expedientTerrestre.setIdExpOrig(2L);
        expedientTerrestre.setUsuari("USER1");
        expedientTerrestre.setCopia("Y");

        ExpedientTerrestreDto result = mapper.fromEntityToDto(expedientTerrestre);

        assertThat(result).isNotNull();
        assertThat(result.getIdExp()).isEqualTo(expedientTerrestre.getIdExp());
        assertThat(result.getVersioExp()).isEqualTo(expedientTerrestre.getVersioExp());
        assertThat(result.getTipusExp()).isEqualTo(expedientTerrestre.getTipusExp());
        assertThat(result.getEstatExp()).isEqualTo(expedientTerrestre.getEstatExp());
        assertThat(result.getDataInici()).isEqualTo(expedientTerrestre.getDataInici());
        assertThat(result.getDataUltMod()).isEqualTo(expedientTerrestre.getDataUltMod());
        assertThat(result.getIdExpOrig()).isEqualTo(expedientTerrestre.getIdExpOrig());
        assertThat(result.getUsuari()).isEqualTo(expedientTerrestre.getUsuari());
        assertThat(result.getCopia()).isEqualTo(expedientTerrestre.getCopia());
    }
}
