package net.portic.gestorComprobacionNotificacionMMPP.services;

import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.EntryRequestDto;
import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.ExpiredProgResponseDto;
import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.ProgWithIncidencesResponseDto;

public interface ManagerService {

    //Método que comprueba las incidencias de las programaciones
    //(estados de los entréguses asociados a la programación)
    ProgWithIncidencesResponseDto checkIncidencesProgrammings(EntryRequestDto requestData);

    ExpiredProgResponseDto checkExpiredProgrammings(EntryRequestDto requestData);

}
