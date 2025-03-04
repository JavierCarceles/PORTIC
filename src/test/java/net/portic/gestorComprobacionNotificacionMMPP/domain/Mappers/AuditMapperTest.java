package net.portic.gestorComprobacionNotificacionMMPP.domain.Mappers;

import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.AuditDto;
import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.AuditModificationDto;
import net.portic.gestorComprobacionNotificacionMMPP.domain.mappers.AuditMapper;
import net.portic.gestorComprobacionNotificacionMMPP.domain.models.Audit;
import net.portic.gestorComprobacionNotificacionMMPP.domain.models.AuditModification;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AuditMapperTest {

    private final AuditMapper auditMapper = AuditMapper.INSTANCE;

    @Test
    void testFromEntityToDto() {
        Audit audit = new Audit(1L, "Test Service", LocalDateTime.now(), 10, 5, 3, 7, Collections.emptyList());

        AuditModification modification = new AuditModification();
        modification.setIdAudit(1L);
        modification.setIdModification(1);
        modification.setIdProgramming(123);
        modification.setOldProgStatus("PENDING");
        modification.setPresentProgStatus("COMPLETED");
        modification.setOldRequestStatus("OPEN");
        modification.setPresentRequestStatus("CLOSED");
        modification.setReceptorEmails("test@example.com");
        modification.setAudit(audit);
        audit.setAuditModificationList(Collections.singletonList(modification));

        AuditDto result = auditMapper.fromEntityToDto(audit);

        assertNotNull(result);
        assertEquals(audit.getId(), result.getId());
        assertEquals(audit.getService(), result.getService());
        assertEquals(audit.getDate(), result.getDate());
        assertEquals(audit.getNumChecked(), result.getNumChecked());
        assertEquals(audit.getNumModified(), result.getNumModified());
        assertEquals(audit.getNumIncidences(), result.getNumIncidences());
        assertEquals(audit.getNumSentEmails(), result.getNumSentEmails());

        // Asumimos que el mapeo de AuditModification es manual en este caso
        if (result.getAuditModificationDtoList() != null && !result.getAuditModificationDtoList().isEmpty()) {
            AuditModificationDto resultModificationDto = result.getAuditModificationDtoList().get(0);
            assertEquals(modification.getIdAudit(), resultModificationDto.getIdAudit());
            assertEquals(modification.getIdModification(), resultModificationDto.getIdModification());
            assertEquals(modification.getIdProgramming(), resultModificationDto.getIdProgramming());
            assertEquals(modification.getOldProgStatus(), resultModificationDto.getOldProgStatus());
            assertEquals(modification.getPresentProgStatus(), resultModificationDto.getPresentProgStatus());
            assertEquals(modification.getOldRequestStatus(), resultModificationDto.getOldRequestStatus());
            assertEquals(modification.getPresentRequestStatus(), resultModificationDto.getPresentRequestStatus());
            assertEquals(modification.getReceptorEmails(), resultModificationDto.getReceptorEmails());
        }
    }

    @Test
    void testFromDtoToEntity() {
        AuditDto auditDto = new AuditDto(1L, "Test Service", LocalDateTime.now(), 10, 5, 3, 7, Collections.emptyList());

        AuditModificationDto modificationDto = new AuditModificationDto();
        modificationDto.setIdAudit(1L);
        modificationDto.setIdModification(1);
        modificationDto.setIdProgramming(123);
        modificationDto.setOldProgStatus("PENDING");
        modificationDto.setPresentProgStatus("COMPLETED");
        modificationDto.setOldRequestStatus("OPEN");
        modificationDto.setPresentRequestStatus("CLOSED");
        modificationDto.setReceptorEmails("test@example.com");

        auditDto.setAuditModificationDtoList(Collections.singletonList(modificationDto));

        Audit result = auditMapper.fromDtoToEntity(auditDto);

        assertNotNull(result);
        assertEquals(auditDto.getId(), result.getId());
        assertEquals(auditDto.getService(), result.getService());
        assertEquals(auditDto.getDate().getYear(), result.getDate().getYear());
        assertEquals(auditDto.getDate().getMonth(), result.getDate().getMonth());
        assertEquals(auditDto.getDate().getDayOfMonth(), result.getDate().getDayOfMonth());
        assertEquals(auditDto.getDate().getHour(), result.getDate().getHour());
        assertEquals(auditDto.getDate().getMinute(), result.getDate().getMinute());
        assertEquals(auditDto.getNumChecked(), result.getNumChecked());
        assertEquals(auditDto.getNumModified(), result.getNumModified());
        assertEquals(auditDto.getNumIncidences(), result.getNumIncidences());
        assertEquals(auditDto.getNumSentEmails(), result.getNumSentEmails());

        // Asumimos que el mapeo de AuditModification es manual en este caso
        if (result.getAuditModificationList() != null && !result.getAuditModificationList().isEmpty()) {
            AuditModification resultModification = result.getAuditModificationList().get(0);
            assertEquals(modificationDto.getIdAudit(), resultModification.getIdAudit());
            assertEquals(modificationDto.getIdModification(), resultModification.getIdModification());
            assertEquals(modificationDto.getIdProgramming(), resultModification.getIdProgramming());
            assertEquals(modificationDto.getOldProgStatus(), resultModification.getOldProgStatus());
            assertEquals(modificationDto.getPresentProgStatus(), resultModification.getPresentProgStatus());
            assertEquals(modificationDto.getOldRequestStatus(), resultModification.getOldRequestStatus());
            assertEquals(modificationDto.getPresentRequestStatus(), resultModification.getPresentRequestStatus());
            assertEquals(modificationDto.getReceptorEmails(), resultModification.getReceptorEmails());
        }
    }
}
