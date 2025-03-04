package net.portic.gestorComprobacionNotificacionMMPP.domain.Mappers;

import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.AdditionalEquipmentDto;
import net.portic.gestorComprobacionNotificacionMMPP.domain.models.AdditionalEquipment;
import net.portic.gestorComprobacionNotificacionMMPP.domain.mappers.AdditionalEquipmentMapper;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class AdditionalEquipmentMapperTest {

    private final AdditionalEquipmentMapper mapper = AdditionalEquipmentMapper.INSTANCE;

    @Test
    void testFromEntityToDto() {
        AdditionalEquipment entity = new AdditionalEquipment();
        entity.setInternalDoc(12345L);
        entity.setInternalVs(2);
        entity.setEquipmentNumber(1);
        entity.setPlate("ABC123");
        entity.setRefContainer("CONT987");
        entity.setIssuing("Issuer");
        entity.setBooking("Booking123");
        entity.setNumEntreguese("Entreguese123");
        entity.setDeposit("Deposit987");
        entity.setExpirationDate(LocalDateTime.of(2023, 12, 1, 10, 0));
        entity.setDatePrevEntr(LocalDateTime.of(2023, 11, 30, 9, 0));
        entity.setDatePrevPr(LocalDateTime.of(2023, 11, 29, 8, 0));
        entity.setState("OK");
        entity.setNumRefFile("File123");
        entity.setNumMsg("Msg123");
        entity.setFechaActualizacion(LocalDateTime.of(2023, 12, 1, 12, 0));
        entity.setProceso("PROC1");
        entity.setNumCodeco("Codeco1");
        entity.setIdEsmtMsg(54321L);

        AdditionalEquipmentDto dto = mapper.fromEntityToDto(entity);

        assertEquals(entity.getInternalDoc(), dto.getInternalDoc());
        assertEquals(entity.getInternalVs(), dto.getInternalVs());
        assertEquals(entity.getEquipmentNumber(), dto.getEquipmentNumber());
        assertEquals(entity.getPlate(), dto.getPlate());
        assertEquals(entity.getRefContainer(), dto.getRefContainer());
        assertEquals(entity.getIssuing(), dto.getIssuing());
        assertEquals(entity.getBooking(), dto.getBooking());
        assertEquals(entity.getNumEntreguese(), dto.getNumEntreguese());
        assertEquals(entity.getDeposit(), dto.getDeposit());
        assertEquals(entity.getExpirationDate(), dto.getExpirationDate());
        assertEquals(entity.getDatePrevEntr(), dto.getDatePrevEntr());
        assertEquals(entity.getDatePrevPr(), dto.getDatePrevPr());
        assertEquals(entity.getState(), dto.getState());
        assertEquals(entity.getNumRefFile(), dto.getNumRefFile());
        assertEquals(entity.getNumMsg(), dto.getNumMsg());
        assertEquals(entity.getFechaActualizacion(), dto.getFechaActualizacion());
        assertEquals(entity.getProceso(), dto.getProceso());
        assertEquals(entity.getNumCodeco(), dto.getNumCodeco());
        assertEquals(entity.getIdEsmtMsg(), dto.getIdEsmtMsg());
    }

    @Test
    void testFromDtoToEntity() {
        AdditionalEquipmentDto dto = new AdditionalEquipmentDto();
        dto.setInternalDoc(54321L);
        dto.setInternalVs(3);
        dto.setEquipmentNumber(2);
        dto.setPlate("DEF456");
        dto.setRefContainer("CONT123");
        dto.setIssuing("AnotherIssuer");
        dto.setBooking("Booking456");
        dto.setNumEntreguese("Entreguese456");
        dto.setDeposit("Deposit123");
        dto.setExpirationDate(LocalDateTime.of(2024, 1, 1, 15, 0));
        dto.setDatePrevEntr(LocalDateTime.of(2023, 12, 31, 14, 0));
        dto.setDatePrevPr(LocalDateTime.of(2023, 12, 30, 13, 0));
        dto.setState("OK2");
        dto.setNumRefFile("File456");
        dto.setNumMsg("Msg456");
        dto.setFechaActualizacion(LocalDateTime.of(2024, 1, 1, 16, 0));
        dto.setProceso("PROC2");
        dto.setNumCodeco("Codeco2");
        dto.setIdEsmtMsg(67890L);

        AdditionalEquipment entity = mapper.fromDtoToEntity(dto);

        assertEquals(dto.getInternalDoc(), entity.getInternalDoc());
        assertEquals(dto.getInternalVs(), entity.getInternalVs());
        assertEquals(dto.getEquipmentNumber(), entity.getEquipmentNumber());
        assertEquals(dto.getPlate(), entity.getPlate());
        assertEquals(dto.getRefContainer(), entity.getRefContainer());
        assertEquals(dto.getIssuing(), entity.getIssuing());
        assertEquals(dto.getBooking(), entity.getBooking());
        assertEquals(dto.getNumEntreguese(), entity.getNumEntreguese());
        assertEquals(dto.getDeposit(), entity.getDeposit());
        assertEquals(dto.getExpirationDate(), entity.getExpirationDate());
        assertEquals(dto.getDatePrevEntr(), entity.getDatePrevEntr());
        assertEquals(dto.getDatePrevPr(), entity.getDatePrevPr());
        assertEquals(dto.getState(), entity.getState());
        assertEquals(dto.getNumRefFile(), entity.getNumRefFile());
        assertEquals(dto.getNumMsg(), entity.getNumMsg());
        assertEquals(dto.getFechaActualizacion(), entity.getFechaActualizacion());
        assertEquals(dto.getProceso(), entity.getProceso());
        assertEquals(dto.getNumCodeco(), entity.getNumCodeco());
        assertEquals(dto.getIdEsmtMsg(), entity.getIdEsmtMsg());
    }
}

