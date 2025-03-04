package net.portic.gestorComprobacionNotificacionMMPP.domain.Models;
import net.portic.gestorComprobacionNotificacionMMPP.domain.mappers.AdditionalEquipmentMapper;
import net.portic.gestorComprobacionNotificacionMMPP.domain.models.AdditionalEquipment;
import net.portic.gestorComprobacionNotificacionMMPP.domain.models.AdditionalEquipmentIds;
import net.portic.gestorComprobacionNotificacionMMPP.domain.models.Audit;
import net.portic.gestorComprobacionNotificacionMMPP.repositories.AdditionalEquipmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;


class AdditionalEquipmentTest {

    private AdditionalEquipmentRepository additionalEquipmentRepository;

    @Autowired
    private AdditionalEquipmentMapper additionalEquipmentMapper;

    @BeforeEach
    public void setUp() {
        additionalEquipmentRepository = mock(AdditionalEquipmentRepository.class);
    }

    @Test
    void testSaveAndRetrieveAdditionalEquipment() {
        // Creación de una instancia de AdditionalEquipment
        AdditionalEquipment additionalEquipment = new AdditionalEquipment();
        additionalEquipment.setInternalDoc(12345L);
        additionalEquipment.setInternalVs(1);
        additionalEquipment.setEquipmentNumber(67890);
        additionalEquipment.setPlate("ABC123");
        additionalEquipment.setRefContainer("REF789");
        additionalEquipment.setIssuing("ISSUING123");
        additionalEquipment.setBooking("BOOKING123");
        additionalEquipment.setNumEntreguese("ENTREGUESE123");
        additionalEquipment.setDeposit("DEPOSIT123");
        additionalEquipment.setExpirationDate(LocalDateTime.now().plusDays(30));
        additionalEquipment.setDatePrevEntr(LocalDateTime.now().plusDays(5));
        additionalEquipment.setDatePrevPr(LocalDateTime.now().plusDays(10));
        additionalEquipment.setState("ACT");
        additionalEquipment.setNumRefFile("FILE123");
        additionalEquipment.setNumMsg("MSG456");
        additionalEquipment.setFechaActualizacion(LocalDateTime.now());
        additionalEquipment.setProceso("PROC123");

        // Configurar el comportamiento del mock
        when(additionalEquipmentRepository.save(any())).thenReturn(additionalEquipment);
        when(additionalEquipmentRepository.findById(any())).thenReturn(Optional.of(additionalEquipment));

        // Recuperamos el objeto por su clave primaria
        AdditionalEquipment retrievedEquipment = additionalEquipmentRepository.findById(new AdditionalEquipmentIds(12345L, 1, 67890)).orElse(null);

        // Verificación de que los valores coinciden con lo esperado
        assertNotNull(retrievedEquipment);
        assertEquals("ABC123", retrievedEquipment.getPlate());
        assertEquals("REF789", retrievedEquipment.getRefContainer());
        assertEquals("ISSUING123", retrievedEquipment.getIssuing());
        assertEquals("BOOKING123", retrievedEquipment.getBooking());
        assertEquals("ENTREGUESE123", retrievedEquipment.getNumEntreguese());
        assertEquals("DEPOSIT123", retrievedEquipment.getDeposit());
        assertEquals("ACT", retrievedEquipment.getState());
        assertEquals("FILE123", retrievedEquipment.getNumRefFile());
        assertEquals("MSG456", retrievedEquipment.getNumMsg());
        assertEquals("PROC123", retrievedEquipment.getProceso());
    }
}