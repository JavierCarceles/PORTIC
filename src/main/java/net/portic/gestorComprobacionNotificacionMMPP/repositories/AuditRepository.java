package net.portic.gestorComprobacionNotificacionMMPP.repositories;

import net.portic.gestorComprobacionNotificacionMMPP.domain.models.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository
        extends JpaRepository<Audit, Long>, JpaSpecificationExecutor<Audit> {

}
