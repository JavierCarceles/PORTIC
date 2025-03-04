package net.portic.gestorComprobacionNotificacionMMPP.domain.Mappers;

import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.ExpedientMaritimDto;
import net.portic.gestorComprobacionNotificacionMMPP.domain.mappers.ExpedientMaritimMapper;
import net.portic.gestorComprobacionNotificacionMMPP.domain.models.ExpedientMaritim;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ExpedientMaritimMapperTest {

    private final ExpedientMaritimMapper mapper = ExpedientMaritimMapper.INSTANCE;

    @Test
    void shouldMapDtoToEntity() {
        ExpedientMaritimDto expedientMaritimDto = new ExpedientMaritimDto();
        expedientMaritimDto.setIdExp(1L);
        expedientMaritimDto.setPortCall("Port of Barcelona");
        expedientMaritimDto.setShipName("Ocean Explorer");
        expedientMaritimDto.setShipOmi("1234567");

        ExpedientMaritim result = mapper.fromDtoToEntity(expedientMaritimDto);

        assertThat(result).isNotNull();
        assertThat(result.getIdExp()).isEqualTo(expedientMaritimDto.getIdExp());
        assertThat(result.getPortCall()).isEqualTo(expedientMaritimDto.getPortCall());
        assertThat(result.getShipName()).isEqualTo(expedientMaritimDto.getShipName());
        assertThat(result.getShipOmi()).isEqualTo(expedientMaritimDto.getShipOmi());
    }

    @Test
    void shouldMapEntityToDto() {
        ExpedientMaritim expedientMaritim = new ExpedientMaritim();
        expedientMaritim.setIdExp(1L);
        expedientMaritim.setPortCall("Port of Barcelona");
        expedientMaritim.setShipName("Ocean Explorer");
        expedientMaritim.setShipOmi("1234567");

        ExpedientMaritimDto result = mapper.fromEntityToDto(expedientMaritim);

        assertThat(result).isNotNull();
        assertThat(result.getIdExp()).isEqualTo(expedientMaritim.getIdExp());
        assertThat(result.getPortCall()).isEqualTo(expedientMaritim.getPortCall());
        assertThat(result.getShipName()).isEqualTo(expedientMaritim.getShipName());
        assertThat(result.getShipOmi()).isEqualTo(expedientMaritim.getShipOmi());
    }
}
