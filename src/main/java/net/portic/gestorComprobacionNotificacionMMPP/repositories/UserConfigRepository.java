package net.portic.gestorComprobacionNotificacionMMPP.repositories;

import net.portic.gestorComprobacionNotificacionMMPP.domain.models.UserConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserConfigRepository extends JpaRepository<UserConfig, String>,
        JpaSpecificationExecutor<UserConfig> {

    UserConfig findByUserAndIdCompany(String user, long idCompany);

}
