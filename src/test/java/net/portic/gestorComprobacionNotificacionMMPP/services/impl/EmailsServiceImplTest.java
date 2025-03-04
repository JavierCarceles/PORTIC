package net.portic.gestorComprobacionNotificacionMMPP.services.impl;

import lombok.SneakyThrows;
import net.portic.gestorComprobacionNotificacionMMPP.config.BuzonesProperties;
import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

import org.mockito.junit.jupiter.MockitoExtension;

import javax.mail.*;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailsServiceImplTest {

    private static final String inicioExpiradaT = "Ha expirado la programación de MMPP terrestres asociada a los entrégueses: ";
    private static final String MATTER_INCIDENCES = "Incidencias en las programaciones de Mercancías peligrosas";
    private static final String TITLE_INCIDENCES = "Comprobación de los estados de los entréguese";

    @Mock
    private BuzonesProperties mockBuzonesProperties;

    @Mock
    private UtilsServiceImpl utilsService;

    @InjectMocks
    private EmailsServiceImpl emailsService;

    @BeforeEach
    void setUp() {
        emailsService = new EmailsServiceImpl(mockBuzonesProperties, utilsService);
    }

    @SneakyThrows
    @Test
    void testDeleteEquipments() {

        ArrayList<ProgWithIncidencesDto> orderedListMT = new ArrayList<>();

        ProgWithIncidencesDto prog = new ProgWithIncidencesDto();
        ProgrammingDto progData = new ProgrammingDto();

        List<GlobalEquipmentDto> equipmentList = new ArrayList<>();

        GlobalEquipmentDto equipmentWithNumEntreguese = new GlobalEquipmentDto();
        AdditionalEquipmentDto additionalEquipmentWithNum = new AdditionalEquipmentDto();
        additionalEquipmentWithNum.setNumEntreguese("12345");
        equipmentWithNumEntreguese.setAdditionalEquipmentDto(additionalEquipmentWithNum);
        equipmentList.add(equipmentWithNumEntreguese);

        GlobalEquipmentDto equipmentWithoutNumEntreguese = new GlobalEquipmentDto();
        AdditionalEquipmentDto additionalEquipmentWithoutNum = new AdditionalEquipmentDto();
        additionalEquipmentWithoutNum.setNumEntreguese("");
        equipmentWithoutNumEntreguese.setAdditionalEquipmentDto(additionalEquipmentWithoutNum);
        equipmentList.add(equipmentWithoutNumEntreguese);

        progData.setGlobalEquipmentDtosList(equipmentList);
        prog.setProgData(progData);

        orderedListMT.add(prog);

        Method method = EmailsServiceImpl.class.getDeclaredMethod("deleteEquipments", ArrayList.class);
        method.setAccessible(true);
        method.invoke(emailsService, orderedListMT);

        assertEquals(1, prog.getProgData().getGlobalEquipmentDtosList().size());
        assertEquals("12345", prog.getProgData().getGlobalEquipmentDtosList().get(0).getAdditionalEquipmentDto().getNumEntreguese());
    }

    @Test
    @SneakyThrows
    void deberiaConstruirPrimerEmailExitosamente() {
        List<EmailToSendDto> listaEmailsEnviar = new ArrayList<>();
        ArrayList<ProgWithIncidencesDto> listaOrdenadaMT = new ArrayList<>();

        ProgWithIncidencesDto progConIncidencias = new ProgWithIncidencesDto();
        progConIncidencias.setTrk(123L);
        progConIncidencias.setStatus("ACTIVO");
        progConIncidencias.setDate(LocalDateTime.now());

        ProgrammingDto datosProgramacion = new ProgrammingDto();
        datosProgramacion.setIdProgramming(456L);
        datosProgramacion.setStatus("EN_PROGRESO");
        datosProgramacion.setInternalDoc(789L);
        datosProgramacion.setInternalVs(1);
        datosProgramacion.setCreationDate(LocalDateTime.now());
        datosProgramacion.setExpirationDate(LocalDateTime.now().plusDays(30));
        datosProgramacion.setCreatorUser("usuarioCreador");
        datosProgramacion.setNifCompanyUser("NIFCompañia");

        RequestDto requestDto = new RequestDto();
        requestDto.setDocumentNumber("DOC123");
        datosProgramacion.setRequest(requestDto);

        GlobalEquipmentDto equipo = new GlobalEquipmentDto();
        AdditionalEquipmentDto additionalEquipmentDto = new AdditionalEquipmentDto();
        additionalEquipmentDto.setNumEntreguese("ENT123");
        equipo.setAdditionalEquipmentDto(additionalEquipmentDto);
        datosProgramacion.setGlobalEquipmentDtosList(List.of(equipo));

        progConIncidencias.setProgData(datosProgramacion);

        SentEmailDto emailEnviado = new SentEmailDto();
        emailEnviado.setEmail("cliente@ejemplo.com");
        progConIncidencias.setSentEmails(List.of(emailEnviado));

        listaOrdenadaMT.add(progConIncidencias);

        int indice = 0;
        int contadorEquipos = 0;
        int incidencias = 2;

        EmailToSendDto emailSimulado = new EmailToSendDto();
        emailSimulado.setTitle(TITLE_INCIDENCES);
        emailSimulado.setMatter(MATTER_INCIDENCES);
        emailSimulado.setContent(inicioExpiradaT + "ENT123");
        when(utilsService.setContentEmail(listaOrdenadaMT, indice, contadorEquipos, incidencias)).thenReturn(emailSimulado);

        Method metodoConstruirPrimerEmail = EmailsServiceImpl.class.getDeclaredMethod("buildFirstEmail", List.class, ArrayList.class, int.class, int.class, int.class);
        metodoConstruirPrimerEmail.setAccessible(true);
        metodoConstruirPrimerEmail.invoke(emailsService, listaEmailsEnviar, listaOrdenadaMT, indice, contadorEquipos, incidencias);

        assertEquals(1, listaEmailsEnviar.size());
        assertEquals(emailEnviado, listaEmailsEnviar.get(0).getCustomerData());
        assertEquals(MATTER_INCIDENCES, listaEmailsEnviar.get(0).getMatter());
        assertEquals(inicioExpiradaT + "ENT123", listaEmailsEnviar.get(0).getContent());
    }

    @Test
    void sendEmailThrowException() {
        when(mockBuzonesProperties.getReboteMailhost()).thenReturn("invalidMailHost");
        SentEmailDto sentEmailDto = new SentEmailDto();
        sentEmailDto.setEmail("hola@gmail.com");

        EmailToSendDto emailToSendDto = new EmailToSendDto();
        emailToSendDto.setTitle("Test Title");
        emailToSendDto.setContent("This is a test email content.");
        emailToSendDto.setMatter("Test Matter");
        emailToSendDto.setCustomerData(sentEmailDto);
        assertThatThrownBy(() -> emailsService.sendEmail(emailToSendDto))
                .isInstanceOf(RuntimeException.class)
                .hasCauseInstanceOf(MessagingException.class);
        verify(mockBuzonesProperties).getReboteMailhost();
    }

    @Test
    @SneakyThrows
    void testProcessEmailIncidencesEmailUpdatedSuccessfully() {
        List<EmailToSendDto> emailToSendList = new ArrayList<>();
        ArrayList<ProgWithIncidencesDto> orderedListMT = new ArrayList<>();

        EmailToSendDto existingEmail = new EmailToSendDto();
        existingEmail.setContent("Contenido inicial ");
        SentEmailDto sentEmailDto = new SentEmailDto();
        existingEmail.setCustomerData(sentEmailDto);
        emailToSendList.add(existingEmail);

        ProgWithIncidencesDto progWithIncidence = new ProgWithIncidencesDto();
        List<SentEmailDto> sentEmails = new ArrayList<>();
        sentEmails.add(sentEmailDto);
        progWithIncidence.setSentEmails(sentEmails);
        orderedListMT.add(progWithIncidence);

        when(utilsService.checkCustomerData(existingEmail.getCustomerData(), sentEmailDto)).thenReturn(true);

        EmailToSendDto updatedEmail = new EmailToSendDto();
        updatedEmail.setContent("contenido actualizado ");
        when(utilsService.setContentEmail(any(), anyInt(), anyInt(), anyInt())).thenReturn(updatedEmail);

        Method method = EmailsServiceImpl.class.getDeclaredMethod(
                "processEmailIncidences",
                List.class, ArrayList.class, int.class, int.class, int.class
        );
        method.setAccessible(true);

        boolean result = (boolean) method.invoke(emailsService, emailToSendList, orderedListMT, 0, 1, 1);

        assertTrue(result);
        assertTrue(emailToSendList.get(0).getContent().contains("contenido actualizado "));
        assertEquals(1, emailToSendList.size());
    }

    @Test
    @SneakyThrows
    void testAddEmailInfoIncidences_EmailAddedForMultipleSentEmails() {
        ArrayList<ProgWithIncidencesDto> orderedListMTWithoutRepetitions = new ArrayList<>();
        List<EmailToSendDto> emailToSendList = new ArrayList<>();

        ProgWithIncidencesDto progWithIncidencesDto = new ProgWithIncidencesDto();
        List<SentEmailDto> sentEmails = List.of(new SentEmailDto(), new SentEmailDto()); // Dos customerData
        progWithIncidencesDto.setSentEmails(sentEmails);
        orderedListMTWithoutRepetitions.add(progWithIncidencesDto);

        EmailToSendDto baseEmail = new EmailToSendDto();
        baseEmail.setTitle("Comprobación de los estados de los entréguese");
        baseEmail.setMatter("Incidencias en las programaciones de Mercancías peligrosas");
        baseEmail.setContent("Contenido de prueba para incidencias");
        emailToSendList.add(baseEmail);

        Method method = EmailsServiceImpl.class.getDeclaredMethod(
                "addEmailInfoIncidences",
                ArrayList.class, List.class
        );
        method.setAccessible(true);

        method.invoke(emailsService, orderedListMTWithoutRepetitions, emailToSendList);

        assertEquals(2, emailToSendList.size());
        assertEquals(TITLE_INCIDENCES, emailToSendList.get(1).getTitle());
        assertEquals(MATTER_INCIDENCES, emailToSendList.get(1).getMatter());
        assertEquals("Contenido de prueba para incidencias", emailToSendList.get(1).getContent());
        assertEquals(sentEmails.get(1), emailToSendList.get(1).getCustomerData());
    }

    @Test
    @SneakyThrows
    void testIsIncidence_IncidenceTrue() {
        List<ProgWithIncidencesDto> dataIncidences = List.of(new ProgWithIncidencesDto());
        List<ExpiredProgDto> dataExpired = null;

        Method method = EmailsServiceImpl.class.getDeclaredMethod(
                "isIncidence", List.class, List.class
        );
        method.setAccessible(true);

        boolean result = (boolean) method.invoke(emailsService, dataIncidences, dataExpired);

        assertTrue(result);
    }

    @Test
    @SneakyThrows
    void testIsIncidence_IncidenceFalse() {
        List<ProgWithIncidencesDto> dataIncidences = null;
        List<ExpiredProgDto> dataExpired = List.of(new ExpiredProgDto());

        Method method = EmailsServiceImpl.class.getDeclaredMethod(
                "isIncidence", List.class, List.class
        );
        method.setAccessible(true);

        boolean result = (boolean) method.invoke(emailsService, dataIncidences, dataExpired);

        assertFalse(result);
    }

    @Test
    @SneakyThrows
    void testIsExpired_ExpiredTrue() {
        List<ProgWithIncidencesDto> dataIncidences = null;
        List<ExpiredProgDto> dataExpired = List.of(new ExpiredProgDto());

        Method method = EmailsServiceImpl.class.getDeclaredMethod(
                "isExpired", List.class, List.class
        );
        method.setAccessible(true);

        boolean result = (boolean) method.invoke(emailsService, dataIncidences, dataExpired);

        assertTrue(result);
    }

    @Test
    @SneakyThrows
    void testIsExpired_ExpiredFalse() {
        List<ProgWithIncidencesDto> dataIncidences = List.of(new ProgWithIncidencesDto());
        List<ExpiredProgDto> dataExpired = List.of(new ExpiredProgDto());

        Method method = EmailsServiceImpl.class.getDeclaredMethod(
                "isExpired", List.class, List.class
        );
        method.setAccessible(true);

        boolean result = (boolean) method.invoke(emailsService, dataIncidences, dataExpired);

        assertFalse(result);
    }

    @Test
    @SneakyThrows
    void testIsSameProgData_SameProgDataTrue() {
        List<ProgWithIncidencesDto> orderedListMT = new ArrayList<>();

        ProgrammingDto progData1 = new ProgrammingDto();
        progData1.setInternalDoc(123L);
        progData1.setInternalVs(123);

        ProgrammingDto progData2 = new ProgrammingDto();
        progData2.setInternalDoc(123L);
        progData2.setInternalVs(123);

        ProgWithIncidencesDto prog1 = new ProgWithIncidencesDto();
        prog1.setProgData(progData1);

        ProgWithIncidencesDto prog2 = new ProgWithIncidencesDto();
        prog2.setProgData(progData2);

        orderedListMT.add(prog1);
        orderedListMT.add(prog2);

        Method method = EmailsServiceImpl.class.getDeclaredMethod("isSameProgData", List.class, int.class);
        method.setAccessible(true);

        boolean result = (boolean) method.invoke(emailsService, orderedListMT, 1);

        assertTrue(result);
    }

    @Test
    @SneakyThrows
    void testIsSameProgData_SameProgDataFalse() {
        List<ProgWithIncidencesDto> orderedListMT = new ArrayList<>();

        ProgrammingDto progData1 = new ProgrammingDto();
        progData1.setInternalDoc(124L);
        progData1.setInternalVs(124);

        ProgrammingDto progData2 = new ProgrammingDto();
        progData2.setInternalDoc(123L);
        progData2.setInternalVs(123);

        ProgWithIncidencesDto prog1 = new ProgWithIncidencesDto();
        prog1.setProgData(progData1);

        ProgWithIncidencesDto prog2 = new ProgWithIncidencesDto();
        prog2.setProgData(progData2);

        orderedListMT.add(prog1);
        orderedListMT.add(prog2);

        Method method = EmailsServiceImpl.class.getDeclaredMethod("isSameProgData", List.class, int.class);
        method.setAccessible(true);

        boolean result = (boolean) method.invoke(emailsService, orderedListMT, 1);

        assertFalse(result);
    }

    @Test
    @SneakyThrows
    void testProcessSeveralEmails() {
        // Arrange
        List<ProgWithIncidencesDto> orderedListMT = new ArrayList<>();
        ArrayList<ProgWithIncidencesDto> orderedListMTWithoutRepetitions = new ArrayList<>();

        ProgWithIncidencesDto prog1 = new ProgWithIncidencesDto();
        ProgrammingDto progData1 = new ProgrammingDto();
        progData1.setCreatorUser("user1");
        prog1.setProgData(progData1);

        ProgWithIncidencesDto prog2 = new ProgWithIncidencesDto();
        ProgrammingDto progData2 = new ProgrammingDto();
        progData2.setCreatorUser("user2");
        prog2.setProgData(progData2);

        ProgWithIncidencesDto prog3 = new ProgWithIncidencesDto();
        ProgrammingDto progData3 = new ProgrammingDto();
        progData3.setCreatorUser("user1"); // Duplicate user for testing
        prog3.setProgData(progData3);

        orderedListMT.add(prog1);
        orderedListMT.add(prog2);
        orderedListMT.add(prog3);

        lenient().when(utilsService.isARepeatedUserForProgrammings(eq(orderedListMTWithoutRepetitions), eq(prog1))).thenReturn(false);
        lenient().when(utilsService.isARepeatedUserForProgrammings(eq(orderedListMTWithoutRepetitions), eq(prog2))).thenReturn(false);
        lenient().when(utilsService.isARepeatedUserForProgrammings(eq(orderedListMTWithoutRepetitions), eq(prog3))).thenReturn(true);

        // Act
        Method method = EmailsServiceImpl.class.getDeclaredMethod("processSeveralEmails", List.class, ArrayList.class);
        method.setAccessible(true);
        method.invoke(emailsService, orderedListMT, orderedListMTWithoutRepetitions);

        // Assert
        assertEquals(2, orderedListMTWithoutRepetitions.size());
        assertTrue(orderedListMTWithoutRepetitions.contains(prog1));
        assertTrue(orderedListMTWithoutRepetitions.contains(prog2));
        assertFalse(orderedListMTWithoutRepetitions.contains(prog3)); // prog3 should be ignored
    }


}


