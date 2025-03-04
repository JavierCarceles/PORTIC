package net.portic.gestorComprobacionNotificacionMMPP.domain.Dtos;

import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.AdditionalEquipmentDto;
import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.AppTrkMsgDto;
import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.EquipmentDto;
import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.GlobalEquipmentDto;
import net.portic.gestorComprobacionNotificacionMMPP.domain.models.AppTrkMsg;
import net.portic.gestorComprobacionNotificacionMMPP.domain.models.GlobalEquipment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EquipmentDtoTest {

    private EquipmentDto equipmentDto;
    private GlobalEquipmentDto globalEquipmentDto;
    private AdditionalEquipmentDto additionalEquipmentDto;
    private LocalDateTime now;

    @BeforeEach
    public void setUp() {
        now = LocalDateTime.now();

        // Inicializando los DTOs con valores de prueba
        globalEquipmentDto = new GlobalEquipmentDto(
                1L,
                2,
                "ABC123",
                "CODE123",
                "20ft",
                10,
                5,
                1000,
                900,
                "A1",
                new AdditionalEquipmentDto()
        );

        additionalEquipmentDto = new AdditionalEquipmentDto(
                1L,
                2,
                123,
                "XYZ456",
                "REF123",
                "Issuer1",
                "Booking1",
                "Entreguese1",
                "Deposit1",
                now.plusDays(30),
                now.plusDays(5),
                now.plusDays(10),
                "Active",
                "REFFILE123",
                "MSG123",
                null,
                "",
                "",
                new AppTrkMsgDto(),
                new GlobalEquipmentDto(),
                123456
        );

        equipmentDto = new EquipmentDto(globalEquipmentDto, additionalEquipmentDto);
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(globalEquipmentDto, equipmentDto.getGlobalEquipmentDto());
        assertEquals(additionalEquipmentDto, equipmentDto.getAdditionalEquipmentDto());
        GlobalEquipmentDto newGlobalEquipmentDto = new GlobalEquipmentDto(
                2L,
                3,
                "DEF456",
                "CODE456",
                "40ft",
                20,
                10,
                2000,
                1800,
                "B2",
                new AdditionalEquipmentDto()
        );
        equipmentDto.setGlobalEquipmentDto(newGlobalEquipmentDto);
        assertEquals(newGlobalEquipmentDto, equipmentDto.getGlobalEquipmentDto());
        AdditionalEquipmentDto newAdditionalEquipmentDto = new AdditionalEquipmentDto(
                2L,
                3,
                456,
                "UVW789",
                "REF456",
                "Issuer2",
                "Booking2",
                "Entreguese2",
                "Deposit2",
                now.plusDays(60),
                now.plusDays(15),
                now.plusDays(20),
                "Inactive",
                "REFFILE456",
                "MSG456",
                null,
                "",
                "",
                new AppTrkMsgDto(),
                new GlobalEquipmentDto(),
                123456
        );
        equipmentDto.setAdditionalEquipmentDto(newAdditionalEquipmentDto);
        assertEquals(newAdditionalEquipmentDto, equipmentDto.getAdditionalEquipmentDto());
    }

    @Test
    void testDefaultValues() {
        EquipmentDto defaultEquipmentDto = new EquipmentDto();
        assertNull(defaultEquipmentDto.getGlobalEquipmentDto());
        assertNull(defaultEquipmentDto.getAdditionalEquipmentDto());
    }
}

