package net.portic.gestorComprobacionNotificacionMMPP.services.impl;

import com.sun.mail.util.MailConnectException;
import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.*;
import net.portic.gestorComprobacionNotificacionMMPP.domain.mappers.*;
import net.portic.gestorComprobacionNotificacionMMPP.domain.models.*;
import net.portic.gestorComprobacionNotificacionMMPP.repositories.*;
import net.portic.gestorComprobacionNotificacionMMPP.services.ManagerService;
import org.codehaus.plexus.util.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class ManagerServiceImpl implements ManagerService {

    Logger log = LoggerFactory.getLogger(ManagerServiceImpl.class);
    @Autowired
    private EmailsServiceImpl emailsService;

    @Autowired
    private UtilsServiceImpl utilsService;

    @Autowired
    private ProgrammingRepository programmingRepository;

    @Autowired
    private AuditRepository auditRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserConfigRepository userConfigRepository;

    @Autowired
    private AdditionalEquipmentRepository additionalEquipmentRepository;

    @Autowired
    private ExpedientMaritimRepository expedientMaritimRepository;

    @Autowired
    private ExpedientTerrestreRepository expedientTerrestreRepository;

    @Autowired
    private AppTrkMsgRepository appTrkMsgRepository;

    @Autowired
    private MessageAttributesRepository messageAttributesRepository;

    @Autowired
    private ProgrammingMapper programmingMapper;

    @Autowired
    private GlobalEquipmentMapper globalEquipmentMapper;

    @Autowired
    private AuditMapper auditMapper;

    @Autowired
    private AuditModificationMapper auditModificationMapper;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private UserConfigMapper userConfigMapper;

    @Autowired
    private AdditionalEquipmentMapper additionalEquipmentMapper;

    @Autowired
    private AppTrkMsgMapper appTrkMsgMapper;


    private String dummy = "XXXXX111";

    private String incidences = "INCIDENCES";
    private String expirations = "EXPIRATIONS";

    private String messageOkIncidences = "Comprobación de estados de entrégueses realizada con éxito.";
    private String messageOkExpireds = "Comprobación de expiraciones de programaciones realizada con éxito.";
    private String messageServerErrorIncidences = "Error interno del servidor al comprobar los estados de los entrégueses.";
    private String messageServerErrorExpireds = "Error interno del servidor al comprobar las expiraciones de programaciones.";
    private String messageNoContentIncidences = "No se encontraron actualizaciones sobre los entréguese asociados a las programaciones.";
    private String messageNoContentExpireds = "No se encontraron programaciones con fecha de expiración alcanzada.";


    @Override
    public ProgWithIncidencesResponseDto checkIncidencesProgrammings(EntryRequestDto requestData) {
        log.info("ManagerServiceImpl.checkIncidencesProgrammings Inicio: parámetros de enrada: -hasAudit: "
                + requestData.isHasAudit() + " -dateFilter: " + requestData.getDateFilter());
        ProgWithIncidencesResponseDto response = new ProgWithIncidencesResponseDto();
        AuditDto auditDto = new AuditDto();
        List<AuditModificationDto> auditModificationDtoList = new ArrayList<>();
        try {
            //Transformamos el String en LocalDateTime
            DateTimeFormatter genericFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateFilter = LocalDateTime.parse(requestData.getDateFilter(), genericFormatter);

            //Buscamos las programaciones filtradas por fecha(>=dateFilter), estado('PR') y tipo de expediente('M' o 'T')
            log.info("ManagerServiceImpl.checkIncidencesProgrammings: procedemos a obtener la lista de programaciones:");
            List<Programming> progList = programmingRepository.getListProgrammings(dateFilter);
            log.info("ManagerServiceImpl.checkIncidencesProgrammings: las programaciones de la lista (longitud: " + progList.size() +
                    ") encontradas tienen los siguientes documentos internos y versiones internas: ");
            progList.stream().forEach(prog ->
                    log.info("ManagerServiceImpl.checkIncidencesProgrammings: " + prog.getInternalDoc() + "-" + prog.getInternalVs())
            );

            //Se ontienen los equipos de todas las programaciones de la lista
            getEquipmentsWithAppTrkMsg(progList);

            //Pasamos la lista de Entidades a Dtos
            List<ProgrammingDto> progDtoList = programmingMapper.fromEntitiesToDtos(progList);

            //Se pasan los equipos de la entidad al dto
            parseEquipmentToDtoIncidences(progList, progDtoList);

            //Damos valor al id, servicio, fecha y número de programaciones comprobadas de la auditoria general.
            initAuditDto(auditDto, progDtoList, incidences);

            //Caso en el que no ha encontrado ninguna programación
            if (progDtoList.isEmpty()) {
                noProgrammingFoundWithIncidences(response, auditDto, requestData);
                return response;
            }

            List<ProgWithIncidencesDto> progWithIncidencesList = new ArrayList<>();
            //Bucle para encontrar los tres tipos de innicdencias y modificar sus datos en cada caso
            log.info("ManagerServiceImpl.checkIncidencesProgrammings: se identifica si tiene y qué tipo de incidencia tiene cada entréguese.");
            for (int i = 0; i < progDtoList.size(); i++) {
                //Bucle para los equipos de cada programación
                for (int m = 0; m < progDtoList.get(i).getGlobalEquipmentDtosList().size(); m++) {
                    //Si no tiene num entréguese un equipo, significa que éste tenía matrícula inicialmente, no hay nada que hacer/revisar
                    if (StringUtils.isEmpty(progDtoList.get(i).getGlobalEquipmentDtosList().get(m).getAdditionalEquipmentDto().getNumEntreguese())) {
                        continue;
                    }
                    //Inicializamos con algunos valores el AuditModificationDto
                    AuditModificationDto auditModificationDto = new AuditModificationDto();
                    initAuditModificationDto(i, auditModificationDto, auditDto, progDtoList.get(i));

                    ProgWithIncidencesDto progWithIncidences = new ProgWithIncidencesDto();
                    initIncidencesProgDto(progWithIncidences, progDtoList.get(i).getGlobalEquipmentDtosList().get(m));

                    //Añadimos el userConfig en la Programming con incidencia que deben ser notificadas
                    initUserConfigForIncidences(progDtoList.get(i), progList.get(i), auditModificationDto, progWithIncidences);

                    //Seteamos el tipo de incidencia
                    obtainAndSetIncidenceType(progWithIncidences, progDtoList.get(i).getGlobalEquipmentDtosList().get(m));

                    if (hasIncidence(progWithIncidences)) {
                        log.info("ManagerServiceImpl.checkIncidencesProgrammings: la programación con documento interno " + progList.get(i).getInternalDoc() +
                                " y versión interna " + progList.get(i).getInternalVs() + " tiene una incidencia de tipo: '" + progWithIncidences.getStatus() + "'.");
                        //Cambiamos el estado de la programación a "Aboratada"
                        progDtoList.get(i).setStatus("AB");
                        //Cambiamos el estado de la solicitud a "En construcción"
                        progDtoList.get(i).getRequest().setStatus("BU");

                        //Auditorías
                        modifyAuditDtos(auditDto, auditModificationDto, progDtoList.get(i), auditModificationDtoList.size());
                        auditModificationDtoList.add(auditModificationDto);

                        //Convertimos el dto modificado a entidad
                        Programming progToSave = programmingMapper.fromDtoToEntity(progDtoList.get(i));
                        AdditionalEquipment additionalEquipmentToSave = additionalEquipmentMapper.fromDtoToEntity(progDtoList.get(i).getGlobalEquipmentDtosList().get(m).getAdditionalEquipmentDto());

                        //Guardamos la entidad en la bbdd
                        log.info("ManagerServiceImpl.checkIncidencesProgrammings: se procede a guardar en la base de datos  los cambios realizados en los " +
                                "estados de la programación, la solicitud y del entréguese en la tabla de los equipos de las programaciones. ");
                        programmingRepository.save(progToSave);
                        additionalEquipmentRepository.save(additionalEquipmentToSave);
                        progWithIncidences.setProgData(progDtoList.get(i));

                        progWithIncidencesList.add(progWithIncidences);
                    }
                }
            }

            try {
                log.info("ManagerServiceImpl.checkIncidencesProgrammings: se procede a generar y enviar los mails.");
                List<EmailToSendDto> emailToSendDtoList = emailsService.createEmail(progWithIncidencesList, null);
                auditDto.setNumSentEmails(emailToSendDtoList.size());
                log.info("ManagerServiceImpl.checkIncidencesProgrammings: se enviaron " + auditDto.getNumSentEmails() + " mails.");
            } catch (Exception e) {
                log.error("ManagerServiceImpl.checkIncidencesProgrammings: error enviando los mails.");
                log.error("ManagerServiceImpl.checkIncidencesProgrammings:" + Arrays.toString(e.getStackTrace()));
            }

            //Seteamos los valores a la respuesta
            setResponseDataIncidences(response, progWithIncidencesList);
            response.setAudit(null);
            if (requestData.isHasAudit()) {
                //Se guarda la auditoría en la bbdd
                saveAudit(auditDto, auditModificationDtoList);
                response.setAudit(auditDto);
            }

            response.setData(progWithIncidencesList);
            return response;

        } catch (DateTimeParseException parseTime) {
            log.error("ManagerServiceImpl.checkIncidencesProgrammings.genericFormatter " + parseTime.getMessage());
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(messageServerErrorIncidences);
            response.setData(null);
            response.setAudit(auditDto);
            return response;
        } catch (NullPointerException | IndexOutOfBoundsException NPexception) {
            log.error("ManagerServiceImpl.checkIncidencesProgrammings " + NPexception.getMessage());
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(messageServerErrorIncidences);
            response.setData(null);
            response.setAudit(auditDto);
            return response;
        } catch (Exception e) {
            log.error("ManagerServiceImpl.checkIncidencesProgrammings: " + e.getMessage());
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(messageServerErrorIncidences);
            response.setData(null);
            response.setAudit(auditDto);
            return response;
        }
    }

    @Override
    public ExpiredProgResponseDto checkExpiredProgrammings(EntryRequestDto requestData) {
        log.info("ManagerServiceImpl.checkExpiredProgrammings Inicio: parámetros de enrada: -hasAudit: "
                + requestData.isHasAudit() + " -dateFilter: " + requestData.getDateFilter());
        ExpiredProgResponseDto response = new ExpiredProgResponseDto();
        AuditDto auditDto = new AuditDto();
        List<AuditModificationDto> auditModificationDtoList = new ArrayList<>();
        try {
            //Transformamos el String en LocalDateTime
            DateTimeFormatter genericFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateFilter = LocalDateTime.parse(requestData.getDateFilter(), genericFormatter);

            //Buscamos las programaciones filtradas por fecha(>=dateFilter), estado('PR'), tipo de expediente('M' o 'T') y con fecha de expiración anterior a hoy
            log.info("ManagerServiceImpl.checkExpiredProgrammings: procedemos a obtener la lista de programaciones:");
            List<Programming> progList = programmingRepository.getListExpiredProgrammings(dateFilter);
            log.info("ManagerServiceImpl.checkExpiredProgrammings: las programaciones de la lista (longitud: " + progList.size() +
                    ") encontradas tienen los siguientes documentos internos y versiones internas: ");
            progList.stream().forEach(prog ->
                    log.info("ManagerServiceImpl.checkExpiredProgrammings: " + prog.getInternalDoc() + "-" + prog.getInternalVs())
            );

            //Se ontienen los equipos de todas las programaciones de la lista
            getEquipmentsWithoutAppTrkMsg(progList);

            //Pasamos la lista de Entidades a Dtos
            List<ProgrammingDto> progDtoList = programmingMapper.fromEntitiesToDtos(progList);
            parseEquipmentToDtoExpireds(progList, progDtoList);

            //Damos valor al id, servicio, fecha y número de programaciones comprobadas de la auditoria general.
            initAuditDto(auditDto, progDtoList, expirations);

            //Caso en el que no ha encontrado ninguna programación
            if (progDtoList.size() == 0) {
                noProgrammingFoundExpirated(response, auditDto, requestData);
                return response;
            }

            List<ExpiredProgDto> progWithExpirationsList = new ArrayList<>();
            for (int i = 0; i < progDtoList.size(); i++) {
                AuditModificationDto auditModificationDto = new AuditModificationDto();
                initAuditModificationDto(i, auditModificationDto, auditDto, progDtoList.get(i));

                ExpiredProgDto expiredProgDto = new ExpiredProgDto();
                initExpiredProgDto(expiredProgDto, progDtoList.get(i));

                //Añadimos el userConfig en la Programming con incidencia que deben ser notificadas
                initUserConfigForExpirations(progDtoList.get(i), progList.get(i), auditModificationDto, expiredProgDto);

                log.info("ManagerServiceImpl.checkExpiredProgrammings: se cambian los estados de la programación y de la solicitud.");
                //Cambiamos el estado de la programación a "Expirada"
                progDtoList.get(i).setStatus("EX");
                //Cambiamos el estado de la solicitud a "En preparación"
                progDtoList.get(i).getRequest().setStatus("BU");

                //En casi de que haya llegado un codeco, seteamos la matrícula a una Dummy
                if (progDtoList.get(i).getGlobalEquipmentDtosList() != null) {
                    findAndCreateLastDummy(progDtoList.get(i));
                }

                //Se modifican los valores de las auditorías
                modifyAuditDtos(auditDto, auditModificationDto, progDtoList.get(i), auditModificationDtoList.size());
                auditModificationDtoList.add(auditModificationDto);

                //Convertimos el dto modificado a entidad
                Programming progToSave = programmingMapper.fromDtoToEntity(progDtoList.get(i));
                //Guardamos la entidad en la bbdd
                log.info("ManagerServiceImpl.checkExpiredProgrammings: se procede a guardar en la base de datos  los cambios realizados en los " +
                        "estados de la programación, la solicitud y de la matrícula del contenedor (en caso necesario) de un equipo en la tabla de " +
                        "los equipos de las programaciones. ");
                programmingRepository.save(progToSave);
                expiredProgDto.setProgData(progDtoList.get(i));
                progWithExpirationsList.add(expiredProgDto);
            }

            try {
                log.info("ManagerServiceImpl.checkExpiredProgrammings: se procede a generar y enviar los mails.");
                List<EmailToSendDto> emailToSendDtoList = emailsService.createEmail(null, progWithExpirationsList);
                auditDto.setNumSentEmails(emailToSendDtoList.size());
                log.info("ManagerServiceImpl.checkExpiredProgrammings: se enviaron " + auditDto.getNumSentEmails() + " mails.");
            } catch (Exception e) {
                log.error("ManagerServiceImpl.checkExpiredProgrammings: error enviando los mails.");
                log.error("ManagerServiceImpl.checkExpiredProgrammings:" + Arrays.toString(e.getStackTrace()));
            }

            if (progWithExpirationsList.size() == 0) {
                response.setData(progWithExpirationsList);
                response.setCode(HttpStatus.NO_CONTENT);
                response.setMessage(messageNoContentExpireds);
            } else {
                response.setData(progWithExpirationsList);
                response.setCode(HttpStatus.OK);
                response.setMessage(messageOkExpireds);
            }

            response.setAudit(null);
            if (requestData.isHasAudit()) {
                //Se guarda la auditoría en la bbdd
                saveAudit(auditDto, auditModificationDtoList);
                response.setAudit(auditDto);
            }

            response.setData(progWithExpirationsList);
            return response;

        } catch (DateTimeParseException parseTime) {
            log.error("ManagerServiceImpl.checkExpiredProgrammings.genericFormatter " + parseTime.getMessage());
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(messageServerErrorExpireds);
            response.setData(null);
            response.setAudit(auditDto);
            return response;
        } catch (NullPointerException | IndexOutOfBoundsException NPexception) {
            log.error("ManagerServiceImpl.checkExpiredProgrammings " + NPexception.getMessage());
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(messageServerErrorExpireds);
            response.setData(null);
            response.setAudit(auditDto);
            return response;
        } catch (Exception e) {
            log.error("ManagerServiceImpl.checkExpiredProgrammings: " + e.getMessage());
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(messageServerErrorExpireds);
            response.setData(null);
            response.setAudit(auditDto);
            return response;
        }
    }

    //Obtiene los equipos (global y additional) de todas las programaciones sin el AppTrkMsg
    private void getEquipmentsWithoutAppTrkMsg(List<Programming> progList) {
        log.info("ManagerServiceImpl.getEquipmentsWithoutAppTrkMsg: inicio.");
        log.info("ManagerServiceImpl.getEquipmentsWithoutAppTrkMsg: se procede a obtener los datos de los expedientes " +
                "y de los equipos para las programaciones encontradas anteriormente.");
        progList.forEach(prog -> {
            if ("M".equals(prog.getExpType())) {
                prog.setExpedientMaritim(expedientMaritimRepository.findByIdExp(prog.getIdExp()));
            } else {
                prog.setExpedientTerrestre(expedientTerrestreRepository.findByIdExp(prog.getIdExp()));
            }
            prog.getGlobalEquipmentList()
                    .forEach(equipment -> equipment.setAdditionalEquipment(additionalEquipmentRepository.findByInternalDocAndInternalVsAndPlate(
                            equipment.getInternalDoc(),
                            equipment.getInternalVs(),
                            equipment.getPlate()
                    )));
        });

        log.info("ManagerServiceImpl.getEquipmentsWithoutAppTrkMsg: final.");
    }

    //Obtiene los equipos (global t additional) de todas las programaciones con el AppTrkMsg
    private void getEquipmentsWithAppTrkMsg(List<Programming> progList) {
        log.info("ManagerServiceImpl.getEquipmentsWithAppTrkMsg: inicio.");
        log.info("ManagerServiceImpl.getEquipmentsWithAppTrkMsg: se procede a obtener los datos de los expedientes " +
                "y de los equipos para las programaciones encontradas anteriormente.");
        progList.forEach(prog -> {
            if ("M".equals(prog.getExpType())) {
                prog.setExpedientMaritim(expedientMaritimRepository.findByIdExp(prog.getIdExp()));
            } else {
                prog.setExpedientTerrestre(expedientTerrestreRepository.findByIdExp(prog.getIdExp()));
            }
            prog.getGlobalEquipmentList()
                    .forEach(equipment -> {
                        equipment.setAdditionalEquipment(additionalEquipmentRepository.findByInternalDocAndInternalVsAndPlate(
                                equipment.getInternalDoc(),
                                equipment.getInternalVs(),
                                equipment.getPlate())
                        );
                        if (StringUtils.isNotEmpty(equipment.getAdditionalEquipment().getNumEntreguese())) {
                            Long maxMsgId = messageAttributesRepository.getMaxMsgIdByNumEntreguese(equipment.getAdditionalEquipment().getNumEntreguese());
                            equipment.getAdditionalEquipment().setAppTrkMsg(appTrkMsgRepository.findByIdEsmtMsg(maxMsgId));
                        }
                    });
        });

        log.info("ManagerServiceImpl.getEquipmentsWithAppTrkMsg: final.");
    }

    //Convierte un listado de equipamientos entity a dto de un listado de programacioens con el AppTrkMsg
    private void parseEquipmentToDtoIncidences(List<Programming> progList, List<ProgrammingDto> progDtoList) {
        log.info("ManagerServiceImpl.parseEquipmentToDtoIncidences: inicio.");
        IntStream.range(0, progList.size())
                .forEach(i -> {
                    progDtoList.get(i).setGlobalEquipmentDtosList(
                            globalEquipmentMapper.fromEntitiesToDtos(progList.get(i).getGlobalEquipmentList()));
                    IntStream.range(0, progList.get(i).getGlobalEquipmentList().size())
                            .forEach(j -> {
                                progDtoList.get(i).getGlobalEquipmentDtosList().get(j).setAdditionalEquipmentDto(
                                        additionalEquipmentMapper.fromEntityToDto(progList.get(i).getGlobalEquipmentList().get(j).getAdditionalEquipment()));
                                if (StringUtils.isNotEmpty(progList.get(i).getGlobalEquipmentList().get(j).getAdditionalEquipment().getNumEntreguese())) {
                                    progDtoList.get(i).getGlobalEquipmentDtosList().get(j).getAdditionalEquipmentDto().setAppTrkMsg(appTrkMsgMapper.fromEntityToDto(progList.get(i).getGlobalEquipmentList().get(j).getAdditionalEquipment().getAppTrkMsg()));
                                }
                            });
                });
        log.info("ManagerServiceImpl.parseEquipmentToDtoIncidences: final.");
    }

    //Convierte un listado de equipamientos entity a dto de un listado de programacioens sin AppTrkMsg
    private void parseEquipmentToDtoExpireds(List<Programming> progList, List<ProgrammingDto> progDtoList) {
        log.info("ManagerServiceImpl.parseEquipmentToDtoExpireds: inicio.");
        IntStream.range(0, progList.size())
                .forEach(i -> {
                    progDtoList.get(i).setGlobalEquipmentDtosList(
                            globalEquipmentMapper.fromEntitiesToDtos(progList.get(i).getGlobalEquipmentList()));
                    IntStream.range(0, progList.get(i).getGlobalEquipmentList().size())
                            .forEach(j ->
                                    progDtoList.get(i).getGlobalEquipmentDtosList().get(j).setAdditionalEquipmentDto(
                                            additionalEquipmentMapper.fromEntityToDto(progList.get(i).getGlobalEquipmentList().get(j).getAdditionalEquipment())));
                });
        log.info("ManagerServiceImpl.parseEquipmentToDtoExpireds: final.");
    }

    //Inicializa los campos de un AuditDto
    private void initAuditDto(AuditDto auditDto, List<ProgrammingDto> progDtoList, String service) {
        log.info("ManagerServiceImpl.initAuditDto: inicio");
        log.info("ManagerServiceImpl.initAuditDto: se inicializa la auditoria.");
        auditDto.setDate(LocalDateTime.now());
        auditDto.setId(utilsService.generateAuditId(auditDto.getDate()));
        auditDto.setNumChecked(progDtoList.size());
        auditDto.setService(service);
        auditDto.setNumModified(0);
        auditDto.setNumIncidences(0);
        auditDto.setNumSentEmails(0);
        log.info("ManagerServiceImpl.initAuditDto: final.");
    }

    //Inicializa los campos de un AuditModificationDto
    private void initAuditModificationDto(int i, AuditModificationDto auditModificationDto, AuditDto auditDto, ProgrammingDto progDto) {
        log.info("ManagerServiceImpl.initAuditModificationDto: inicio");
        auditModificationDto.setIdAudit(auditDto.getId());
        auditModificationDto.setIdProgramming((int) progDto.getIdProgramming());
        auditModificationDto.setOldProgStatus(progDto.getStatus());
        auditModificationDto.setOldRequestStatus(progDto.getRequest().getStatus());
        if (i == 0) {
            auditModificationDto.setIdModification(0);
        }
        log.info("ManagerServiceImpl.initAuditModificationDto: final");
    }

    //Setea la respuesta en el caso de no encontrar programaciones con entrégueses con incidencias
    private void noProgrammingFoundWithIncidences(ProgWithIncidencesResponseDto response, AuditDto auditDto, EntryRequestDto requestData) {
        log.info("ManagerServiceImpl.noProgrammingFoundIncidences: final");
        log.info("ManagerServiceImpl.noProgrammingFoundIncidences: no se encontraron programaciones.");
        response.setCode(HttpStatus.NO_CONTENT);
        response.setMessage(messageNoContentIncidences);
        response.setData(null);
        auditDto.setAuditModificationDtoList(null);
        response.setAudit(null);
        if (requestData.isHasAudit()) {        //Se guarda la auditoría en la bbdd
            log.info("ManagerServiceImpl.noProgrammingFoundIncidences: se guarda la auditoría en la base de datos.");
            response.setAudit(auditDto);
            Audit audit = auditMapper.fromDtoToEntity(auditDto);
            auditRepository.save(audit);
        }
        log.info("ManagerServiceImpl.noProgrammingFoundIncidences: final");
    }

    //Setea la respuesta en el caso de no encontrar programaciones expiradas
    private void noProgrammingFoundExpirated(ExpiredProgResponseDto response, AuditDto auditDto, EntryRequestDto requestData) {
        log.info("ManagerServiceImpl.noProgrammingFoundExpirations: inicio");
        log.info("ManagerServiceImpl.noProgrammingFoundExpirations: no se encontraron programaciones.");
        response.setCode(HttpStatus.NO_CONTENT);
        response.setMessage(messageNoContentExpireds);
        response.setData(null);
        auditDto.setAuditModificationDtoList(null);
        response.setAudit(null);
        if (requestData.isHasAudit()) {        //Se guarda la auditoría en la bbdd
            log.info("ManagerServiceImpl.noProgrammingFoundExpirations: se guarda la auditoría en la base de datos.");
            response.setAudit(auditDto);
            Audit audit = auditMapper.fromDtoToEntity(auditDto);
            auditRepository.save(audit);
        }
        log.info("ManagerServiceImpl.noProgrammingFoundExpirations: final");
    }

    //Setea los primeros campos de la ProgWithIncidencesDto
    private void initIncidencesProgDto(ProgWithIncidencesDto progWithIncidencesDto, GlobalEquipmentDto globalEquipmentDto) {
        log.info("ManagerServiceImpl.initIncidencesProgDto: inicio");
        progWithIncidencesDto.setStatus("");
        progWithIncidencesDto.setTrk(globalEquipmentDto.getAdditionalEquipmentDto().getAppTrkMsg().getNumRefFile());
        progWithIncidencesDto.setDate(globalEquipmentDto.getAdditionalEquipmentDto().getAppTrkMsg().getProcessStartDate());
        log.info("ManagerServiceImpl.initIncidencesProgDto: final");
    }

    //Setea los primeros campos de la ExpiredProgDto
    private void initExpiredProgDto(ExpiredProgDto expiredProgDto, ProgrammingDto progDto) {
        log.info("ManagerServiceImpl.initExpiredProgDto: inicio");
        expiredProgDto.setId(progDto.getIdProgramming());
        expiredProgDto.setExpirationDate(progDto.getExpirationDate());
        expiredProgDto.setCustomerName(progDto.getCreatorUser());
        log.info("ManagerServiceImpl.initExpiredProgDto: final");
    }

    //Setea el UserConfig
    private void initUserConfigForIncidences(ProgrammingDto progDto, Programming prog, AuditModificationDto auditModificationDto, ProgWithIncidencesDto progWithIncidencesDto) {
        log.info("ManagerServiceImpl.initUserConfigForIncidences: inicio");
        if (progDto.getNotifyIncidences().equals("S")) {
            log.info("ManagerServiceImpl.initUserConfigForIncidences: se procede a buscar la empresa por NIF");
            Company company = companyRepository.findByNifCompany(prog.getNifCompanyUser());
            if (company != null) {
                log.info("ManagerServiceImpl.initUserConfigForIncidences: se procede a buscar el UserConfig por usuario y id de la empresa");
                prog.setUserConfig(userConfigRepository.findByUserAndIdCompany(prog.getCreatorUser(), company.getIdCompany()));
                progDto.setUserConfig(userConfigMapper.fromEntityToDto(prog.getUserConfig()));
                if (prog.getUserConfig().getEmail() != null) {
                    progWithIncidencesDto.setSentEmails(utilsService.getEmailsForOneUser(prog.getUserConfig()));
                    auditModificationDto.setReceptorEmails(progDto.getUserConfig().getEmail());
                } else {
                    progWithIncidencesDto.setSentEmails(null);
                    auditModificationDto.setReceptorEmails(null);
                }
            }
        }
        log.info("ManagerServiceImpl.initUserConfigForIncidences: final");
    }

    //Comprueba si el usuario quiere ser notificado para cierta programación, en caso que sí, se busca su UserConfig
    private void initUserConfigForExpirations(ProgrammingDto progDto, Programming prog, AuditModificationDto auditModificationDto, ExpiredProgDto expiredProgDto) {
        log.info("ManagerServiceImpl.initUserConfigForExpirations: inicio");
        if (progDto.getNotifyIncidences().equals("S")) {
            log.info("ManagerServiceImpl.initUserConfigForExpirations: se procede a buscar la empresa por NIF");
            Company company = companyRepository.findByNifCompany(prog.getNifCompanyUser());
            if (company != null) {
                log.info("ManagerServiceImpl.initUserConfigForExpirations: se procede a buscar el UserConfig por usuario y id de la empresa");
                prog.setUserConfig(userConfigRepository.findByUserAndIdCompany(prog.getCreatorUser(), company.getIdCompany()));
                progDto.setUserConfig(userConfigMapper.fromEntityToDto(prog.getUserConfig()));
                if (prog.getUserConfig().getEmail() != null) {
                    expiredProgDto.setSentEmails(utilsService.getEmailsForOneUser(prog.getUserConfig()));
                    auditModificationDto.setReceptorEmails(progDto.getUserConfig().getEmail());
                } else {
                    expiredProgDto.setSentEmails(null);
                    auditModificationDto.setReceptorEmails(null);
                }
            }
        }
        log.info("ManagerServiceImpl.initUserConfigForExpirations: final");
    }

    //Busca la última matrícula dummy y setea el valor de una matrícula Dummy en el caso que llegara algún codeco
    private void findAndCreateLastDummy(ProgrammingDto progDto) {
        log.info("ManagerServiceImpl.findAndCreateLastDummy: inicio");
        progDto.getGlobalEquipmentDtosList()
                .stream()
                .filter(globalEquipmentDto ->
                        globalEquipmentDto.getAdditionalEquipmentDto() != null &&
                                globalEquipmentDto.getAdditionalEquipmentDto().getNumCodeco() != null)
                .forEach(globalEquipmentDto -> {
                    int k = utilsService.findLastDummy(progDto.getInternalDoc(), progDto.getInternalVs()) + 1;
                    String formattedK = k <= 999 ? String.format("%03d", k) : "999";
                    globalEquipmentDto.setPlate(dummy + formattedK);
                });
        log.info("ManagerServiceImpl.findAndCreateLastDummy: final");
    }

    //Incrementa y modifica los valores del AuditDto y de los AuditModificationDto
    private void modifyAuditDtos(AuditDto auditDto, AuditModificationDto auditModificationDto, ProgrammingDto progDto, int auditModificationListSize) {
        log.info("ManagerServiceImpl.modifyAuditDtos: inicio");
        auditDto.setNumIncidences(auditDto.getNumIncidences() + 1);
        auditDto.setNumModified(auditDto.getNumModified() + 1);
        auditModificationDto.setIdModification(auditModificationListSize + 1);
        auditModificationDto.setPresentProgStatus(progDto.getStatus());
        auditModificationDto.setPresentRequestStatus(progDto.getRequest().getStatus());
        log.info("ManagerServiceImpl.modifyAuditDtos: final");
    }

    //Guarda en la base de datos los datos del Audit y de su lista de AuditModifications
    private void saveAudit(AuditDto auditDto, List<AuditModificationDto> auditModificationDtoList) {
        log.info("ManagerServiceImpl.saveAudit: inicio.");
        log.info("ManagerServiceImpl.saveAudit: se guarda la auditoría en la base de datos.");
        auditDto.setAuditModificationDtoList(auditModificationDtoList);
        Audit audit = auditMapper.fromDtoToEntity(auditDto);
        audit.setAuditModificationList(auditModificationMapper.fromDtosToEntities(auditModificationDtoList));
        auditRepository.save(audit);
        log.info("ManagerServiceImpl.saveAudit: final.");
    }

    //Obtiene el tipo de incidencia de la programación
    private void obtainAndSetIncidenceType(ProgWithIncidencesDto progWithIncidencesDto, GlobalEquipmentDto globalEquipmentDto) {
        log.info("ManagerServiceImpl.obtainAndSetIncidenceType: inicio");
        if (globalEquipmentDto.getAdditionalEquipmentDto().getAppTrkMsg().getProcessStatus().equals("OKCA")) {
            progWithIncidencesDto.setStatus("Cancelado");
            globalEquipmentDto.getAdditionalEquipmentDto().setState("OKCA");
        } //APERAK asociado a un entréguese de vacío asociado a una programación sea de rechazo
        else if (globalEquipmentDto.getAdditionalEquipmentDto().getAppTrkMsg().getProcessStatus().equals("OKNO")) {
            progWithIncidencesDto.setStatus("Rechazado");
            globalEquipmentDto.getAdditionalEquipmentDto().setState("OKNO");
        } //Que un entréguese de vacío asociado a una programación haya llegado a la fecha de vencimiento sin que haya llegado el CODECO correspondiente
        else if (globalEquipmentDto.getAdditionalEquipmentDto().getExpirationDate().isBefore(LocalDateTime.now()) &&
                globalEquipmentDto.getAdditionalEquipmentDto().getNumCodeco() == null) {
            progWithIncidencesDto.setStatus("Vencido");
            globalEquipmentDto.getAdditionalEquipmentDto().setState("OKVE");
        }
        log.info("ManagerServiceImpl.obtainAndSetIncidenceType: final");
    }

    //Setea el valor de respuesta para las incidencias
    private void setResponseDataIncidences(ProgWithIncidencesResponseDto response, List<ProgWithIncidencesDto> progWithIncidencesList) {
        log.info("ManagerServiceImpl.setResponseDataIncidences: inicio");
        if (progWithIncidencesList.size() == 0) {
            response.setData(progWithIncidencesList);
            response.setCode(HttpStatus.NO_CONTENT);
            response.setMessage(messageNoContentIncidences);
        } else {
            response.setData(progWithIncidencesList);
            response.setCode(HttpStatus.OK);
            response.setMessage(messageOkIncidences);
        }
        log.info("ManagerServiceImpl.setResponseDataIncidences: final");
    }

    //Comprueba si una programación tiene algún de los 3 tipos de incidencia
    private boolean hasIncidence(ProgWithIncidencesDto progWithIncidences) {
        switch (progWithIncidences.getStatus()) {
            case "Cancelado", "Rechazado", "Vencido":
                return true;
            default:
                return false;
        }
    }
}
