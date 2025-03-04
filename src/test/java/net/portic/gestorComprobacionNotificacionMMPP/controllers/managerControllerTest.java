package net.portic.gestorComprobacionNotificacionMMPP.controllers;

import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.*;
import net.portic.gestorComprobacionNotificacionMMPP.services.ManagerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ManagerControllerTest {

    @Mock
    private ManagerService managerService;

    @InjectMocks
    private ManagerController managerController;

    //200-OK
    @Test
    void checkIncidencesProgrammings_01(){
        EntryRequestDto entryRequestDto = new EntryRequestDto();
        entryRequestDto.setDateFilter("2024-01-01 00:00");
        entryRequestDto.setHasAudit(false);

        SentEmailDto sentEmail = new SentEmailDto();
        sentEmail.setCustomerName("PINOJ");
        sentEmail.setCustomerNif("A08739112");
        sentEmail.setEmail("rgracia@portic.net");

        ProgrammingDto programmingDto = new ProgrammingDto();
        programmingDto.setIdProgramming(48);
        programmingDto.setStatus("AB");
        programmingDto.setInternalDoc(961);
        programmingDto.setInternalVs(1);

        ProgWithIncidencesDto progWithIncidencesDto = new ProgWithIncidencesDto();
        progWithIncidencesDto.setTrk(1087065);
        progWithIncidencesDto.setStatus("Cancelado");
        progWithIncidencesDto.setDate(LocalDateTime.parse("2013-10-18T16:46:35"));
        progWithIncidencesDto.setProgData(programmingDto);
        progWithIncidencesDto.setSentEmails(List.of(sentEmail));

        ProgWithIncidencesResponseDto responseDto = new ProgWithIncidencesResponseDto();
        responseDto.setCode(HttpStatus.OK);
        responseDto.setMessage("Comprobación de estados de entrégueses realizada con éxito.");
        responseDto.setData(Collections.singletonList(progWithIncidencesDto));
        responseDto.setAudit(null);

        when(managerService.checkIncidencesProgrammings(entryRequestDto)).thenReturn(responseDto);

        ProgWithIncidencesResponseDto result = managerController.checkIncidencesProgrammings(entryRequestDto);
        assertEquals(responseDto, result);
    }


    @Test
    void checkIncidencesProgrammings_0() {
        EntryRequestDto entryRequestDto = new EntryRequestDto();
        entryRequestDto.setDateFilter("2024-01-01 00:00");
        entryRequestDto.setHasAudit(false);

        when(managerService.checkIncidencesProgrammings(entryRequestDto))
                .thenThrow(new RuntimeException("Error interno del servidor al comprobar los estados de los entrégueses."));

        ProgWithIncidencesResponseDto response = managerController.checkIncidencesProgrammings(entryRequestDto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getCode());
        assertEquals("Error interno del servidor al comprobar los estados de los entrégueses.", response.getMessage());
    }

    @Test
    void checkIncidencesProgrammings_InvalidDateFormat() {

        EntryRequestDto entryRequestDto = new EntryRequestDto();
        entryRequestDto.setDateFilter("fecha invalida");
        entryRequestDto.setHasAudit(false);

        when(managerService.checkIncidencesProgrammings(entryRequestDto))
                .thenThrow(new DateTimeParseException("No se puede parsear el texto: fecha invalida", "fecha invalida", 0));

        ProgWithIncidencesResponseDto response = managerController.checkIncidencesProgrammings(entryRequestDto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getCode());
        assertEquals("Error interno del servidor al comprobar los estados de los entrégueses.", response.getMessage());
        assertNull(response.getData());
        assertNull(response.getAudit());
    }



    @Test
    void checkIncidencesProgrammings_NoIncidences() {
        EntryRequestDto entryRequestDto = new EntryRequestDto();
        entryRequestDto.setDateFilter("2024-01-01 00:00");
        entryRequestDto.setHasAudit(false);

        ProgWithIncidencesResponseDto responseDto = new ProgWithIncidencesResponseDto();
        responseDto.setCode(HttpStatus.OK);
        responseDto.setMessage("No se encontraron incidencias.");
        responseDto.setData(Collections.emptyList());
        responseDto.setAudit(null);

        when(managerService.checkIncidencesProgrammings(entryRequestDto)).thenReturn(responseDto);

        ProgWithIncidencesResponseDto result = managerController.checkIncidencesProgrammings(entryRequestDto);

        assertEquals(responseDto, result);
    }

    @Test
    void checkExpiredProgrammings_ValidRequest() {
        EntryRequestDto entryRequestDto = new EntryRequestDto();
        entryRequestDto.setDateFilter("2024-01-01 00:00");
        entryRequestDto.setHasAudit(false);

        ExpiredProgResponseDto responseDto = new ExpiredProgResponseDto();
        responseDto.setCode(HttpStatus.OK);
        responseDto.setMessage("Comprobación de expiraciones realizada con éxito.");
        responseDto.setData(Collections.emptyList());
        responseDto.setAudit(null);

        when(managerService.checkExpiredProgrammings(entryRequestDto)).thenReturn(responseDto);

        ExpiredProgResponseDto result = managerController.checkExpiredProgrammings(entryRequestDto);

        assertEquals(responseDto, result);
    }

    @Test
    void testCheckExpiredProgrammings_ExceptionHandling() {

        EntryRequestDto requestData = new EntryRequestDto();
        requestData.setDateFilter("2023-09-19");
        requestData.setHasAudit(true);

        doThrow(new RuntimeException("Simulated Service Exception"))
                .when(managerService)
                .checkExpiredProgrammings(any(EntryRequestDto.class));

        ExpiredProgResponseDto response = managerController.checkExpiredProgrammings(requestData);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getCode());
        assertEquals("Error interno del servidor al comprobar las expiraciones de programaciones.", response.getMessage());
        assertEquals(null, response.getData());
        assertEquals(null, response.getAudit());
    }

}


