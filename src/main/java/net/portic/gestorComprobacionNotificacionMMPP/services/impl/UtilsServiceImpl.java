package net.portic.gestorComprobacionNotificacionMMPP.services.impl;

import net.portic.gestorComprobacionNotificacionMMPP.controllers.ManagerController;
import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.EmailToSendDto;
import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.ProgWithIncidencesDto;
import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.SentEmailDto;
import net.portic.gestorComprobacionNotificacionMMPP.domain.models.UserConfig;
import net.portic.gestorComprobacionNotificacionMMPP.repositories.GlobalEquipmentRepository;
import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UtilsServiceImpl {

    private static final String MSSG_CANCELADO_M = "Se ha abortado la programación correspondiente a la solicitud %s de la escala %s del Buque %s porque se anuló el entréguese %s asociado.";
    private static final String MSSG_CANCELADO_M_SIN_NUM_DOC= "Se ha abortado la programación de MMPP de la escala %s del buque %s porque se anuló el entréguese %s asociado.";
    private static final String MSSG_RECHAZADO_M = "Se ha abortado la programación correspondiente a la solicitud %s de la escala %s del Buque %s porque el entréguese %s asociado fue rechazado por la terminal.";
    private static final String MSSG_RECHAZADO_M_SIN_NUM_DOC= "Se ha abortado la programación de MMPP de la escala %s del buque %s porque el entréguese %s asociado fue rechazado por la terminal.";
    private static final String MSSG_VENCIDO_M = "Se ha abortado la programación correspondiente a la solicitud %s de la escala %s del Buque %s porque el entréguese %s asociado ha vencido sin haber sido recogido el contenedor.";
    private static final String MSSG_VENCIDO_M_SIN_NUM_DOC= "Se ha abortado la programación de MMPP de la escala %s del buque %s porque el entréguese %s asociado ha vencido sin haber sido recogido el contenedor.";

    private static final String MSSG_CANCELADO_T = "Se ha abortado la programación correspondiente a la solicitud %s porque se anuló el entréguese %s asociado.";
    private static final String MSSG_CANCELADO_T_SIN_NUM_DOC = "Se ha abortado la programación de MMPP terrestre porque se anuló el entréguese %s asociado.";
    private static final String MSSG_RECHAZADO_T = "Se ha abortado la programación correspondiente a la solicitud %s porque el entréguese %s asociado fue rechazado por la terminal.";
    private static final String MSSG_RECHAZADO_T_SIN_NUM_DOC = "Se ha abortado la programación de MMPP terrestre porque el entréguese %s asociado fue rechazado por la terminal.";
    private static final String MSSG_VENCIDO_T = "Se ha abortado la programación correspondiente a la solicitud %s porque el entréguese %s asociado ha vencido sin haber sido recogido el contenedor.";
    private static final String MSSG_VENCIDO_T_SIN_NUM_DOC = "Se ha abortado la programación de MMPP terrestre porque el entréguese %s asociado ha vencido sin haber sido recogido el contenedor.";

    static Logger log = LoggerFactory.getLogger(ManagerController.class);

    @Autowired
    private GlobalEquipmentRepository globalEquipmentRepository;

    public ArrayList<ProgWithIncidencesDto> ordenarIncidencias(List<ProgWithIncidencesDto> dataIncidences) {

        ArrayList<ProgWithIncidencesDto> listaMaritimos = new ArrayList<>();
        ArrayList<ProgWithIncidencesDto> listaTerrestres = new ArrayList<>();
        ArrayList<ProgWithIncidencesDto> listaCompleta = new ArrayList<>();

        for (int i = 0; i < dataIncidences.size(); i++) {

            //Si el usuario no tiene correo o no tiene userConfig(usuario/correo) no se le añade
            if ((dataIncidences.get(i).getSentEmails() != null) ||
                    (dataIncidences.get(i).getProgData().getUserConfig() != null)) {

                if (dataIncidences.get(i).getProgData().getExpType().equals("M")) {
                    listaMaritimos.add(dataIncidences.get(i));
                } else if (dataIncidences.get(i).getProgData().getExpType().equals("T")) {
                    listaTerrestres.add(dataIncidences.get(i));
                }
            }
        }

        //Ordena por portcall y en caso de empate por documentNumber, tiene en cuenta los valores null y los apila al final
        Collections.sort(listaMaritimos, Comparator
                .comparing((ProgWithIncidencesDto o) -> o.getProgData().getExpedientMaritim().getPortCall(), Comparator.nullsLast(Comparator.naturalOrder()))
                .thenComparing(o -> o.getProgData().getRequest().getDocumentNumber(), Comparator.nullsLast(Comparator.naturalOrder())));

        //llama al metodo compareTo de la clase ProgWithIncidencesDto
        Collections.sort(listaTerrestres, Comparator
                .comparing((ProgWithIncidencesDto o2) -> o2.getProgData().getRequest().getDocumentNumber(), Comparator.nullsLast(Comparator.naturalOrder())));

        listaCompleta.addAll(listaMaritimos);
        listaCompleta.addAll(listaTerrestres);

        return listaCompleta;

    }

    public void appendIncidenceMessageMaritimo(StringBuilder message, int incidences, String numDocument, String portCall, String shipName, String numEntreguese) {
        String formattedMessage = null;

        switch (incidences) {
            case 1:
                if(StringUtils.isEmpty(numDocument)){
                    formattedMessage = String.format(MSSG_CANCELADO_M_SIN_NUM_DOC, portCall, shipName, numEntreguese);
                }
                else{
                    formattedMessage = String.format(MSSG_CANCELADO_M, numDocument, portCall, shipName, numEntreguese);
                }
                break;
            case 2:
                if(StringUtils.isEmpty(numDocument)){
                    formattedMessage = String.format(MSSG_RECHAZADO_M_SIN_NUM_DOC, portCall, shipName, numEntreguese);
                }
                else{
                    formattedMessage = String.format(MSSG_RECHAZADO_M, numDocument, portCall, shipName, numEntreguese);
                }
                break;
            case 3:
                if(StringUtils.isEmpty(numDocument)){
                    formattedMessage = String.format(MSSG_VENCIDO_M_SIN_NUM_DOC, portCall, shipName, numEntreguese);
                }
                else{
                    formattedMessage = String.format(MSSG_VENCIDO_M, numDocument, portCall, shipName, numEntreguese);
                }
                break;
            default:
                formattedMessage = String.format(MSSG_VENCIDO_M, "-", "-", "-", "-");
                break;
        }

        if (formattedMessage != null && message.indexOf(formattedMessage) == -1) {
            message.append(formattedMessage).append("\n");
        }
    }

    public void appendIncidenceMessageTerrestre(StringBuilder message, int incidences, String numDocument, String numEntreguese) {
        String formattedMessage = null;

        switch (incidences) {
            case 1:
                if(StringUtils.isEmpty(numDocument)){
                    formattedMessage = String.format(MSSG_CANCELADO_T_SIN_NUM_DOC, numEntreguese);
                }
                else{
                    formattedMessage = String.format(MSSG_CANCELADO_T, numDocument, numEntreguese);
                }
                break;
            case 2:
                if(StringUtils.isEmpty(numDocument)){
                    formattedMessage = String.format(MSSG_RECHAZADO_T_SIN_NUM_DOC, numEntreguese);
                }
                else{
                    formattedMessage = String.format(MSSG_RECHAZADO_T, numDocument, numEntreguese);
                }
                break;
            case 3:
                if(StringUtils.isEmpty(numDocument)){
                    formattedMessage = String.format(MSSG_VENCIDO_T_SIN_NUM_DOC, numEntreguese);
                }
                else{
                    formattedMessage = String.format(MSSG_VENCIDO_T, numDocument, numEntreguese);
                }
                break;
            default:
                formattedMessage = String.format(MSSG_VENCIDO_T, "-", "-");
                break;
        }

        if (formattedMessage != null && message.indexOf(formattedMessage) == -1) {
            message.append(formattedMessage).append("\n");
        }
    }


    public long generateAuditId(LocalDateTime date) {
        //year (4 digits)
        String idStr = String.valueOf(date.getYear());
        //month (2 digits)
        if (String.valueOf(date.getMonthValue()).length() == 1) {
            idStr = idStr + "0" + String.valueOf(date.getMonthValue());
        } else {
            idStr = idStr + String.valueOf(date.getMonthValue());
        }
        //day (2 digits)
        if (String.valueOf(date.getDayOfMonth()).length() == 1) {
            idStr = idStr + "0" + String.valueOf(date.getDayOfMonth());
        } else {
            idStr = idStr + String.valueOf(date.getDayOfMonth());
        }
        //hour (2 digits)
        if (String.valueOf(date.getHour()).length() == 1) {
            idStr = idStr + "0" + String.valueOf(date.getHour());
        } else {
            idStr = idStr + String.valueOf(date.getHour());
        }
        //minut (2 digits)
        if (String.valueOf(date.getMinute()).length() == 1) {
            idStr = idStr + "0" + String.valueOf(date.getMinute());
        } else {
            idStr = idStr + String.valueOf(date.getMinute());
        }
        //second (2 digits)
        if (String.valueOf(date.getSecond()).length() == 1) {
            idStr = idStr + "0" + String.valueOf(date.getSecond());
        } else {
            idStr = idStr + String.valueOf(date.getSecond());
        }
        return Long.parseLong(idStr);
    }

    //En la base de datos, obtenemos los emails concatenados por ";",
    public List<SentEmailDto> getEmailsForOneUser(UserConfig userConfig) {
        List<SentEmailDto> sentEmailDtoList = new ArrayList<>();
        //Hay más de un correo electrònico
        if (userConfig.getEmail().contains(";")) {
            String[] splitEmails = userConfig.getEmail().split(";");
            for (String email : splitEmails) {
                email = email.trim();
                if (!email.isEmpty()) {
                    SentEmailDto sentEmail = new SentEmailDto();
                    sentEmail.setCustomerName(userConfig.getUser());
                    sentEmail.setCustomerNif(userConfig.getCompany().getNifCompany());
                    sentEmail.setEmail(email);
                    sentEmailDtoList.add(sentEmail);
                }
            }
        }

        else { //Hay solo un correo electrònico
            SentEmailDto sentEmail = new SentEmailDto();
            sentEmail.setCustomerName(userConfig.getUser());
            sentEmail.setCustomerNif(userConfig.getCompany().getNifCompany());
            sentEmail.setEmail(userConfig.getEmail());
            sentEmailDtoList.add(sentEmail);
        }
        return sentEmailDtoList;
    }

    public boolean checkCustomerData(SentEmailDto data1, SentEmailDto data2) {

        if (data1.getCustomerName().equals(data2.getCustomerName()) &&
                data1.getCustomerNif().equals(data2.getCustomerNif()) &&
                data1.getEmail().equals(data2.getEmail())) {
            return true;
        }
        return false;
    }

    public int findLastDummy(long internalDoc, int internalVs) {
        try {
            return Integer.parseInt(String.format("%03d", globalEquipmentRepository.getLastDummy(internalDoc, internalVs)));
        } catch (NullPointerException n) {
            return 0;
        }

    }

    public EmailToSendDto setContentEmail(ArrayList<ProgWithIncidencesDto> orderedListMT, int i, int contadorEquipos, int incidences) {
        EmailToSendDto mail = new EmailToSendDto();

        String numEntreguese = orderedListMT.get(i).getProgData().getGlobalEquipmentDtosList().get(contadorEquipos).getAdditionalEquipmentDto().getNumEntreguese();

        StringBuilder message = new StringBuilder();
        if (orderedListMT.get(i).getProgData().getExpedientMaritim() == null) {
            appendIncidenceMessageTerrestre(
                    message,
                    incidences,
                    orderedListMT.get(i).getProgData().getRequest().getDocumentNumber(),
                    numEntreguese);
        } else {
            appendIncidenceMessageMaritimo(
                    message,
                    incidences,
                    orderedListMT.get(i).getProgData().getRequest().getDocumentNumber(),
                    orderedListMT.get(i).getProgData().getExpedientMaritim().getPortCall(),
                    orderedListMT.get(i).getProgData().getExpedientMaritim().getShipName(),
                    numEntreguese);
        }

        mail.setContent(message.toString());
        return mail;
    }

    public boolean isARepeatedUserForProgrammings(ArrayList<ProgWithIncidencesDto> progList, ProgWithIncidencesDto prog){
        boolean result = false;
        for(int i=0; i<progList.size(); i++){
            if(progList.get(i).getProgData().getCreatorUser().equals(prog.getProgData().getCreatorUser())){
                result = true;
            }
        }
        return result;
    }
}
