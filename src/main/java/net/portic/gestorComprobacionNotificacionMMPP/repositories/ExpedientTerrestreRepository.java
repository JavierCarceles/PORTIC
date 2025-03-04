package net.portic.gestorComprobacionNotificacionMMPP.repositories;

import net.portic.gestorComprobacionNotificacionMMPP.domain.models.ExpedientTerrestre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpedientTerrestreRepository extends JpaRepository<ExpedientTerrestre, Long>, JpaSpecificationExecutor<ExpedientTerrestre> {
    ExpedientTerrestre findByIdExp(Long idExp);
}
