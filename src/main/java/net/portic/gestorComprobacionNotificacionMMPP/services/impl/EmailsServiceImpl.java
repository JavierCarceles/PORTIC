package net.portic.gestorComprobacionNotificacionMMPP.services.impl;

import net.portic.gestorComprobacionNotificacionMMPP.config.BuzonesProperties;
import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.EmailToSendDto;
import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.ExpiredProgDto;
import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.ProgWithIncidencesDto;
import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

@Service
public class EmailsServiceImpl {

    Logger log = LoggerFactory.getLogger(EmailsServiceImpl.class);

    private static final String inicioExpiradaT = "Ha expirado la programación de MMPP terrestres asociada a los entrégueses: ";
    private static final String inicioExpiradaM = "Ha expirado la programación de MMPP de la escala ";
    private static final String continuacionExpiradaM = " asociada a los entrégueses: ";

    private static final String MATTER_INCIDENCES = "Incidencias en las programaciones de Mercancías peligrosas";
    private static final String MATTER_EXPIRED = "Expiración de las programaciones de Mercancías peligrosas";

    private BuzonesProperties buzonesProperties;

    private UtilsServiceImpl utilsService;

    @Autowired
    public EmailsServiceImpl(BuzonesProperties buzonesProperties,
                             UtilsServiceImpl utilsService) {
        this.buzonesProperties = buzonesProperties;
        this.utilsService = utilsService;
    }

    // Crea y envía correos electrónicos basados en las incidencias y configuraciones del usuario.
    //Solo puede haber una lista null y la otra con datos (en los parámetros de entrada)
    @Transactional
    public List<EmailToSendDto> createEmail(List<ProgWithIncidencesDto> dataIncidences, List<ExpiredProgDto> dataExpired) {
        log.info("EmailsServiceImpl.createEmail: inicio");
        if ((dataIncidences == null || dataIncidences.isEmpty()) && (dataExpired == null || dataExpired.isEmpty())) {
            return Collections.emptyList();
        } else {
            List<EmailToSendDto> emailToSendList = new ArrayList<>();
            //Emails para las incidencias
            if (isIncidence(dataIncidences, dataExpired)) {
                ArrayList<ProgWithIncidencesDto> orderedListMT = utilsService.ordenarIncidencias(dataIncidences);
                //Borramos esos equipos sin numEntreguese
                deleteEquipments(orderedListMT);

                AtomicInteger i = new AtomicInteger();
                orderedListMT.forEach(prog -> {
                    logicEmailIncidences(prog, orderedListMT, i.get(), emailToSendList);
                    i.getAndIncrement();
                });

                //lógica para que los usuarios con más de un mail, reciban el mismo mensaje en cada dirección de correo
                ArrayList<ProgWithIncidencesDto> orderedListMTWithoutRepetitions = new ArrayList<>();
                processSeveralEmails(orderedListMT, orderedListMTWithoutRepetitions);
                addEmailInfoIncidences(orderedListMTWithoutRepetitions, emailToSendList);
            }
            //Emails para las expiraciones
            else if (isExpired(dataIncidences, dataExpired)) {
                processDataExpired(dataExpired, emailToSendList);
            }
            //Se envían los emails
            emailToSendList.forEach(this::sendEmail);
            log.info("EmailsServiceImpl.createEmail: final");
            return emailToSendList;
        }
    }

    //Envía un email 'EmailToSendDto'
    public void sendEmail(EmailToSendDto email) {
        log.info("EmailsServiceImpl.sendEmail: inicio");
        try {
            String receiver = email.getCustomerData().getEmail();
            if (receiver != null && !receiver.isEmpty()) {
                Properties props = System.getProperties();
                props.put("mail.smtp.host", buzonesProperties.getReboteMailhost());
                Session session = Session.getDefaultInstance(props, null);
                Message msg = new MimeMessage(session);
                msg.setFrom(new InternetAddress("noreply@portic.net"));
                msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver, false));
                msg.setSubject(email.getTitle());
                msg.setSentDate(new Date());
                Multipart mp = new MimeMultipart();

                MimeBodyPart mbp1;
                if (email.getContent() != null) {
                    String htmlContent = email.getContent().replace("\n", "<br>");
                    mbp1 = new MimeBodyPart();
                    mbp1.setContent(htmlContent, "text/html; charset=UTF-8");
                    mp.addBodyPart(mbp1);
                }

                if (email.getMatter() != null && !email.getMatter().isEmpty() && email.getContent() == null) {
                    mbp1 = new MimeBodyPart();
                    mbp1.setText(email.getMatter(), "UTF-8");
                    mp.addBodyPart(mbp1);
                }
                msg.setContent(mp);
                Transport.send(msg);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        log.info("EmailsServiceImpl.sendEmail: final");
    }

