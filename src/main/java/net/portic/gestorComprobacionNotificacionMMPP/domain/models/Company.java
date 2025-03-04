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
@Table(name = "TB_EMPRESA_2")
public class Company implements Serializable {

    @Id
    @Column(name="EMPRESA_2", nullable = false)
    private long idCompany;

    @Column(name="NIF_2", nullable = false)
    private String nifCompany;

}
