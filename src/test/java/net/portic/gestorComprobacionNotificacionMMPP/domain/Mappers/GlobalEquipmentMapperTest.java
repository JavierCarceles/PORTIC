package net.portic.gestorComprobacionNotificacionMMPP.domain.Mappers;

import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.GlobalEquipmentDto;
import net.portic.gestorComprobacionNotificacionMMPP.domain.mappers.GlobalEquipmentMapper;
import net.portic.gestorComprobacionNotificacionMMPP.domain.models.GlobalEquipment;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalEquipmentMapperTest {

    private final GlobalEquipmentMapper mapper = GlobalEquipmentMapper.INSTANCE;

    @Test
    void shouldMapDtoToEntity() {
        GlobalEquipmentDto globalEquipmentDto = new GlobalEquipmentDto();
        globalEquipmentDto.setInternalDoc(1L);
        globalEquipmentDto.setInternalVs(100);
        globalEquipmentDto.setPlate("ABCDE123456");
        globalEquipmentDto.setCode("XYZ");
        globalEquipmentDto.setContainerType("TYPE");
        globalEquipmentDto.setGroupage(2);
        globalEquipmentDto.setPackages(100);
        globalEquipmentDto.setGrossWeight(2000);
        globalEquipmentDto.setNetWeight(1500);
        globalEquipmentDto.setStowagePlace("A1");

        GlobalEquipment result = mapper.fromDtoToEntity(globalEquipmentDto);

        assertThat(result).isNotNull();
        assertThat(result.getInternalDoc()).isEqualTo(globalEquipmentDto.getInternalDoc());
        assertThat(result.getInternalVs()).isEqualTo(globalEquipmentDto.getInternalVs());
        assertThat(result.getPlate()).isEqualTo(globalEquipmentDto.getPlate());
        assertThat(result.getCode()).isEqualTo(globalEquipmentDto.getCode());
        assertThat(result.getContainerType()).isEqualTo(globalEquipmentDto.getContainerType());
        assertThat(result.getGroupage()).isEqualTo(globalEquipmentDto.getGroupage());
        assertThat(result.getPackages()).isEqualTo(globalEquipmentDto.getPackages());
        assertThat(result.getGrossWeight()).isEqualTo(globalEquipmentDto.getGrossWeight());
        assertThat(result.getNetWeight()).isEqualTo(globalEquipmentDto.getNetWeight());
        assertThat(result.getStowagePlace()).isEqualTo(globalEquipmentDto.getStowagePlace());
    }

    @Test
    void shouldMapEntityToDto() {
        GlobalEquipment globalEquipment = new GlobalEquipment();
        globalEquipment.setInternalDoc(1L);
        globalEquipment.setInternalVs(100);
        globalEquipment.setPlate("ABCDE123456");
        globalEquipment.setCode("XYZ");
        globalEquipment.setContainerType("TYPE");
        globalEquipment.setGroupage(2);
        globalEquipment.setPackages(100);
        globalEquipment.setGrossWeight(2000);
        globalEquipment.setNetWeight(1500);
        globalEquipment.setStowagePlace("A1");

        GlobalEquipmentDto result = mapper.fromEntityToDto(globalEquipment);

        assertThat(result).isNotNull();
        assertThat(result.getInternalDoc()).isEqualTo(globalEquipment.getInternalDoc());
        assertThat(result.getInternalVs()).isEqualTo(globalEquipment.getInternalVs());
        assertThat(result.getPlate()).isEqualTo(globalEquipment.getPlate());
        assertThat(result.getCode()).isEqualTo(globalEquipment.getCode());
        assertThat(result.getContainerType()).isEqualTo(globalEquipment.getContainerType());
        assertThat(result.getGroupage()).isEqualTo(globalEquipment.getGroupage());
        assertThat(result.getPackages()).isEqualTo(globalEquipment.getPackages());
        assertThat(result.getGrossWeight()).isEqualTo(globalEquipment.getGrossWeight());
        assertThat(result.getNetWeight()).isEqualTo(globalEquipment.getNetWeight());
        assertThat(result.getStowagePlace()).isEqualTo(globalEquipment.getStowagePlace());
    }
}
