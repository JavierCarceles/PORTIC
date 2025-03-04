package net.portic.gestorComprobacionNotificacionMMPP.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExpedientMaritimDto implements Serializable {

    private long idExp;
    private String portCall;
    private String shipName;
    private String shipOmi;

}
