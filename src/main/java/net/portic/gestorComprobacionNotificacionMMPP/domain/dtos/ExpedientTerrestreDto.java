package net.portic.gestorComprobacionNotificacionMMPP.domain.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpedientTerrestreDto implements Serializable {

    private Long idExp;
    private Integer versioExp;
    private String tipusExp;
    private String estatExp;
    private LocalDateTime dataInici;
    private LocalDateTime dataUltMod;
    private Long idExpOrig;
    private String usuari;
    private String copia;
}
