package net.portic.gestorComprobacionNotificacionMMPP.controllers;

import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.EntryRequestDto;
import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.ExpiredProgResponseDto;
import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.ProgWithIncidencesResponseDto;
import net.portic.gestorComprobacionNotificacionMMPP.services.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class ManagerController {

    private String messageServerErrorIncidences = "Error interno del servidor al comprobar los estados de los entrégueses.";
    private String messageServerErrorExpireds = "Error interno del servidor al comprobar las expiraciones de programaciones.";

    static Logger log = LoggerFactory.getLogger(ManagerController.class);

    @Autowired
    private ManagerService managerService;

    @PostMapping(value = "/gestionEstadoEntregueseNotificacionesApi", produces = {"application/json"})
    public ProgWithIncidencesResponseDto checkIncidencesProgrammings(@RequestBody EntryRequestDto requestData){
        ProgWithIncidencesResponseDto response = new ProgWithIncidencesResponseDto();
        log.info("requestData.dateFilter: " + requestData.getDateFilter());
        log.info("requestData.hasAudit: " + requestData.isHasAudit());
        try {
            response = managerService.checkIncidencesProgrammings(requestData);
            return response;
        }catch (Exception e){
            log.error("ManagerController.checkIncidencesProgrammings: " + e.getMessage());
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(messageServerErrorIncidences);
            response.setData(null);
            response.setAudit(null);
            return response;
        }
    }

    @PostMapping(value = "/gestionExpiracionesProgramacionesApi", produces = {"application/json"})
    public ExpiredProgResponseDto checkExpiredProgrammings(@RequestBody EntryRequestDto requestData){
        ExpiredProgResponseDto response = new ExpiredProgResponseDto ();
        log.info("requestData.dateFilter: " + requestData.getDateFilter());
        log.info("requestData.hasAudit: " + requestData.isHasAudit());
        try {
            response = managerService.checkExpiredProgrammings(requestData);
            return response;
        }catch (Exception e){
            log.error("ManagerController.checkIncidencesProgrammings: " + e.getMessage());
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(messageServerErrorExpireds);
            response.setData(null);
            response.setAudit(null);
            return response;
        }
    }

}
