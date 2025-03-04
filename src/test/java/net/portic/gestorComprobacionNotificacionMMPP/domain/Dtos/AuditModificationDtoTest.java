package net.portic.gestorComprobacionNotificacionMMPP.domain.Dtos;

import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.AuditModificationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AuditModificationDtoTest {

    private AuditModificationDto auditModificationDto;

    @BeforeEach
    public void setUp() {
        auditModificationDto = new AuditModificationDto();
        auditModificationDto.setIdAudit(12345L);
        auditModificationDto.setIdModification(54321);
        auditModificationDto.setIdProgramming(67890);
        auditModificationDto.setOldProgStatus("PENDING");
        auditModificationDto.setPresentProgStatus("APPROVED");
        auditModificationDto.setOldRequestStatus("IN_PROGRESS");
        auditModificationDto.setPresentRequestStatus("COMPLETED");
        auditModificationDto.setReceptorEmails("test@example.com");
    }

    @Test
    public void testGettersAndSetters() {
        assertEquals(12345L, auditModificationDto.getIdAudit());
        assertEquals(54321, auditModificationDto.getIdModification());
        assertEquals(67890, auditModificationDto.getIdProgramming());
        assertEquals("PENDING", auditModificationDto.getOldProgStatus());
        assertEquals("APPROVED", auditModificationDto.getPresentProgStatus());
        assertEquals("IN_PROGRESS", auditModificationDto.getOldRequestStatus());
        assertEquals("COMPLETED", auditModificationDto.getPresentRequestStatus());
        assertEquals("test@example.com", auditModificationDto.getReceptorEmails());

        auditModificationDto.setIdAudit(67890L);
        assertEquals(67890L, auditModificationDto.getIdAudit());

        auditModificationDto.setIdModification(98765);
        assertEquals(98765, auditModificationDto.getIdModification());

        auditModificationDto.setIdProgramming(43210);
        assertEquals(43210, auditModificationDto.getIdProgramming());

        auditModificationDto.setOldProgStatus("REJECTED");
        assertEquals("REJECTED", auditModificationDto.getOldProgStatus());

        auditModificationDto.setPresentProgStatus("REVIEW");
        assertEquals("REVIEW", auditModificationDto.getPresentProgStatus());

        auditModificationDto.setOldRequestStatus("ON_HOLD");
        assertEquals("ON_HOLD", auditModificationDto.getOldRequestStatus());

        auditModificationDto.setPresentRequestStatus("APPROVED");
        assertEquals("APPROVED", auditModificationDto.getPresentRequestStatus());

        auditModificationDto.setReceptorEmails("newtest@example.com");
        assertEquals("newtest@example.com", auditModificationDto.getReceptorEmails());
    }

    @Test
    public void testDefaultValues() {
        AuditModificationDto defaultAuditModificationDto = new AuditModificationDto();
        assertEquals(0L, defaultAuditModificationDto.getIdAudit());
        assertEquals(0, defaultAuditModificationDto.getIdModification());
        assertEquals(0, defaultAuditModificationDto.getIdProgramming());
        assertNull(defaultAuditModificationDto.getOldProgStatus());
        assertNull(defaultAuditModificationDto.getPresentProgStatus());
        assertNull(defaultAuditModificationDto.getOldRequestStatus());
        assertNull(defaultAuditModificationDto.getPresentRequestStatus());
        assertNull(defaultAuditModificationDto.getReceptorEmails());
    }
}
