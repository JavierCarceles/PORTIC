package net.portic.gestorComprobacionNotificacionMMPP.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *  objeto que guarda los campos de los correos electrónicos enviados
 *  para las programaciones con incidencias y/o expiradas.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SentEmailDto {

    private String customerName;
    private String customerNif;
    private String email;

}
