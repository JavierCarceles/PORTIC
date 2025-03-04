package net.portic.gestorComprobacionNotificacionMMPP.domain.Models;


import net.portic.gestorComprobacionNotificacionMMPP.domain.models.Programming;
import net.portic.gestorComprobacionNotificacionMMPP.domain.models.Request;
import net.portic.gestorComprobacionNotificacionMMPP.domain.models.ExpedientMaritim;
import net.portic.gestorComprobacionNotificacionMMPP.domain.models.GlobalEquipment;
import net.portic.gestorComprobacionNotificacionMMPP.domain.models.UserConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class ProgrammingTest {

    private Programming programming;
    private Request requestMock;
    private ExpedientMaritim expedientMaritimMock;
    private List<GlobalEquipment> globalEquipmentList;

    @BeforeEach
    public void setUp() {
        programming = new Programming();

        // Mocking related entities
        requestMock = mock(Request.class);
        expedientMaritimMock = mock(ExpedientMaritim.class);
        globalEquipmentList = new ArrayList<>();

        // Setting up test data
        programming.setIdProgramming(1001L);
        programming.setStatus("PR");
        programming.setInternalDoc(2002L);
        programming.setInternalVs(1);
        programming.setCreationDate(LocalDateTime.now());
        programming.setExpirationDate(LocalDateTime.now().plusDays(30));
        programming.setCreatorUser("testUser");
        programming.setNotifyIncidences("Y");
        programming.setExpType("M");
        programming.setIdExp(1234L);
        programming.setNifCompanyUser("12345678A");
        programming.setRequest(requestMock);
        programming.setExpedientMaritim(expedientMaritimMock);
        programming.setGlobalEquipmentList(globalEquipmentList);
    }

    @Test
    void testProgrammingFields() {
        // Test basic fields
        assertEquals(1001L, programming.getIdProgramming());
        assertEquals("PR", programming.getStatus());
        assertEquals(2002L, programming.getInternalDoc());
        assertEquals(1, programming.getInternalVs());
        assertNotNull(programming.getCreationDate());
        assertNotNull(programming.getExpirationDate());
        assertEquals("testUser", programming.getCreatorUser());
        assertEquals("Y", programming.getNotifyIncidences());
        assertEquals("M", programming.getExpType());
        assertEquals(1234L, programming.getIdExp());
        assertEquals("12345678A", programming.getNifCompanyUser());
    }

    @Test
    void testProgrammingRequestAndExpedient() {
        // Test the relationships (Request and Expedient)
        assertNotNull(programming.getRequest());
        assertNotNull(programming.getExpedientMaritim());
    }

    @Test
    void testGlobalEquipmentList() {
        // Test the globalEquipmentList relationship
        assertNotNull(programming.getGlobalEquipmentList());
        assertEquals(0, programming.getGlobalEquipmentList().size());

        // Add an item to the list and check
        GlobalEquipment equipment = mock(GlobalEquipment.class);
        programming.getGlobalEquipmentList().add(equipment);
        assertEquals(1, programming.getGlobalEquipmentList().size());
    }

    @Test
    void testProgrammingTransientFieldUserConfig() {
        // Testing Transient field
        UserConfig userConfig = mock(UserConfig.class);
        programming.setUserConfig(userConfig);

        assertNotNull(programming.getUserConfig());
        assertEquals(userConfig, programming.getUserConfig());
    }

}
