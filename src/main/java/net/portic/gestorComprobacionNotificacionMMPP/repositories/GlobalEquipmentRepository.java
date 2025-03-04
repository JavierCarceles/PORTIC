package net.portic.gestorComprobacionNotificacionMMPP.repositories;

import net.portic.gestorComprobacionNotificacionMMPP.domain.models.GlobalEquipment;
import net.portic.gestorComprobacionNotificacionMMPP.domain.models.GlobalEquipmentIds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalEquipmentRepository
        extends JpaRepository<GlobalEquipment, GlobalEquipmentIds>,
        JpaSpecificationExecutor<GlobalEquipment> {

    Integer getLastDummy(long internalDoc, int internalVs);

}
