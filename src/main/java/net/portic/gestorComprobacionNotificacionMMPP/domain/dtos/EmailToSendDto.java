package net.portic.gestorComprobacionNotificacionMMPP.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * objeto que guarda los datos para poder enviar un correo electrónico.
 */
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public class EmailToSendDto {

        private SentEmailDto customerData;
        private String title;
        private String matter;
        private String content;

    }
