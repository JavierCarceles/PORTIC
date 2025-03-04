package net.portic.gestorComprobacionNotificacionMMPP.domain.Dtos;

import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.AppTrkMsgDto;
import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.GlobalEquipmentDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.AdditionalEquipmentDto;


class AdditionalEquipmentDtoTest {

    private AdditionalEquipmentDto additionalEquipmentDto;
    private LocalDateTime now;

    @BeforeEach
    public void setUp() {
        now = LocalDateTime.now();

        additionalEquipmentDto = new AdditionalEquipmentDto(
                12345L,
                1,
                56789,
                "XYZ123",
                "REF456",
                "ISSUING123",
                "BOOK789",
                "ENT123",
                "DEPOSIT",
                now.plusDays(5),
                now.plusDays(2),
                now.plusDays(3),
                "Active",
                "FILE123",
                "MSG456",
                null,
                "",
                "",
                new AppTrkMsgDto(),
                new GlobalEquipmentDto(),
                123456
        );
    }

    @Test
    void testGetters() {
        assertEquals(12345L, additionalEquipmentDto.getInternalDoc());
        assertEquals(1, additionalEquipmentDto.getInternalVs());
        assertEquals(56789, additionalEquipmentDto.getEquipmentNumber());
        assertEquals("XYZ123", additionalEquipmentDto.getPlate());
        assertEquals("REF456", additionalEquipmentDto.getRefContainer());
        assertEquals("ISSUING123", additionalEquipmentDto.getIssuing());
        assertEquals("BOOK789", additionalEquipmentDto.getBooking());
        assertEquals("ENT123", additionalEquipmentDto.getNumEntreguese());
        assertEquals("DEPOSIT", additionalEquipmentDto.getDeposit());
        assertEquals(now.plusDays(5), additionalEquipmentDto.getExpirationDate());
        assertEquals(now.plusDays(2), additionalEquipmentDto.getDatePrevEntr());
        assertEquals(now.plusDays(3), additionalEquipmentDto.getDatePrevPr());
        assertEquals("Active", additionalEquipmentDto.getState());
        assertEquals("FILE123", additionalEquipmentDto.getNumRefFile());
        assertEquals("MSG456", additionalEquipmentDto.getNumMsg());
    }

    @Test
    void testSetters() {
        additionalEquipmentDto.setInternalDoc(54321L);
        assertEquals(54321L, additionalEquipmentDto.getInternalDoc());

        additionalEquipmentDto.setInternalVs(2);
        assertEquals(2, additionalEquipmentDto.getInternalVs());

        additionalEquipmentDto.setEquipmentNumber(98765);
        assertEquals(98765, additionalEquipmentDto.getEquipmentNumber());

        additionalEquipmentDto.setPlate("ABC987");
        assertEquals("ABC987", additionalEquipmentDto.getPlate());

        additionalEquipmentDto.setRefContainer("REF789");
        assertEquals("REF789", additionalEquipmentDto.getRefContainer());

        additionalEquipmentDto.setIssuing("ISSUING789");
        assertEquals("ISSUING789", additionalEquipmentDto.getIssuing());

        additionalEquipmentDto.setBooking("BOOK456");
        assertEquals("BOOK456", additionalEquipmentDto.getBooking());

        additionalEquipmentDto.setNumEntreguese("ENT789");
        assertEquals("ENT789", additionalEquipmentDto.getNumEntreguese());

        additionalEquipmentDto.setDeposit("NEWDEPOSIT");
        assertEquals("NEWDEPOSIT", additionalEquipmentDto.getDeposit());

        additionalEquipmentDto.setExpirationDate(now.plusDays(10));
        assertEquals(now.plusDays(10), additionalEquipmentDto.getExpirationDate());

        additionalEquipmentDto.setDatePrevEntr(now.plusDays(4));
        assertEquals(now.plusDays(4), additionalEquipmentDto.getDatePrevEntr());

        additionalEquipmentDto.setDatePrevPr(now.plusDays(6));
        assertEquals(now.plusDays(6), additionalEquipmentDto.getDatePrevPr());

        additionalEquipmentDto.setState("Inactive");
        assertEquals("Inactive", additionalEquipmentDto.getState());

        additionalEquipmentDto.setNumRefFile("FILE789");
        assertEquals("FILE789", additionalEquipmentDto.getNumRefFile());

        additionalEquipmentDto.setNumMsg("MSG789");
        assertEquals("MSG789", additionalEquipmentDto.getNumMsg());

        additionalEquipmentDto.setFechaActualizacion(now.plusDays(1));
        assertEquals(now.plusDays(1), additionalEquipmentDto.getFechaActualizacion());

        additionalEquipmentDto.setProceso("NEWPROCESS");
        assertEquals("NEWPROCESS", additionalEquipmentDto.getProceso());

        AppTrkMsgDto newAppTrkMsg = new AppTrkMsgDto();
        additionalEquipmentDto.setAppTrkMsg(newAppTrkMsg);
        assertEquals(newAppTrkMsg, additionalEquipmentDto.getAppTrkMsg());

        GlobalEquipmentDto newGlobalEquipment = new GlobalEquipmentDto();
        additionalEquipmentDto.setGlobalEquipment(newGlobalEquipment);
        assertEquals(newGlobalEquipment, additionalEquipmentDto.getGlobalEquipment());
    }


}
