package net.portic.gestorComprobacionNotificacionMMPP.domain.Dtos;

import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.AuditDto;
import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.AuditModificationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AuditDtoTest {

    private AuditDto auditDto;
    private LocalDateTime now;
    private List<AuditModificationDto> auditModificationDtoList;

    @BeforeEach
    public void setUp() {
        now = LocalDateTime.now();
        auditModificationDtoList = new ArrayList<>();
        auditDto = new AuditDto();
        auditDto.setId(1L);
        auditDto.setService("TestService");
        auditDto.setDate(now);
        auditDto.setNumChecked(5);
        auditDto.setNumModified(3);
        auditDto.setNumIncidences(2);
        auditDto.setNumSentEmails(4);
        auditDto.setAuditModificationDtoList(auditModificationDtoList);
    }

    @Test
    public void testGettersAndSetters() {
        assertEquals(1L, auditDto.getId());
        assertEquals("TestService", auditDto.getService());
        assertEquals(now, auditDto.getDate());
        assertEquals(5, auditDto.getNumChecked());
        assertEquals(3, auditDto.getNumModified());
        assertEquals(2, auditDto.getNumIncidences());
        assertEquals(4, auditDto.getNumSentEmails());
        assertEquals(auditModificationDtoList, auditDto.getAuditModificationDtoList());

        auditDto.setId(2L);
        assertEquals(2L, auditDto.getId());

        auditDto.setService("NewService");
        assertEquals("NewService", auditDto.getService());

        LocalDateTime newDate = now.plusDays(1);
        auditDto.setDate(newDate);
        assertEquals(newDate, auditDto.getDate());

        auditDto.setNumChecked(10);
        assertEquals(10, auditDto.getNumChecked());

        auditDto.setNumModified(8);
        assertEquals(8, auditDto.getNumModified());

        auditDto.setNumIncidences(7);
        assertEquals(7, auditDto.getNumIncidences());

        auditDto.setNumSentEmails(6);
        assertEquals(6, auditDto.getNumSentEmails());

        List<AuditModificationDto> newAuditModificationList = new ArrayList<>();
        auditDto.setAuditModificationDtoList(newAuditModificationList);
        assertEquals(newAuditModificationList, auditDto.getAuditModificationDtoList());
    }

    @Test
    public void testDefaultValues() {
        AuditDto defaultAuditDto = new AuditDto();
        assertEquals(0L, defaultAuditDto.getId());
        assertNull(defaultAuditDto.getService());
        assertNull(defaultAuditDto.getDate());
        assertEquals(0, defaultAuditDto.getNumChecked());
        assertEquals(0, defaultAuditDto.getNumModified());
        assertEquals(0, defaultAuditDto.getNumIncidences());
        assertEquals(0, defaultAuditDto.getNumSentEmails());
        assertNull(defaultAuditDto.getAuditModificationDtoList());
    }
}
