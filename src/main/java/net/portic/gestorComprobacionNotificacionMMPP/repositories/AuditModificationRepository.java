package net.portic.gestorComprobacionNotificacionMMPP.repositories;

import net.portic.gestorComprobacionNotificacionMMPP.domain.models.AuditModification;
import net.portic.gestorComprobacionNotificacionMMPP.domain.models.AuditModificationIds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditModificationRepository
        extends JpaRepository<AuditModification, AuditModificationIds>, JpaSpecificationExecutor<AuditModification> {

}