    //Elimina los equipos sin número de entréguese de la lista de programaciones
    private void deleteEquipments(ArrayList<ProgWithIncidencesDto> orderedListMT) {
        log.info("EmailsServiceImpl.deleteEquipments: inicio");
        orderedListMT.forEach(prog ->
                prog.getProgData().setGlobalEquipmentDtosList(
                        prog.getProgData().getGlobalEquipmentDtosList().stream()
                                .filter(dto -> !StringUtils.isEmpty(dto.getAdditionalEquipmentDto().getNumEntreguese()))
                                .toList()
                )
        );
        log.info("EmailsServiceImpl.deleteEquipments: final");
    }

    //Construye el primer email para incidencias
    private void buildFirstEmail(List<EmailToSendDto> emailToSendList, ArrayList<ProgWithIncidencesDto> orderedListMT, int i, int contadorEquipos, int incidences) {
        log.info("EmailsServiceImpl.buildFirstEmail: inicio");
        EmailToSendDto firstEmail = utilsService.setContentEmail(orderedListMT, i, contadorEquipos, incidences);
        firstEmail.setCustomerData(orderedListMT.get(i).getSentEmails().get(0));
        firstEmail.setTitle(MATTER_INCIDENCES);
        firstEmail.setMatter(MATTER_INCIDENCES);
        emailToSendList.add(firstEmail);
        log.info("EmailsServiceImpl.buildFirstEmail: final");
    }

    //Añade el contenido de una incidencia en un email creado anteriormente o genera un nuevo email.
    private boolean processEmailIncidences(List<EmailToSendDto> emailToSendList, ArrayList<ProgWithIncidencesDto> orderedListMT, int i, int contadorEquipos, int incidences) {
        log.info("EmailsServiceImpl.processEmailIncidences: inicio");
        boolean added = emailToSendList.stream()
                .filter(email -> utilsService.checkCustomerData(email.getCustomerData(), orderedListMT.get(i).getSentEmails().get(0)))
                .findFirst()
                .map(email -> {
                    EmailToSendDto updatedEmail = utilsService.setContentEmail(orderedListMT, i, contadorEquipos, incidences);
                    email.setContent(email.getContent() + updatedEmail.getContent());
                    return true;
                })
                .orElse(false);

        if (!added) {
            EmailToSendDto newEmail = utilsService.setContentEmail(orderedListMT, i, contadorEquipos, incidences);
            newEmail.setCustomerData(orderedListMT.get(i).getSentEmails().get(0));
            newEmail.setTitle(MATTER_INCIDENCES);
            newEmail.setMatter(MATTER_INCIDENCES);
            emailToSendList.add(newEmail);
        }
        log.info("EmailsServiceImpl.processEmailIncidences: final");
        return added;
    }

    //Genera un nuevo email para las expiraciones y lo añade a la lista de emails para enviar
    private void processEmailExpirations(ExpiredProgDto dataExpired, List<EmailToSendDto> emailToSendList, StringBuilder contentT, StringBuilder contentM, boolean added) {
        log.info("EmailsServiceImpl.processEmailExpirations: inicio");
        if (!added) {
            EmailToSendDto email = new EmailToSendDto();
            email.setCustomerData(dataExpired.getSentEmails().get(0));
            email.setTitle(MATTER_EXPIRED);
            email.setMatter(MATTER_EXPIRED);
            if (dataExpired.getProgData().getExpType().equals("T")) {
                contentT.append(inicioExpiradaT).append("\n");
                dataExpired.getProgData().getGlobalEquipmentDtosList().forEach(equipment -> {
                    if (StringUtils.isNotEmpty(equipment.getAdditionalEquipmentDto().getNumEntreguese())) {
                        contentT.append("-").append(equipment.getAdditionalEquipmentDto().getNumEntreguese()).append("\n");
                    }
                });
                email.setContent(contentT + "\n");
            } else {
                contentM.append(inicioExpiradaM).append(dataExpired.getProgData().getExpedientMaritim().getPortCall()).append(continuacionExpiradaM).append("\n");
                dataExpired.getProgData().getGlobalEquipmentDtosList().forEach(equipment -> {
                    if (StringUtils.isNotEmpty(equipment.getAdditionalEquipmentDto().getNumEntreguese())) {
                        contentM.append("-").append(equipment.getAdditionalEquipmentDto().getNumEntreguese()).append("\n");
                    }
                });
                email.setContent(contentM + "\n");
            }
            emailToSendList.add(email);
            if (dataExpired.getSentEmails().size() > 1) {
                dataExpired.getSentEmails()
                        .stream().skip(1).forEach(sendEmail -> {
                            EmailToSendDto repeatedEmail = new EmailToSendDto(
                                    sendEmail,
                                    email.getTitle(),
                                    email.getMatter(),
                                    email.getContent()
                            );
                            emailToSendList.add(repeatedEmail);
                        });
            }
        }
        log.info("EmailsServiceImpl.processEmailExpirations: final");
    }

