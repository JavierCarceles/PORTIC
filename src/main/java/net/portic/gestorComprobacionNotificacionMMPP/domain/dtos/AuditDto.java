package net.portic.gestorComprobacionNotificacionMMPP.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuditDto {

    @JsonProperty("id")
    private long id;

    @JsonProperty("service")
    private String service;

    @JsonProperty("date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime date;

    @JsonProperty("comprobationsNumber")
    private int numChecked;

    @JsonProperty("modificationsNumber")
    private int numModified;

    @JsonProperty("incidencesNumber")
    private int numIncidences;

    @JsonProperty("sentEmailsNumber")
    private int numSentEmails;

    @JsonProperty("auditModificationList")
    private List<AuditModificationDto> auditModificationDtoList;

}
