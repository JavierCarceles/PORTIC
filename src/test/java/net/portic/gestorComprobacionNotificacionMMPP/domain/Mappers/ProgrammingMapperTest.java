package net.portic.gestorComprobacionNotificacionMMPP.domain.Mappers;

import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.ProgrammingDto;
import net.portic.gestorComprobacionNotificacionMMPP.domain.mappers.ProgrammingMapper;
import net.portic.gestorComprobacionNotificacionMMPP.domain.models.Programming;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class ProgrammingMapperTest {

    private final ProgrammingMapper mapper = ProgrammingMapper.INSTANCE;

    @Test
    void shouldMapDtoToEntity() {
        ProgrammingDto programmingDto = new ProgrammingDto();
        programmingDto.setIdProgramming(1L);
        programmingDto.setStatus("PR");
        programmingDto.setInternalDoc(12345L);
        programmingDto.setInternalVs(100);
        programmingDto.setCreationDate(LocalDateTime.now());
        programmingDto.setExpirationDate(LocalDateTime.now().plusDays(1));
        programmingDto.setCreatorUser("USER1");
        programmingDto.setNotifyIncidences("Y");
        programmingDto.setExpType("M");
        programmingDto.setIdExp(10L);
        programmingDto.setNifCompanyUser("COMPANY123");

        Programming result = mapper.fromDtoToEntity(programmingDto);

        assertThat(result).isNotNull();
        assertThat(result.getIdProgramming()).isEqualTo(programmingDto.getIdProgramming());
        assertThat(result.getStatus()).isEqualTo(programmingDto.getStatus());
        assertThat(result.getInternalDoc()).isEqualTo(programmingDto.getInternalDoc());
        assertThat(result.getInternalVs()).isEqualTo(programmingDto.getInternalVs());
        assertThat(result.getCreationDate()).isEqualTo(programmingDto.getCreationDate());
        assertThat(result.getExpirationDate()).isEqualTo(programmingDto.getExpirationDate());
        assertThat(result.getCreatorUser()).isEqualTo(programmingDto.getCreatorUser());
        assertThat(result.getNotifyIncidences()).isEqualTo(programmingDto.getNotifyIncidences());
        assertThat(result.getExpType()).isEqualTo(programmingDto.getExpType());
        assertThat(result.getIdExp()).isEqualTo(programmingDto.getIdExp());
        assertThat(result.getNifCompanyUser()).isEqualTo(programmingDto.getNifCompanyUser());
    }

    @Test
    void shouldMapEntityToDto() {
        Programming programming = new Programming();
        programming.setIdProgramming(1L);
        programming.setStatus("PR");
        programming.setInternalDoc(12345L);
        programming.setInternalVs(100);
        programming.setCreationDate(LocalDateTime.now());
        programming.setExpirationDate(LocalDateTime.now().plusDays(1));
        programming.setCreatorUser("USER1");
        programming.setNotifyIncidences("Y");
        programming.setExpType("M");
        programming.setIdExp(10L);
        programming.setNifCompanyUser("COMPANY123");
        programming.setRequest(null);
        programming.setExpedientMaritim(null);
        programming.setExpedientTerrestre(null);
        programming.setUserConfig(null);
        programming.setGlobalEquipmentList(Collections.emptyList());

        ProgrammingDto result = mapper.fromEntityToDto(programming);

        assertThat(result).isNotNull();
        assertThat(result.getIdProgramming()).isEqualTo(programming.getIdProgramming());
        assertThat(result.getStatus()).isEqualTo(programming.getStatus());
        assertThat(result.getInternalDoc()).isEqualTo(programming.getInternalDoc());
        assertThat(result.getInternalVs()).isEqualTo(programming.getInternalVs());
        assertThat(result.getCreationDate()).isEqualTo(programming.getCreationDate());
        assertThat(result.getExpirationDate()).isEqualTo(programming.getExpirationDate());
        assertThat(result.getCreatorUser()).isEqualTo(programming.getCreatorUser());
        assertThat(result.getNotifyIncidences()).isEqualTo(programming.getNotifyIncidences());
        assertThat(result.getExpType()).isEqualTo(programming.getExpType());
        assertThat(result.getIdExp()).isEqualTo(programming.getIdExp());
        assertThat(result.getNifCompanyUser()).isEqualTo(programming.getNifCompanyUser());
    }
}
