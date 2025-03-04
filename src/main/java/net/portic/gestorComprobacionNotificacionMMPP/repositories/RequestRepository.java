package net.portic.gestorComprobacionNotificacionMMPP.repositories;

import net.portic.gestorComprobacionNotificacionMMPP.domain.models.ProgAndRequestIds;
import net.portic.gestorComprobacionNotificacionMMPP.domain.models.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Request, ProgAndRequestIds>, JpaSpecificationExecutor<Request> {

}
