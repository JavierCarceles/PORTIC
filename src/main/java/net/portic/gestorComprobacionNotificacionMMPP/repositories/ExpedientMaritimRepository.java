package net.portic.gestorComprobacionNotificacionMMPP.repositories;

import net.portic.gestorComprobacionNotificacionMMPP.domain.models.ExpedientMaritim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpedientMaritimRepository extends JpaRepository<ExpedientMaritim, Long>, JpaSpecificationExecutor<ExpedientMaritim> {

    ExpedientMaritim findByIdExp(Long idExp);

}