    //Setea el contenido de los distintos emails de incidencias para los distintos receptores
    private void addEmailInfoIncidences(ArrayList<ProgWithIncidencesDto> orderedListMTWithoutRepetitions, List<EmailToSendDto> emailToSendList) {
        log.info("EmailsServiceImpl.addEmailInfoIncidences: inicio");
        IntStream.range(0, orderedListMTWithoutRepetitions.size()).forEach(i -> {
            ProgWithIncidencesDto item = orderedListMTWithoutRepetitions.get(i);
            IntStream.range(1, item.getSentEmails().size()).forEach(j -> {
                EmailToSendDto newEmail = new EmailToSendDto();
                newEmail.setTitle(emailToSendList.get(i).getTitle());
                newEmail.setMatter(emailToSendList.get(i).getMatter());
                newEmail.setContent(emailToSendList.get(i).getContent());
                newEmail.setCustomerData(item.getSentEmails().get(j));
                emailToSendList.add(newEmail);
            });
        });
        log.info("EmailsServiceImpl.addEmailInfoIncidences: final");
    }

    //Añade nuevo contenido en los emails para expiraciones ya creados anteriormente.
    private void addEmailInfoExpirations(EmailToSendDto email, ExpiredProgDto prog, StringBuilder contentT, StringBuilder contentM) {
        log.info("EmailsServiceImpl.addEmailInfoExpirations: inicio");
        StringBuilder newContent = new StringBuilder(email.getContent());
        if (prog.getProgData().getExpType().equals("T")) {
            contentT.append(inicioExpiradaT).append("\n");
            prog.getProgData().getGlobalEquipmentDtosList().forEach(equipment -> {
                if (StringUtils.isNotEmpty(equipment.getAdditionalEquipmentDto().getNumEntreguese())) {
                    contentT.append("-").append(equipment.getAdditionalEquipmentDto().getNumEntreguese()).append("\n");
                }
            });
            email.setContent(newContent + contentT.toString() + "\n");
        } else {
            contentM.append(inicioExpiradaM).append(prog.getProgData().getExpedientMaritim().getPortCall()).append(continuacionExpiradaM).append("\n");
            prog.getProgData().getGlobalEquipmentDtosList().forEach(equipment -> {
                if (StringUtils.isNotEmpty(equipment.getAdditionalEquipmentDto().getNumEntreguese())) {
                    contentM.append("-").append(equipment.getAdditionalEquipmentDto().getNumEntreguese()).append("\n");
                }
            });
            email.setContent(newContent + contentM.toString() + "\n");
        }
        log.info("EmailsServiceImpl.addEmailInfoExpirations: final");
    }

    //Añade contenido a los emails existentes cuando el usuario receptor se repite
    private boolean addMoreEmailContent(List<EmailToSendDto> emailToSendList, ExpiredProgDto prog, StringBuilder contentT, StringBuilder contentM,  int i) {
        log.info("EmailsServiceImpl.addMoreEmailContent: inicio");
        if (i != 0) {
            boolean[] added = {false};
            int[] copiedIndex = {0};
            int[] count = {0};
            //Lambda para no reenviar dos o más correos a la misma persona
            prog.getSentEmails().forEach(sentEmail -> {
                for(int k = count[0]; k<prog.getSentEmails().size();k++){
                    if (utilsService.checkCustomerData(emailToSendList.get(count[0]).getCustomerData(), prog.getSentEmails().get(k))) {
                        if(added[0]) {
                            emailToSendList.get(count[0]).setContent(emailToSendList.get(copiedIndex[0]).getContent());
                        }
                        else{
                            addEmailInfoExpirations(emailToSendList.get(count[0]), prog, contentT, contentM);
                            copiedIndex[0] = count[0];
                            added[0] = true;
                        }
                        break;
                    }
                }
                count[0]++;
            });
            log.info("EmailsServiceImpl.addMoreEmailContent: final");
            return added[0];
        }
        log.info("EmailsServiceImpl.addMoreEmailContent: final");
        return false;
    }

