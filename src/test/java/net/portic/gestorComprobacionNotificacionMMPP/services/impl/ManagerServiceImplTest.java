package net.portic.gestorComprobacionNotificacionMMPP.services.impl;

import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.*;
import net.portic.gestorComprobacionNotificacionMMPP.domain.mappers.*;
import net.portic.gestorComprobacionNotificacionMMPP.domain.models.*;
import net.portic.gestorComprobacionNotificacionMMPP.repositories.*;
import net.portic.gestorComprobacionNotificacionMMPP.services.ManagerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ManagerServiceImplTest {

    @Mock
    private UserConfigRepository userConfigRepository;

    @Mock
    private AuditModificationMapper auditModificationMapper;

    @Mock
    private CompanyMapper companyMapper;

    @Mock
    private UserConfigMapper userConfigMapper;

    @InjectMocks
    private ManagerServiceImpl managerServiceImpl;

    @Mock
    private ProgrammingRepository programmingRepository;

    @Mock
    private ProgrammingMapper programmingMapper;

    @Mock
    private AdditionalEquipmentRepository additionalEquipmentRepository;

    @Mock
    private GlobalEquipmentMapper globalEquipmentMapper;

    @Mock
    private AdditionalEquipmentMapper additionalEquipmentMapper;

    @Mock
    private ManagerService managerService;

    @Mock
    private AppTrkMsgRepository appTrkMsgRepository;

    @Mock
    private UtilsServiceImpl utilsService;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private EmailsServiceImpl emailsService;

    @Mock
    private AuditMapper auditMapper;

    @Mock
    private ExpedientMaritimRepository expedientMaritimRepository;

    @Mock
    private ExpedientTerrestreRepository expedientTerrestreRepository;

    @Mock
    private AuditRepository auditRepository;

    @Mock
    private AppTrkMsgMapper appTrkMsgMapper;

    @Mock
    private MessageAttributesRepository messageAttributesRepository;

    private final String messageOkExpireds = "Comprobación de expiraciones de programaciones realizada con éxito.";
    private final String messageNoContentExpireds = "No se encontraron programaciones con fecha de expiración alcanzada.";
    private final String messageServerErrorExpireds = "Error interno del servidor al comprobar las expiraciones de programaciones.";
    private String messageServerErrorIncidences = "Error interno del servidor al comprobar los estados de los entrégueses.";


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void expiredProgramsFound() {
        LocalDateTime date = LocalDateTime.of(2024, 10, 3, 9, 44);

        EntryRequestDto requestData = new EntryRequestDto();
        requestData.setDateFilter("2024-08-06 00:00");
        requestData.setHasAudit(true);

        List<AuditModificationDto> auditModificationDtos = new ArrayList<>();
        AuditModificationDto auditModificationDto = new AuditModificationDto();
        auditModificationDto.setIdAudit(20241003094455L);
        auditModificationDto.setIdModification(1);
        auditModificationDto.setIdProgramming(50);
        auditModificationDto.setOldProgStatus("PR");
        auditModificationDto.setPresentProgStatus("EX");
        auditModificationDto.setOldRequestStatus("BU");
        auditModificationDto.setPresentRequestStatus("BU");
        auditModificationDto.setReceptorEmails("javicarceles7@gmail.com");
        auditModificationDtos.add(auditModificationDto);

        List<AuditModification> auditModifications = new ArrayList<>();
        AuditModification auditModification = new AuditModification();
        auditModification.setIdAudit(20241003094455L);
        auditModification.setIdModification(1);
        auditModification.setIdProgramming(50);
        auditModification.setOldProgStatus("PR");
        auditModification.setPresentProgStatus("EX");
        auditModification.setOldRequestStatus("BU");
        auditModification.setPresentRequestStatus("BU");
        auditModification.setReceptorEmails("javicarceles7@gmail.com");
        auditModifications.add(auditModification);

        AuditDto auditDto = new AuditDto();
        auditDto.setId(20241003094455L);
        auditDto.setService("EXPIRATIONS");
        auditDto.setDate(date);
        auditDto.setNumChecked(1);
        auditDto.setNumModified(1);
        auditDto.setNumSentEmails(1);
        auditDto.setAuditModificationDtoList(auditModificationDtos);

        Audit audit = new Audit();
        audit.setId(20241003094455L);
        audit.setService("EXPIRATIONS");
        audit.setDate(date);
        audit.setNumChecked(1);
        audit.setNumModified(1);
        audit.setNumSentEmails(1);
        audit.setAuditModificationList(auditModifications);

        AdditionalEquipment additionalEquipment = new AdditionalEquipment();
        additionalEquipment.setInternalDoc(734);
        additionalEquipment.setInternalVs(1);
        additionalEquipment.setEquipmentNumber(1);
        additionalEquipment.setPlate("XXXXX111002");
        additionalEquipment.setState("S");

        AdditionalEquipmentDto additionalEquipmentDto = new AdditionalEquipmentDto();
        additionalEquipmentDto.setInternalDoc(734);
        additionalEquipmentDto.setInternalVs(1);
        additionalEquipmentDto.setEquipmentNumber(1);
        additionalEquipmentDto.setPlate("XXXXX111002");
        additionalEquipmentDto.setState("S");

        GlobalEquipment globalEquipment = new GlobalEquipment();
        globalEquipment.setInternalDoc(734);
        globalEquipment.setInternalVs(1);
        globalEquipment.setPlate("XXXXX111002");
        globalEquipment.setCode("CN");
        globalEquipment.setContainerType("111");
        globalEquipment.setGroupage(1);
        globalEquipment.setPackages(11);
        globalEquipment.setGrossWeight(11);
        globalEquipment.setNetWeight(11);
        globalEquipment.setAdditionalEquipment(additionalEquipment);

        GlobalEquipmentDto globalEquipmentDto = new GlobalEquipmentDto();
        globalEquipmentDto.setInternalDoc(734);
        globalEquipmentDto.setInternalVs(1);
        globalEquipmentDto.setPlate("XXXXX111002");
        globalEquipmentDto.setCode("CN");
        globalEquipmentDto.setContainerType("111");
        globalEquipmentDto.setGroupage(1);
        globalEquipmentDto.setPackages(11);
        globalEquipmentDto.setGrossWeight(11);
        globalEquipmentDto.setNetWeight(11);
        globalEquipmentDto.setAdditionalEquipmentDto(additionalEquipmentDto);

        List<GlobalEquipment> globalEquipmentList = new ArrayList<>();
        globalEquipmentList.add(globalEquipment);
        List<GlobalEquipmentDto> globalEquipmentDtoList = new ArrayList<>();
        globalEquipmentDtoList.add(globalEquipmentDto);

        Request request = new Request();
        request.setInternalDoc(734);
        request.setInternalVs(1);
        request.setIdExpDeparture(47788L);
        request.setStatus("BU");
        request.setDate(new Date(1296812351000L));
        request.setDocumentNumber("01141100308");
        request.setNumRefTrk(883793L);
        request.setNumMsgTrk(1L);

        RequestDto requestDto = new RequestDto();
        requestDto.setInternalDoc(734);
        requestDto.setInternalVs(1);
        requestDto.setIdExpDeparture(47788L);
        requestDto.setStatus("BU");
        requestDto.setDate(new Date(1296812351000L));
        requestDto.setDocumentNumber("01141100308");
        requestDto.setNumRefTrk(883793L);
        requestDto.setNumMsgTrk(1L);

        ExpedientMaritim expedientMaritim = new ExpedientMaritim();
        expedientMaritim.setIdExp(47788);
        expedientMaritim.setPortCall("18281");
        expedientMaritim.setShipName("VILLE DE DUBAI");
        expedientMaritim.setShipOmi("9153678");

        ExpedientMaritimDto expedientMaritimDto = new ExpedientMaritimDto();
        expedientMaritimDto.setIdExp(47788);
        expedientMaritimDto.setPortCall("18281");
        expedientMaritimDto.setShipName("VILLE DE DUBAI");
        expedientMaritimDto.setShipOmi("9153678");

        ExpedientTerrestre expedientTerrestre = new ExpedientTerrestre();
        expedientTerrestre.setIdExp(131454L);
        expedientTerrestre.setVersioExp(1);
        expedientTerrestre.setTipusExp("QACONSI");
        expedientTerrestre.setEstatExp("9");
        expedientTerrestre.setDataInici(LocalDateTime.of(2024, 10, 4, 7, 42, 43));
        expedientTerrestre.setIdExpOrig(1337046L);
        expedientTerrestre.setUsuari("ESQ3333");
        expedientTerrestre.setCopia("N");

        ExpedientTerrestreDto expedientTerrestreDto = new ExpedientTerrestreDto();
        expedientTerrestreDto.setIdExp(131454L);
        expedientTerrestreDto.setVersioExp(1);
        expedientTerrestreDto.setTipusExp("QACONSI");
        expedientTerrestreDto.setEstatExp("9");
        expedientTerrestreDto.setDataInici(LocalDateTime.of(2024, 10, 4, 7, 42, 43));
        expedientTerrestreDto.setIdExpOrig(1337046L);
        expedientTerrestreDto.setUsuari("ESQ3333");
        expedientTerrestreDto.setCopia("N");

        Company company = new Company();
        company.setNifCompany("A08739112");
        company.setIdCompany(37);

        UserConfig userConfig = new UserConfig();
        userConfig.setUser("PINOJ");
        userConfig.setEmail("javicarceles7@gmail.com");
        userConfig.setCompany(company);

        UserConfigDto userConfigDto = new UserConfigDto();
        userConfigDto.setUser("PINOJ");
        userConfigDto.setEmail("javicarceles7@gmail.com");

        Programming programming = new Programming();
        programming.setIdProgramming(50);
        programming.setStatus("PR");
        programming.setInternalDoc(734);
        programming.setInternalVs(1);
        programming.setCreationDate(LocalDateTime.parse("2024-08-19T12:30"));
        programming.setExpirationDate(LocalDateTime.parse("2024-09-01T00:00"));
        programming.setCreatorUser("PINOJ");
        programming.setNotifyIncidences("S");
        programming.setExpType("A08739112");
        programming.setIdExp(47788);
        programming.setNifCompanyUser("A08739112");
        programming.setRequest(request);
        programming.setExpedientMaritim(expedientMaritim);
        programming.setUserConfig(userConfig);
        programming.setGlobalEquipmentList(globalEquipmentList);

        ProgrammingDto progDto = new ProgrammingDto();
        progDto.setIdProgramming(50);
        progDto.setStatus("PR");
        progDto.setInternalDoc(734);
        progDto.setInternalVs(1);
        progDto.setCreationDate(LocalDateTime.parse("2024-08-19T12:30:00"));
        progDto.setExpirationDate(LocalDateTime.parse("2024-09-01T00:00:00"));
        progDto.setNotifyIncidences("S");
        progDto.setCreatorUser("PINOJ");
        progDto.setExpType("M");
        progDto.setIdExp(47788);
        progDto.setNifCompanyUser("A08739112");
        progDto.setRequest(requestDto);
        progDto.setUserConfig(userConfigDto);
        progDto.setExpedientMaritim(expedientMaritimDto);
        progDto.setGlobalEquipmentDtosList(globalEquipmentDtoList);

        List<SentEmailDto> sentEmailDtos = new ArrayList<>();
        SentEmailDto sentEmailDto = new SentEmailDto();
        sentEmailDto.setCustomerName("PINOJ");
        sentEmailDto.setCustomerNif("AO8739112");
        sentEmailDto.setEmail("javicarceles@gmail.com");
        sentEmailDtos.add(sentEmailDto);

        List<Programming> progList = new ArrayList<>();
        progList.add(programming);
        List<ProgrammingDto> progDtoList = new ArrayList<>();
        progDtoList.add(progDto);

        ExpiredProgDto expiredProgDto = new ExpiredProgDto();
        expiredProgDto.setId(50);
        expiredProgDto.setCustomerName("PINOJ");
        expiredProgDto.setProgData(progDto);
        expiredProgDto.setSentEmails(sentEmailDtos);

        List<EmailToSendDto> emailToSendDtos = new ArrayList<>();
        EmailToSendDto email = new EmailToSendDto();
        email.setCustomerData(sentEmailDto);
        email.setTitle("Comprobación de las programaciones expiradas");
        email.setMatter("Expiración de las programaciones de Mercancías Peligrosas");
        email.setContent("La programación correspondiente a la solicitud 01141100308 ha expirado.\n");
        emailToSendDtos.add(email);

        when(userConfigRepository.findByUserAndIdCompany(anyString(), anyLong())).thenReturn(userConfig);
        when(utilsService.getEmailsForOneUser(userConfig)).thenReturn(sentEmailDtos);
        when(userConfigMapper.fromEntityToDto(userConfig)).thenReturn(userConfigDto);
        when(programmingRepository.getListExpiredProgrammings(any(LocalDateTime.class))).thenReturn(progList);
        when(companyRepository.findByNifCompany(anyString())).thenReturn(company);
        when(additionalEquipmentRepository.findByInternalDocAndInternalVsAndPlate(anyLong(), anyInt(), anyString())).thenReturn(additionalEquipment);
        when(programmingMapper.fromDtoToEntity(progDto)).thenReturn(programming);
        when(programmingMapper.fromEntitiesToDtos(progList)).thenReturn(progDtoList);
        when(globalEquipmentMapper.fromEntitiesToDtos(globalEquipmentList)).thenReturn(globalEquipmentDtoList);
        when(additionalEquipmentMapper.fromEntityToDto(additionalEquipment)).thenReturn(additionalEquipmentDto);
        when(emailsService.createEmail(eq(null), anyList())).thenReturn(emailToSendDtos);
        when(auditMapper.fromDtoToEntity(any())).thenReturn(audit);
        when(auditModificationMapper.fromDtosToEntities(anyList())).thenReturn(auditModifications);
        lenient().when(expedientMaritimRepository.findByIdExp(anyLong())).thenReturn(expedientMaritim);
        lenient().when(expedientTerrestreRepository.findByIdExp(anyLong())).thenReturn(expedientTerrestre);

        ExpiredProgResponseDto response = managerServiceImpl.checkExpiredProgrammings(requestData);

        assertEquals(HttpStatus.OK, response.getCode());
        assertEquals(messageOkExpireds, response.getMessage());
        assertNotNull(response.getData());
        assertEquals(1, response.getData().size());
    }

    @Test
    void noExpiredPrograms() {
        EntryRequestDto requestData = new EntryRequestDto();
        requestData.setDateFilter("2024-12-12 12:00");
        requestData.setHasAudit(false);

        when(programmingRepository.getListExpiredProgrammings(any(LocalDateTime.class))).thenReturn(new ArrayList<>());

        ExpiredProgResponseDto response = managerServiceImpl.checkExpiredProgrammings(requestData);

        assertEquals(HttpStatus.NO_CONTENT, response.getCode());
        assertEquals(messageNoContentExpireds, response.getMessage());
        assertNull(response.getData());
    }

    @Test
    void invalidDateExp() {
        EntryRequestDto requestData = new EntryRequestDto();
        requestData.setDateFilter("invalid-date-format");

        ExpiredProgResponseDto response = managerServiceImpl.checkExpiredProgrammings(requestData);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getCode());
        assertEquals(messageServerErrorExpireds, response.getMessage());
        assertNull(response.getData());
    }

    @Test
    void unexpectedErrorExp() {
        EntryRequestDto requestData = new EntryRequestDto();
        requestData.setDateFilter("2023-10-01 12:00");

        when(programmingRepository.getListExpiredProgrammings(any(LocalDateTime.class)))
                .thenThrow(new RuntimeException("Unexpected error"));

        ExpiredProgResponseDto response = managerServiceImpl.checkExpiredProgrammings(requestData);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getCode());
        assertEquals(messageServerErrorExpireds, response.getMessage());
        assertNull(response.getData());
    }

    @Test
    void nullPointerHandlingExp() {
        EntryRequestDto requestData = new EntryRequestDto();
        requestData.setDateFilter("2024-08-06 00:00");
        requestData.setHasAudit(true);

        when(programmingRepository.getListExpiredProgrammings(any(LocalDateTime.class))).thenReturn(null);

        ExpiredProgResponseDto response = managerServiceImpl.checkExpiredProgrammings(requestData);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getCode());
        assertEquals(messageServerErrorExpireds, response.getMessage());
        assertNull(response.getData());
    }

    @Test
    void invalidDate() {
        EntryRequestDto requestData = new EntryRequestDto();
        requestData.setDateFilter("invalid-date-format");

        ProgWithIncidencesResponseDto response = managerServiceImpl.checkIncidencesProgrammings(requestData);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getCode());
        assertEquals(messageServerErrorIncidences, response.getMessage());
        assertNull(response.getData());
    }

    @Test
    void unexpectedError() {
        EntryRequestDto requestData = new EntryRequestDto();
        requestData.setDateFilter("2023-10-01 12:00");

        when(programmingRepository.getListProgrammings(any(LocalDateTime.class)))
                .thenThrow(new RuntimeException("Unexpected error"));

        ProgWithIncidencesResponseDto response = managerServiceImpl.checkIncidencesProgrammings(requestData);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getCode());
        assertEquals(messageServerErrorIncidences, response.getMessage());
        assertNull(response.getData());
    }

    @Test
    void nullPointerHandling() {
        EntryRequestDto requestData = new EntryRequestDto();
        requestData.setDateFilter("2024-08-06 00:00");
        requestData.setHasAudit(true);

        when(programmingRepository.getListProgrammings(any(LocalDateTime.class))).thenReturn(null);

        ProgWithIncidencesResponseDto response = managerServiceImpl.checkIncidencesProgrammings(requestData);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getCode());
        assertEquals(messageServerErrorIncidences, response.getMessage());
        assertNull(response.getData());
    }

    @Test
    void emptyProgWithExpirationsList() {

        EntryRequestDto requestData = new EntryRequestDto();
        requestData.setDateFilter("2024-08-06 00:00");
        requestData.setHasAudit(true);

        List<Programming> progList = new ArrayList<>();
        when(programmingRepository.getListExpiredProgrammings(any(LocalDateTime.class))).thenReturn(progList);

        ExpiredProgResponseDto response = managerServiceImpl.checkExpiredProgrammings(requestData);

        assertEquals(HttpStatus.NO_CONTENT, response.getCode());
        assertEquals(messageNoContentExpireds, response.getMessage());
        assertNull(response.getData());
    }

    @Test
    void noIncidencesFound() {
        EntryRequestDto requestData = new EntryRequestDto();
        requestData.setDateFilter("2024-10-01 00:00");
        requestData.setHasAudit(false);

        when(programmingRepository.getListProgrammings(any(LocalDateTime.class))).thenReturn(new ArrayList<>());

        ProgWithIncidencesResponseDto response = managerServiceImpl.checkIncidencesProgrammings(requestData);

        assertEquals(HttpStatus.NO_CONTENT, response.getCode());
        assertEquals("No se encontraron actualizaciones sobre los entréguese asociados a las programaciones.", response.getMessage());
        assertNull(response.getData());
    }

    @Test
    void testCheckIncidencesProgramming_NoNullValues() {
        // Configuración del EntryRequestDto sin valores nulos
        EntryRequestDto requestData = new EntryRequestDto();
        requestData.setDateFilter("2024-10-03 09:44");
        requestData.setHasAudit(true);

        // Configuramos Company sin valores nulos
        Company company = new Company();
        company.setIdCompany(37L);
        company.setNifCompany("A08739112");

        // Configuramos UserConfig sin valores nulos
        UserConfig userConfig = new UserConfig();
        userConfig.setUser("PINOJ");
        userConfig.setIdCompany(37L);
        userConfig.setEmail("javicarceles7@gmail.com");
        userConfig.setCompany(company);

        // Configuramos AdditionalEquipment sin valores nulos
        AdditionalEquipment additionalEquipment = new AdditionalEquipment();
        additionalEquipment.setIdEsmtMsg(1L);
        additionalEquipment.setInternalDoc(734);
        additionalEquipment.setInternalVs(1);
        additionalEquipment.setEquipmentNumber(1);
        additionalEquipment.setPlate("XXXXX111002");
        additionalEquipment.setState("OKCA");
        additionalEquipment.setNumEntreguese("123456");
        additionalEquipment.setExpirationDate(LocalDateTime.now().plusDays(1));
        additionalEquipment.setNumCodeco("COD123");

        // Configuramos AppTrkMsg sin valores nulos
        AppTrkMsg appTrkMsg = new AppTrkMsg();
        appTrkMsg.setIdEsmtMsg(1L);
        appTrkMsg.setNumRefFile(123456L);
        appTrkMsg.setProcessStartDate(LocalDateTime.now());
        appTrkMsg.setProcessStatus("OKCA");

        // Configuramos GlobalEquipment sin valores nulos
        GlobalEquipment globalEquipment = new GlobalEquipment();
        globalEquipment.setInternalDoc(734);
        globalEquipment.setInternalVs(1);
        globalEquipment.setPlate("XXXXX111002");
        globalEquipment.setCode("CN");
        globalEquipment.setContainerType("111");
        globalEquipment.setGroupage(1);
        globalEquipment.setPackages(11);
        globalEquipment.setGrossWeight(11);
        globalEquipment.setNetWeight(11);
        globalEquipment.setAdditionalEquipment(additionalEquipment);

        // Configuramos Request sin valores nulos
        Request request = new Request();
        request.setInternalDoc(734);
        request.setInternalVs(1);
        request.setStatus("BU");
        request.setNumRefTrk(883793L);
        request.setNumMsgTrk(1L);

        // Configuramos Programming sin valores nulos
        Programming programming = new Programming();
        programming.setIdProgramming(50);
        programming.setStatus("PR");
        programming.setInternalDoc(734);
        programming.setInternalVs(1);
        programming.setCreationDate(LocalDateTime.parse("2024-08-19T12:30:00"));
        programming.setExpirationDate(LocalDateTime.parse("2024-09-01T00:00:00"));
        programming.setCreatorUser("PINOJ");
        programming.setNotifyIncidences("S");
        programming.setExpType("M");
        programming.setIdExp(47788);
        programming.setNifCompanyUser("A08739112");
        programming.setRequest(request);
        programming.setUserConfig(userConfig);
        programming.setGlobalEquipmentList(Collections.singletonList(globalEquipment));

        // Configuramos los DTOs
        RequestDto requestDto = new RequestDto();
        requestDto.setInternalDoc(734);
        requestDto.setInternalVs(1);
        requestDto.setStatus("BU");
        requestDto.setNumRefTrk(883793L);
        requestDto.setNumMsgTrk(1L);

        AdditionalEquipmentDto additionalEquipmentDto = new AdditionalEquipmentDto();
        additionalEquipmentDto.setIdEsmtMsg(1L);
        additionalEquipmentDto.setInternalDoc(734);
        additionalEquipmentDto.setInternalVs(1);
        additionalEquipmentDto.setEquipmentNumber(1);
        additionalEquipmentDto.setPlate("XXXXX111002");
        additionalEquipmentDto.setState("OKCA");
        additionalEquipmentDto.setNumEntreguese("123456");
        additionalEquipmentDto.setExpirationDate(LocalDateTime.now().plusDays(1));
        additionalEquipmentDto.setNumCodeco("COD123");

        GlobalEquipmentDto globalEquipmentDto = new GlobalEquipmentDto();
        globalEquipmentDto.setInternalDoc(734);
        globalEquipmentDto.setInternalVs(1);
        globalEquipmentDto.setPlate("XXXXX111002");
        globalEquipmentDto.setCode("CN");
        globalEquipmentDto.setContainerType("111");
        globalEquipmentDto.setGroupage(1);
        globalEquipmentDto.setPackages(11);
        globalEquipmentDto.setGrossWeight(11);
        globalEquipmentDto.setNetWeight(11);
        globalEquipmentDto.setAdditionalEquipmentDto(additionalEquipmentDto);

        ProgrammingDto programmingDto = new ProgrammingDto();
        programmingDto.setIdProgramming(50);
        programmingDto.setStatus("PR");
        programmingDto.setInternalDoc(734);
        programmingDto.setInternalVs(1);
        programmingDto.setCreationDate(LocalDateTime.parse("2024-08-19T12:30:00"));
        programmingDto.setExpirationDate(LocalDateTime.parse("2024-09-01T00:00:00"));
        programmingDto.setNotifyIncidences("S");
        programmingDto.setCreatorUser("PINOJ");
        programmingDto.setExpType("M");
        programmingDto.setIdExp(47788);
        programmingDto.setNifCompanyUser("A08739112");
        programmingDto.setRequest(requestDto);
        UserConfigDto userConfigDto = new UserConfigDto();
        userConfigDto.setUser("PINOJ");
        userConfigDto.setEmail("javicarceles7@gmail.com");
        programmingDto.setUserConfig(userConfigDto);
        programmingDto.setGlobalEquipmentDtosList(Collections.singletonList(globalEquipmentDto));

        // Mockeamos el Audit
        Audit audit = new Audit();
        audit.setId(20241003094455L);
        audit.setService("INCIDENCES");
        audit.setDate(LocalDateTime.now());
        audit.setNumChecked(1);
        audit.setNumModified(1);
        audit.setNumSentEmails(1);
        audit.setAuditModificationList(Collections.emptyList());

        // Mockeamos el SentEmailDto
        SentEmailDto sentEmailDto = new SentEmailDto("PINOJ", "A08739112", "javicarceles7@gmail.com");
        List<SentEmailDto> sentEmailDtos = Collections.singletonList(sentEmailDto);

// Instancia de AppTrkMsgDto
        AppTrkMsgDto appTrkMsgDto = new AppTrkMsgDto();
        appTrkMsgDto.setNumRefFile(123456L);
        appTrkMsgDto.setNumMsg(1L);
        appTrkMsgDto.setProcessStatus("OKCA");
        appTrkMsgDto.setProcessStartDate(LocalDateTime.now());
        appTrkMsgDto.setIdEsmtMsg(1L);

// Instancia de AuditModification
        AuditModification auditModification = new AuditModification();
        auditModification.setIdAudit(audit.getId());
        auditModification.setIdProgramming(50); // ID de la programación correspondiente
        auditModification.setOldProgStatus("PR"); // Estado anterior
        auditModification.setOldRequestStatus("BU"); // Estado anterior de la solicitud
        auditModification.setPresentProgStatus("AB"); // Estado actual
        auditModification.setPresentRequestStatus("BU"); // Estado actual de la solicitud

// Lista de modificaciones de auditoría
        List<AuditModification> auditModifications = new ArrayList<>();
        auditModifications.add(auditModification);

        List<EmailToSendDto> emailToSendDtos = new ArrayList<>();
        EmailToSendDto email = new EmailToSendDto();
        email.setCustomerData(sentEmailDto);
        email.setTitle("Comprobación de las programaciones expiradas");
        email.setMatter("Expiración de las programaciones de Mercancías Peligrosas");
        email.setContent("La programación correspondiente a la solicitud 01141100308 ha expirado.\n");
        emailToSendDtos.add(email);

        // Configuramos todos los mocks
        when(userConfigRepository.findByUserAndIdCompany(anyString(), anyLong())).thenReturn(userConfig);
        when(utilsService.getEmailsForOneUser(userConfig)).thenReturn(sentEmailDtos);
        when(userConfigMapper.fromEntityToDto(userConfig)).thenReturn(userConfigDto);
        when(programmingRepository.getListProgrammings(any(LocalDateTime.class))).thenReturn(Collections.singletonList(programming));
        when(companyRepository.findByNifCompany(anyString())).thenReturn(company);
        when(additionalEquipmentRepository.findByInternalDocAndInternalVsAndPlate(anyLong(), anyInt(), anyString())).thenReturn(additionalEquipment);
        when(programmingMapper.fromDtoToEntity(programmingDto)).thenReturn(programming);
        when(programmingMapper.fromEntitiesToDtos(Collections.singletonList(programming))).thenReturn(Collections.singletonList(programmingDto));
        when(globalEquipmentMapper.fromEntitiesToDtos(programming.getGlobalEquipmentList())).thenReturn(Collections.singletonList(globalEquipmentDto));
        when(additionalEquipmentMapper.fromEntityToDto(additionalEquipment)).thenReturn(additionalEquipmentDto);
        when(additionalEquipmentMapper.fromDtoToEntity(additionalEquipmentDto)).thenReturn(additionalEquipment);
        when(appTrkMsgRepository.findByIdEsmtMsg(anyLong())).thenReturn(appTrkMsg);
        when(appTrkMsgMapper.fromEntityToDto(appTrkMsg)).thenReturn(appTrkMsgDto);
        when(auditMapper.fromDtoToEntity(any(AuditDto.class))).thenReturn(audit);
        when(auditModificationMapper.fromDtosToEntities(anyList())).thenReturn(auditModifications);
        when(emailsService.createEmail(anyList(), eq(null))).thenReturn(emailToSendDtos);
        when(utilsService.generateAuditId(any(LocalDateTime.class))).thenReturn(20241003094455L);
        when(messageAttributesRepository.getMaxMsgIdByNumEntreguese(anyString())).thenReturn(20241003094455L);

        // Ejecutamos el método y realizamos las comprobaciones necesarias
        ProgWithIncidencesResponseDto response = managerServiceImpl.checkIncidencesProgrammings(requestData);

        // Validaciones finales para asegurarnos de que el test pasa y los valores están configurados correctamente
        assertNotNull(response.getData());
        assertEquals(HttpStatus.OK, response.getCode());
        assertEquals("Comprobación de estados de entrégueses realizada con éxito.", response.getMessage());
    }

}
