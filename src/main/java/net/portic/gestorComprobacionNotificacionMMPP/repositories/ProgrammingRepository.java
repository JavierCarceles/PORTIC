package net.portic.gestorComprobacionNotificacionMMPP.repositories;

import net.portic.gestorComprobacionNotificacionMMPP.domain.models.ProgAndRequestIds;
import net.portic.gestorComprobacionNotificacionMMPP.domain.models.Programming;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProgrammingRepository extends JpaRepository<Programming, ProgAndRequestIds>,
        JpaSpecificationExecutor<Programming> {

    List<Programming> getListProgrammings(LocalDateTime dateFilter);

    List<Programming> getListExpiredProgrammings(LocalDateTime dateFilter);

}
