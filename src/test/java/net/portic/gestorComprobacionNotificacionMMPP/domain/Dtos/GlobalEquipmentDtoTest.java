package net.portic.gestorComprobacionNotificacionMMPP.domain.Dtos;

import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.AdditionalEquipmentDto;
import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.GlobalEquipmentDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class GlobalEquipmentDtoTest {

    private GlobalEquipmentDto globalEquipmentDto;
    private AdditionalEquipmentDto additionalEquipmentDto;

    @BeforeEach
    public void setUp() {
        globalEquipmentDto = new GlobalEquipmentDto();
        globalEquipmentDto.setInternalDoc(123456L);
        globalEquipmentDto.setInternalVs(1);
        globalEquipmentDto.setPlate("ABC123");
        globalEquipmentDto.setCode("XYZ");
        globalEquipmentDto.setContainerType("Type A");
        globalEquipmentDto.setGroupage(10);
        globalEquipmentDto.setPackages(5);
        globalEquipmentDto.setGrossWeight(1000);
        globalEquipmentDto.setNetWeight(800);
        globalEquipmentDto.setStowagePlace("Place A");
        additionalEquipmentDto = new AdditionalEquipmentDto();
        globalEquipmentDto.setAdditionalEquipmentDto(additionalEquipmentDto);
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(123456L, globalEquipmentDto.getInternalDoc());
        assertEquals(1, globalEquipmentDto.getInternalVs());
        assertEquals("ABC123", globalEquipmentDto.getPlate());
        assertEquals("XYZ", globalEquipmentDto.getCode());
        assertEquals("Type A", globalEquipmentDto.getContainerType());
        assertEquals(10, globalEquipmentDto.getGroupage());
        assertEquals(5, globalEquipmentDto.getPackages());
        assertEquals(1000, globalEquipmentDto.getGrossWeight());
        assertEquals(800, globalEquipmentDto.getNetWeight());
        assertEquals("Place A", globalEquipmentDto.getStowagePlace());
        assertEquals(additionalEquipmentDto, globalEquipmentDto.getAdditionalEquipmentDto());
        globalEquipmentDto.setInternalDoc(987654L);
        assertEquals(987654L, globalEquipmentDto.getInternalDoc());
        globalEquipmentDto.setInternalVs(2);
        assertEquals(2, globalEquipmentDto.getInternalVs());
        globalEquipmentDto.setPlate("XYZ456");
        assertEquals("XYZ456", globalEquipmentDto.getPlate());
        globalEquipmentDto.setCode("ABC");
        assertEquals("ABC", globalEquipmentDto.getCode());
        globalEquipmentDto.setContainerType("Type B");
        assertEquals("Type B", globalEquipmentDto.getContainerType());
        globalEquipmentDto.setGroupage(20);
        assertEquals(20, globalEquipmentDto.getGroupage());
        globalEquipmentDto.setPackages(10);
        assertEquals(10, globalEquipmentDto.getPackages());
        globalEquipmentDto.setGrossWeight(2000);
        assertEquals(2000, globalEquipmentDto.getGrossWeight());
        globalEquipmentDto.setNetWeight(1600);
        assertEquals(1600, globalEquipmentDto.getNetWeight());
        globalEquipmentDto.setStowagePlace("Place B");
        assertEquals("Place B", globalEquipmentDto.getStowagePlace());
        AdditionalEquipmentDto newAdditionalEquipmentDto = new AdditionalEquipmentDto();
        globalEquipmentDto.setAdditionalEquipmentDto(newAdditionalEquipmentDto);
        assertEquals(newAdditionalEquipmentDto, globalEquipmentDto.getAdditionalEquipmentDto());
    }

    @Test
    void testDefaultValues() {
        GlobalEquipmentDto defaultGlobalEquipmentDto = new GlobalEquipmentDto();
        assertEquals(0L, defaultGlobalEquipmentDto.getInternalDoc());
        assertEquals(0, defaultGlobalEquipmentDto.getInternalVs());
        assertNull(defaultGlobalEquipmentDto.getPlate());
        assertNull(defaultGlobalEquipmentDto.getCode());
        assertNull(defaultGlobalEquipmentDto.getContainerType());
        assertEquals(0, defaultGlobalEquipmentDto.getGroupage());
        assertEquals(0, defaultGlobalEquipmentDto.getPackages());
        assertEquals(0, defaultGlobalEquipmentDto.getGrossWeight());
        assertEquals(0, defaultGlobalEquipmentDto.getNetWeight());
        assertNull(defaultGlobalEquipmentDto.getStowagePlace());
        assertNull(defaultGlobalEquipmentDto.getAdditionalEquipmentDto());
    }
}

