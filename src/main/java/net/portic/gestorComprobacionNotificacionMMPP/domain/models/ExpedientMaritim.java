package net.portic.gestorComprobacionNotificacionMMPP.domain.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "BLOCI_EXPEDIENTES")
public class ExpedientMaritim implements Serializable {

    @Id
    @Column(name="ID_EXP")
    private long idExp;

    @Column(name="ESCALA")
    private String portCall;

    @Column(name="BUQUE_NOMBRE")
    private String shipName;

    @Column(name="BUQUE_OMI")
    private String shipOmi;

}
