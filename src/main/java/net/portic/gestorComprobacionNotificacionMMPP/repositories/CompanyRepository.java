package net.portic.gestorComprobacionNotificacionMMPP.repositories;


import net.portic.gestorComprobacionNotificacionMMPP.domain.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>,
        JpaSpecificationExecutor<Company> {

    Company findByNifCompany(String nifCompany);

}
