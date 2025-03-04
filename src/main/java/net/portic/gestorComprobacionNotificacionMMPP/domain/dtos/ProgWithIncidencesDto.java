package net.portic.gestorComprobacionNotificacionMMPP.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProgWithIncidencesDto{

    private long trk;
    private String status;
    private LocalDateTime date;
    private ProgrammingDto progData;
    private List<SentEmailDto> sentEmails;

}