    //Validaciones para saber si estamos en el camino de incidencias.
    private boolean isIncidence(List<ProgWithIncidencesDto> dataIncidences, List<ExpiredProgDto> dataExpired) {
        return ((dataIncidences != null) && (dataExpired == null || dataExpired.isEmpty()));
    }

    //Validaciones para saber si estamos en el camino de expiraciones.
    private boolean isExpired(List<ProgWithIncidencesDto> dataIncidences, List<ExpiredProgDto> dataExpired) {
        return (dataExpired != null && (dataIncidences == null || dataIncidences.isEmpty()));
    }

    //Procesa la lista de programaciones para quitar  esas con el usuario repetido (en este punto ya tenemos los emails creados cuando llamamos a este método)
    private void processSeveralEmails(List<ProgWithIncidencesDto> orderedListMT, ArrayList<ProgWithIncidencesDto> orderedListMTWithoutRepetitions) {
        log.info("EmailsServiceImpl.addUniqueIncidences: inicio");
        for (int i = 0; i < orderedListMT.size(); i++) {
            if (i == 0) {
                orderedListMTWithoutRepetitions.add(orderedListMT.get(i));
                continue;
            }
            if (!utilsService.isARepeatedUserForProgrammings(orderedListMTWithoutRepetitions, orderedListMT.get(i))) {
                orderedListMTWithoutRepetitions.add(orderedListMT.get(i));
            }
        }
        log.info("EmailsServiceImpl.addUniqueIncidences: final");
    }

    //Decide si generar un nuevo mensaje o añadir contenido a uno ya existente
    private void processDataExpired(List<ExpiredProgDto> dataExpired, List<EmailToSendDto> emailToSendList) {
        log.info("EmailsServiceImpl.processDataExpired: inicio");
        for (int i = 0; i < dataExpired.size(); i++) {
            StringBuilder contentT = new StringBuilder();
            StringBuilder contentM = new StringBuilder();
            boolean added = false;

            // Rellena más contenido en un email ya existente
            added = addMoreEmailContent(emailToSendList, dataExpired.get(i), contentT, contentM, i);

            // Genera los campos del mensaje en el caso de tener que crear un nuevo email
            processEmailExpirations(dataExpired.get(i), emailToSendList, contentT, contentM, added);
        }
        log.info("EmailsServiceImpl.processDataExpired: final");
    }

    //Setea el tipo de incidencia y posteriormente decide si crear el primer email, crear uno nuevo o añadir conteinido en uno ya existente
    private void logicEmailIncidences(ProgWithIncidencesDto prog, ArrayList<ProgWithIncidencesDto> progList, int i, List<EmailToSendDto> emailToSendList) {
        log.info("EmailsServiceImpl.logicEmailIncidences: inicio");
        int contadorEquipos = 0;
        Map<String, Integer> statusMap = Map.of(
                "Cancelado", 1,
                "Rechazado", 2,
                "Vencido", 3
        );
        int incidences = statusMap.getOrDefault(prog.getStatus(), 0);

        if (contadorEquipos >= prog.getProgData().getGlobalEquipmentDtosList().size()) {
            contadorEquipos = 0;
        }

        //Primer email
        if (emailToSendList.size() == 0) {
            buildFirstEmail(emailToSendList, progList, i, contadorEquipos, incidences);
            return ;
        }

        if (isSameProgData(progList, i)) {
            contadorEquipos++;
        } else {
            contadorEquipos = 0;
        }
        processEmailIncidences(emailToSendList, progList, i, contadorEquipos, incidences);
        log.info("EmailsServiceImpl.logicEmailIncidences: final");
    }

    //Comprueba si la programación anterior tratada es la misma que la actual
    private boolean isSameProgData(List<ProgWithIncidencesDto> orderedListMT, int currentIndex) {
        return orderedListMT.get(currentIndex).getProgData().getInternalDoc() == orderedListMT.get(currentIndex - 1).getProgData().getInternalDoc() &&
                orderedListMT.get(currentIndex).getProgData().getInternalVs() == orderedListMT.get(currentIndex - 1).getProgData().getInternalVs();
    }

}



