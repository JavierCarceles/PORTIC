package net.portic.gestorComprobacionNotificacionMMPP.repositories;

import net.portic.gestorComprobacionNotificacionMMPP.domain.models.MessageAttributes;
import net.portic.gestorComprobacionNotificacionMMPP.domain.models.MessageAttributesIds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageAttributesRepository extends JpaRepository<MessageAttributes, MessageAttributesIds>,
        JpaSpecificationExecutor<MessageAttributes> {

    Long getMaxMsgIdByNumEntreguese(String numEntreguese);

}
