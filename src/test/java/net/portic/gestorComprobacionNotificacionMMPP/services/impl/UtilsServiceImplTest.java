package net.portic.gestorComprobacionNotificacionMMPP.services.impl;

import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.*;
import net.portic.gestorComprobacionNotificacionMMPP.domain.models.Company;
import net.portic.gestorComprobacionNotificacionMMPP.domain.models.UserConfig;
import net.portic.gestorComprobacionNotificacionMMPP.repositories.GlobalEquipmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UtilsServiceImplTest {


    @InjectMocks
    private UtilsServiceImpl utilsService;

    @Mock
    private GlobalEquipmentRepository globalEquipmentRepository;

    @Mock
    private ProgWithIncidencesDto mockProg;

    @Mock
    private ProgrammingDto mockProgData;

    @Mock
    private RequestDto mockRequest;

    @Mock
    private ExpedientMaritimDto mockExpedient;

    @Mock
    private GlobalEquipmentDto mockEquipment;

    @Mock
    private AdditionalEquipmentDto mockAdditionalEquipment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSetContentEmailTerrestre() {
        // Configuración de mocks para el caso Terrestre
        when(mockAdditionalEquipment.getNumEntreguese()).thenReturn("12345");
        when(mockEquipment.getAdditionalEquipmentDto()).thenReturn(mockAdditionalEquipment);
        when(mockProgData.getRequest()).thenReturn(mockRequest);
        when(mockProgData.getExpedientMaritim()).thenReturn(null); // No hay datos marítimos para Terrestre
        when(mockProgData.getGlobalEquipmentDtosList()).thenReturn(Collections.singletonList(mockEquipment));
        when(mockProg.getProgData()).thenReturn(mockProgData);

        ArrayList<ProgWithIncidencesDto> orderedListMT = new ArrayList<>();
        orderedListMT.add(mockProg);

        EmailToSendDto email = utilsService.setContentEmail(orderedListMT, 0, 0, 1);

        assertNotNull(email);
        assertTrue(email.getContent().contains("se anuló el entréguese"));
    }

    @Test
    void testSetContentEmailMaritimo() {
        // Configuración de mocks para el caso Marítimo
        when(mockAdditionalEquipment.getNumEntreguese()).thenReturn("67890");
        when(mockEquipment.getAdditionalEquipmentDto()).thenReturn(mockAdditionalEquipment);
        when(mockProgData.getRequest()).thenReturn(mockRequest);
        when(mockProgData.getExpedientMaritim()).thenReturn(mockExpedient); // Incluye datos marítimos
        when(mockProgData.getGlobalEquipmentDtosList()).thenReturn(Collections.singletonList(mockEquipment));
        when(mockProg.getProgData()).thenReturn(mockProgData);

        ArrayList<ProgWithIncidencesDto> orderedListMT = new ArrayList<>();
        orderedListMT.add(mockProg);

        EmailToSendDto email = utilsService.setContentEmail(orderedListMT, 0, 0, 2);

        assertNotNull(email);
        assertTrue(email.getContent().contains("rechazado por la terminal"));
    }

    @Test
    void testAppendIncidenceMessageMaritimo() {
        StringBuilder message = new StringBuilder();
        utilsService.appendIncidenceMessageMaritimo(message, 1, "001", "Puerto A", "Buque X", "12345");

        assertTrue(message.toString().contains("se anuló el entréguese"));
    }

    @Test
    void testAppendIncidenceMessageTerrestre() {
        StringBuilder message = new StringBuilder();
        utilsService.appendIncidenceMessageTerrestre(message, 2, "002", "67890");

        assertTrue(message.toString().contains("rechazado por la terminal"));
    }

    @Test
    void testFindLastDummy() {
        when(globalEquipmentRepository.getLastDummy(1234L,123)).thenReturn(5);

        int lastDummy = utilsService.findLastDummy(1234L,123);

        assertEquals(5, lastDummy);
    }

    @Test
    void testFindLastDummyNullPointerException() {
        when(globalEquipmentRepository.getLastDummy(1234L,123)).thenThrow(new NullPointerException());

        int lastDummy = utilsService.findLastDummy(1234L,123);

        assertEquals(0, lastDummy);
    }

    @Test
    void generateAuditId() {
        LocalDateTime date = LocalDateTime.of(2023, 9, 19, 14, 30, 45);
        long expectedId = 20230919143045L;

        long generatedId = utilsService.generateAuditId(date);

        assertEquals(expectedId, generatedId);
    }

    @Test
    void getEmailsMultiple() {
        UserConfig userConfig = new UserConfig();
        userConfig.setUser("Javier");
        userConfig.setEmail("javi@gmail.com; javi2@gmail.com");

        Company company = new Company();
        company.setNifCompany("B12345678");
        userConfig.setCompany(company);

        List<SentEmailDto> emails = utilsService.getEmailsForOneUser(userConfig);

        assertEquals(2, emails.size());
        assertEquals("javi@gmail.com", emails.get(0).getEmail());
        assertEquals("javi2@gmail.com", emails.get(1).getEmail());
        assertEquals("Javier", emails.get(0).getCustomerName());
        assertEquals("B12345678", emails.get(0).getCustomerNif());
    }

    @Test
    void getEmailsSingle() {
        UserConfig userConfig = new UserConfig();
        userConfig.setUser("Javier");
        userConfig.setEmail("javi@gmail.com");

        Company company = new Company();
        company.setNifCompany("B12345678");
        userConfig.setCompany(company);

        List<SentEmailDto> emails = utilsService.getEmailsForOneUser(userConfig);

        assertEquals(1, emails.size());
        assertEquals("javi@gmail.com", emails.get(0).getEmail());
        assertEquals("Javier", emails.get(0).getCustomerName());
        assertEquals("B12345678", emails.get(0).getCustomerNif());
    }

    @Test
    void checkMatchingData() {
        SentEmailDto data1 = new SentEmailDto();
        data1.setCustomerName("Javier");
        data1.setCustomerNif("B12345678");
        data1.setEmail("javi@gmail.com");

        SentEmailDto data2 = new SentEmailDto();
        data2.setCustomerName("Javier");
        data2.setCustomerNif("B12345678");
        data2.setEmail("javi@gmail.com");

        boolean result = utilsService.checkCustomerData(data1, data2);

        assertTrue(result);
    }

    @Test
    void checkNonMatchingData() {
        SentEmailDto data1 = new SentEmailDto();
        data1.setCustomerName("Javier");
        data1.setCustomerNif("B12345678");
        data1.setEmail("javi@gmail.com");

        SentEmailDto data2 = new SentEmailDto();
        data2.setCustomerName("Carlos");
        data2.setCustomerNif("B87654321");
        data2.setEmail("carlos@gmail.com");

        boolean result = utilsService.checkCustomerData(data1, data2);

        assertFalse(result);
    }

    @Test
    void ordenarIncidencias() {
        ProgWithIncidencesDto maritime1 = new ProgWithIncidencesDto();
        ProgWithIncidencesDto maritime2 = new ProgWithIncidencesDto();
        ProgWithIncidencesDto terrestrial1 = new ProgWithIncidencesDto();
        ProgWithIncidencesDto withoutEmailsOrUserConfig = new ProgWithIncidencesDto();

        ProgrammingDto progDataMaritime1 = new ProgrammingDto();
        ProgrammingDto progDataMaritime2 = new ProgrammingDto();
        ProgrammingDto progDataTerrestrial1 = new ProgrammingDto();
        ProgrammingDto progDataWithoutEmails = new ProgrammingDto();

        RequestDto reqMaritime1 = new RequestDto();
        RequestDto reqMaritime2 = new RequestDto();
        RequestDto reqTerrestrial1 = new RequestDto();

        ExpedientMaritimDto expMaritime1 = new ExpedientMaritimDto();
        ExpedientMaritimDto expMaritime2 = new ExpedientMaritimDto();

        reqMaritime1.setDocumentNumber("001");
        reqMaritime2.setDocumentNumber("002");
        reqTerrestrial1.setDocumentNumber("003");

        expMaritime1.setPortCall("A");
        expMaritime2.setPortCall("B");

        progDataMaritime1.setExpType("M");
        progDataMaritime1.setRequest(reqMaritime1);
        progDataMaritime1.setExpedientMaritim(expMaritime1);
        progDataMaritime1.setUserConfig(new UserConfigDto());

        progDataMaritime2.setExpType("M");
        progDataMaritime2.setRequest(reqMaritime2);
        progDataMaritime2.setExpedientMaritim(expMaritime2);
        progDataMaritime2.setUserConfig(new UserConfigDto());

        progDataTerrestrial1.setExpType("T");
        progDataTerrestrial1.setRequest(reqTerrestrial1);
        progDataTerrestrial1.setExpedientMaritim(expMaritime1);
        progDataTerrestrial1.setUserConfig(null);
        List<SentEmailDto> sentEmailsTerrestrial = Collections.singletonList(new SentEmailDto());
        terrestrial1.setSentEmails(sentEmailsTerrestrial);

        progDataWithoutEmails.setExpType("M");
        progDataWithoutEmails.setRequest(reqMaritime1);
        progDataWithoutEmails.setExpedientMaritim(expMaritime1);
        progDataWithoutEmails.setUserConfig(null);
        withoutEmailsOrUserConfig.setSentEmails(null);

        maritime1.setProgData(progDataMaritime1);
        maritime2.setProgData(progDataMaritime2);
        terrestrial1.setProgData(progDataTerrestrial1);
        withoutEmailsOrUserConfig.setProgData(progDataWithoutEmails);

        maritime1.setSentEmails(Collections.singletonList(new SentEmailDto()));
        maritime2.setSentEmails(Collections.emptyList());
        terrestrial1.setSentEmails(sentEmailsTerrestrial);
        withoutEmailsOrUserConfig.setSentEmails(null);

        List<ProgWithIncidencesDto> dataIncidences = Arrays.asList(maritime1, maritime2, terrestrial1, withoutEmailsOrUserConfig);

        ArrayList<ProgWithIncidencesDto> result = utilsService.ordenarIncidencias(dataIncidences);

        assertEquals(3, result.size());
        assertEquals(maritime1, result.get(0));
        assertEquals(maritime2, result.get(1));
        assertEquals(terrestrial1, result.get(2));
    }

    @Test
    void testAppendIncidenceMessageMaritimoRechazado() {
        StringBuilder message = new StringBuilder();
        utilsService.appendIncidenceMessageMaritimo(message, 2, "002", "Puerto B", "Buque Y", "67890");
        assertTrue(message.toString().contains("rechazado por la terminal"));
    }

    @Test
    void testAppendIncidenceMessageMaritimoVencido() {
        StringBuilder message = new StringBuilder();
        utilsService.appendIncidenceMessageMaritimo(message, 3, "003", "Puerto C", "Buque Z", "98765");
        assertTrue(message.toString().contains("ha vencido sin haber sido recogido el contenedor"));
    }

    @Test
    void testAppendIncidenceMessageMaritimoDefault() {
        StringBuilder message = new StringBuilder();
        utilsService.appendIncidenceMessageMaritimo(message, 4, "004", "Puerto D", "Buque W", "54321");
        assertTrue(message.toString().contains("ha vencido sin haber sido recogido el contenedor"));
    }

    @Test
    void testAppendIncidenceMessageTerrestreCancelado() {
        StringBuilder message = new StringBuilder();
        utilsService.appendIncidenceMessageTerrestre(message, 1, "001", "12345");
        assertTrue(message.toString().contains("se anuló el entréguese"));
    }

    @Test
    void testAppendIncidenceMessageTerrestreVencido() {
        StringBuilder message = new StringBuilder();
        utilsService.appendIncidenceMessageTerrestre(message, 3, "003", "98765");
        assertTrue(message.toString().contains("ha vencido sin haber sido recogido el contenedor"));
    }

    @Test
    void testAppendIncidenceMessageTerrestreDefault() {
        StringBuilder message = new StringBuilder();
        utilsService.appendIncidenceMessageTerrestre(message, 4, "004", "54321");
        assertTrue(message.toString().contains("ha vencido sin haber sido recogido el contenedor"));
    }

    @Test
    void testAppendIncidenceMessageTerrestreCanceladoEmptyNumDocument() {
        StringBuilder message = new StringBuilder();
        utilsService.appendIncidenceMessageTerrestre(message, 1, "", "12345");
        assertTrue(message.toString().contains("Se ha abortado la programación de MMPP terrestre porque se anuló el entréguese 12345 asociado."));
    }

    @Test
    void testAppendIncidenceMessageTerrestreCanceladoNullNumDocument() {
        StringBuilder message = new StringBuilder();
        utilsService.appendIncidenceMessageTerrestre(message, 1, null, "12345");
        assertTrue(message.toString().contains("Se ha abortado la programación de MMPP terrestre porque se anuló el entréguese 12345 asociado."));
    }

    @Test
    void testAppendIncidenceMessageTerrestreRechazadoNullNumDocument() {
        StringBuilder message = new StringBuilder();
        utilsService.appendIncidenceMessageTerrestre(message, 2, null, "67890");
        assertTrue(message.toString().contains("Se ha abortado la programación de MMPP terrestre porque el entréguese 67890 asociado fue rechazado por la terminal."));
    }

    @Test
    void testAppendIncidenceMessageTerrestreVencidoNullNumDocument() {
        StringBuilder message = new StringBuilder();
        utilsService.appendIncidenceMessageTerrestre(message, 3, null, "98765");
        assertTrue(message.toString().contains("Se ha abortado la programación de MMPP terrestre porque el entréguese 98765 asociado ha vencido sin haber sido recogido el contenedor."));
    }

    @Test
    void testAppendIncidenceMessageTerrestreNoDuplicateMessages() {
        StringBuilder message = new StringBuilder();
        utilsService.appendIncidenceMessageTerrestre(message, 1, "001", "12345");
        utilsService.appendIncidenceMessageTerrestre(message, 1, "001", "12345"); // Intento de añadir duplicado
        String result = message.toString();
        int occurrences = result.split("se anuló el entréguese", -1).length - 1;
        assertEquals(1, occurrences, "El mensaje no debe duplicarse.");
    }

}
