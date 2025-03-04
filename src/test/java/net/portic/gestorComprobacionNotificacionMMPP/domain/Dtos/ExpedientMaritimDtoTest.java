package net.portic.gestorComprobacionNotificacionMMPP.domain.Dtos;

import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.ExpedientMaritimDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExpedientMaritimDtoTest {

    private ExpedientMaritimDto expedientMaritimDto;

    @BeforeEach
    public void setUp() {
        expedientMaritimDto = new ExpedientMaritimDto();
        expedientMaritimDto.setIdExp(47788);
        expedientMaritimDto.setPortCall("18281");
        expedientMaritimDto.setShipName("VILLE DE DUBAI");
        expedientMaritimDto.setShipOmi("9153678");
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(47788, expedientMaritimDto.getIdExp());
        assertEquals("18281", expedientMaritimDto.getPortCall());
        assertEquals("VILLE DE DUBAI", expedientMaritimDto.getShipName());
        assertEquals("9153678", expedientMaritimDto.getShipOmi());
        expedientMaritimDto.setIdExp(12345);
        assertEquals(12345, expedientMaritimDto.getIdExp());
        expedientMaritimDto.setPortCall("12345");
        assertEquals("12345", expedientMaritimDto.getPortCall());
        expedientMaritimDto.setShipName("NEW SHIP");
        assertEquals("NEW SHIP", expedientMaritimDto.getShipName());
        expedientMaritimDto.setShipOmi("1234567");
        assertEquals("1234567", expedientMaritimDto.getShipOmi());
    }

    @Test
    void testDefaultValues() {
        ExpedientMaritimDto defaultExpedientMaritimDto = new ExpedientMaritimDto();
        assertEquals(0L, defaultExpedientMaritimDto.getIdExp());
        assertNull(defaultExpedientMaritimDto.getPortCall());
        assertNull(defaultExpedientMaritimDto.getShipName());
        assertNull(defaultExpedientMaritimDto.getShipOmi());
    }
}

