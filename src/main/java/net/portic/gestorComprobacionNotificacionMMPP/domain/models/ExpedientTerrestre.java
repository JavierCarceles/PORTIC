package net.portic.gestorComprobacionNotificacionMMPP.domain.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "EXPEDIENT")
public class ExpedientTerrestre implements Serializable {

    @Id
    @Column(name = "ID_EXP", nullable = false)
    private Long idExp;

    @Column(name = "VERSIO_EXP", nullable = false)
    private Integer versioExp;

    @Column(name = "TIPUS_EXP", nullable = false, length = 4)
    private String tipusExp;

    @Column(name = "ESTAT_EXP", nullable = false, length = 4)
    private String estatExp;

    @Column(name = "DATA_INICI", nullable = false)
    private LocalDateTime dataInici;

    @Column(name = "DATA_ULT_MOD")
    private LocalDateTime dataUltMod;

    @Column(name = "ID_EXP_ORIG")
    private Long idExpOrig;

    @Column(name = "USUARI", nullable = false, length = 7)
    private String usuari;

    @Column(name = "COPIA", nullable = false, length = 1)
    private String copia;
}

