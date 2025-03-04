package net.portic.gestorComprobacionNotificacionMMPP.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProgWithIncidencesResponseDto {

    private HttpStatus code;
    private String message;
    private List<ProgWithIncidencesDto> data;
    private AuditDto audit;

}
