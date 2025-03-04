package net.portic.gestorComprobacionNotificacionMMPP.domain.Dtos;
import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.CompanyDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompanyDtoTest {

    private CompanyDto companyDto;

    @BeforeEach
    public void setUp() {
        companyDto = new CompanyDto();
        companyDto.setIdCompany(123L);
        companyDto.setNifCompany("A12345678");
    }

    @Test
    public void testGettersAndSetters() {
        assertEquals(123L, companyDto.getIdCompany());
        assertEquals("A12345678", companyDto.getNifCompany());
        companyDto.setIdCompany(456L);
        assertEquals(456L, companyDto.getIdCompany());
        companyDto.setNifCompany("B87654321");
        assertEquals("B87654321", companyDto.getNifCompany());
    }

    @Test
    public void testDefaultValues() {
        CompanyDto defaultCompanyDto = new CompanyDto();
        assertEquals(0L, defaultCompanyDto.getIdCompany());
        assertNull(defaultCompanyDto.getNifCompany());
    }
}
