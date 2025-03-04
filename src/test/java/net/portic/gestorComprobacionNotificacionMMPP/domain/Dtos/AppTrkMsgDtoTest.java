package net.portic.gestorComprobacionNotificacionMMPP.domain.Dtos;

import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.AdditionalEquipmentDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.AppTrkMsgDto;

class AppTrkMsgDtoTest {

    private AppTrkMsgDto appTrkMsgDto;
    private LocalDateTime now;
    private AdditionalEquipmentDto additionalEquipmentDto;

    @BeforeEach
    public void setUp() {
        now = LocalDateTime.now();
        additionalEquipmentDto = new AdditionalEquipmentDto();
        appTrkMsgDto = new AppTrkMsgDto();
        appTrkMsgDto.setNumRefFile(123456L);
        appTrkMsgDto.setNumMsg(654321L);
        appTrkMsgDto.setProcessStatus("IN_PROGRESS");
        appTrkMsgDto.setProcessStartDate(now);
        appTrkMsgDto.setAdditionalEquipment(additionalEquipmentDto);
    }

    @Test
    public void testGettersAndSetters() {
        assertEquals(123456L, appTrkMsgDto.getNumRefFile());
        assertEquals(654321L, appTrkMsgDto.getNumMsg());
        assertEquals("IN_PROGRESS", appTrkMsgDto.getProcessStatus());
        assertEquals(now, appTrkMsgDto.getProcessStartDate());
        assertEquals(additionalEquipmentDto, appTrkMsgDto.getAdditionalEquipment());

        appTrkMsgDto.setNumRefFile(987654L);
        assertEquals(987654L, appTrkMsgDto.getNumRefFile());

        appTrkMsgDto.setNumMsg(123987L);
        assertEquals(123987L, appTrkMsgDto.getNumMsg());

        appTrkMsgDto.setProcessStatus("COMPLETED");
        assertEquals("COMPLETED", appTrkMsgDto.getProcessStatus());

        LocalDateTime newStartDate = now.plusDays(1);
        appTrkMsgDto.setProcessStartDate(newStartDate);
        assertEquals(newStartDate, appTrkMsgDto.getProcessStartDate());

        AdditionalEquipmentDto newAdditionalEquipment = new AdditionalEquipmentDto();
        appTrkMsgDto.setAdditionalEquipment(newAdditionalEquipment);
        assertEquals(newAdditionalEquipment, appTrkMsgDto.getAdditionalEquipment());
    }

    @Test
    public void testDefaultValues() {
        AppTrkMsgDto defaultAppTrkMsgDto = new AppTrkMsgDto();
        assertEquals(0L, defaultAppTrkMsgDto.getNumRefFile());
        assertEquals(0L, defaultAppTrkMsgDto.getNumMsg());
        assertNull(defaultAppTrkMsgDto.getProcessStatus());
        assertNull(defaultAppTrkMsgDto.getProcessStartDate());
        assertNull(defaultAppTrkMsgDto.getAdditionalEquipment());
    }
}

