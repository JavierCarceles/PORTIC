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
public class ExpiredProgDto {

    private long id;
    private LocalDateTime expirationDate;
    private String customerName;
    private ProgrammingDto progData;
    private List<SentEmailDto> sentEmails;

}
