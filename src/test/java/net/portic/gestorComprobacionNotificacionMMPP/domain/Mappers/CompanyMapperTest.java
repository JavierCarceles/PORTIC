package net.portic.gestorComprobacionNotificacionMMPP.domain.Mappers;

import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.CompanyDto;
import net.portic.gestorComprobacionNotificacionMMPP.domain.mappers.CompanyMapper;
import net.portic.gestorComprobacionNotificacionMMPP.domain.models.Company;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompanyMapperTest {

    private final CompanyMapper mapper = CompanyMapper.INSTANCE;

    @Test
    void shouldMapDtoToEntity() {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setIdCompany(1L);
        companyDto.setNifCompany("12345678A");

        Company result = mapper.fromDtoToEntity(companyDto);

        assertThat(result).isNotNull();
        assertThat(result.getIdCompany()).isEqualTo(companyDto.getIdCompany());
        assertThat(result.getNifCompany()).isEqualTo(companyDto.getNifCompany());
    }

    @Test
    void shouldMapEntityToDto() {
        Company company = new Company();
        company.setIdCompany(1L);
        company.setNifCompany("12345678A");

        CompanyDto result = mapper.fromEntityToDto(company);

        assertThat(result).isNotNull();
        assertThat(result.getIdCompany()).isEqualTo(company.getIdCompany());
        assertThat(result.getNifCompany()).isEqualTo(company.getNifCompany());
    }
}
