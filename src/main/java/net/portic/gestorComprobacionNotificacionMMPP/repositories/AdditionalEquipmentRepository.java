package net.portic.gestorComprobacionNotificacionMMPP.repositories;

import net.portic.gestorComprobacionNotificacionMMPP.domain.models.AdditionalEquipment;
import net.portic.gestorComprobacionNotificacionMMPP.domain.models.AdditionalEquipmentIds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AdditionalEquipmentRepository  extends JpaRepository<AdditionalEquipment, AdditionalEquipmentIds>,
        JpaSpecificationExecutor<AdditionalEquipment> {

    AdditionalEquipment findByInternalDocAndInternalVsAndPlate(long internalDoc, int internalVs, String plate);

}
