package net.portic.gestorComprobacionNotificacionMMPP.repositories;

import net.portic.gestorComprobacionNotificacionMMPP.domain.models.AppTrkMsg;
import net.portic.gestorComprobacionNotificacionMMPP.domain.models.AppTrkMsgIds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AppTrkMsgRepository extends JpaRepository<AppTrkMsg, AppTrkMsgIds>,
        JpaSpecificationExecutor<AppTrkMsg> {

    AppTrkMsg findByIdEsmtMsg(Long idEsmtMsg);
}
