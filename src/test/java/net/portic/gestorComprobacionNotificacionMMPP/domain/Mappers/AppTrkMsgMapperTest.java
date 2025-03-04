package net.portic.gestorComprobacionNotificacionMMPP.domain.Mappers;
import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.AppTrkMsgDto;
import net.portic.gestorComprobacionNotificacionMMPP.domain.models.AppTrkMsg;
import net.portic.gestorComprobacionNotificacionMMPP.domain.mappers.AppTrkMsgMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppTrkMsgMapperTest {

    private final AppTrkMsgMapper mapper = AppTrkMsgMapper.INSTANCE;

    @Test
    void testFromEntityToDto() {
        AppTrkMsg entity = new AppTrkMsg();
        entity.setNumRefFile(12345L);
        entity.setNumMsg(67890L);
        entity.setProcessStatus("Completed");
        entity.setProcessStartDate(LocalDateTime.of(2023, 12, 1, 10, 0));
        entity.setIdEsmtMsg(54321L);

        AppTrkMsgDto dto = mapper.fromEntityToDto(entity);

        assertEquals(entity.getNumRefFile(), dto.getNumRefFile());
        assertEquals(entity.getNumMsg(), dto.getNumMsg());
        assertEquals(entity.getProcessStatus(), dto.getProcessStatus());
        assertEquals(entity.getProcessStartDate(), dto.getProcessStartDate());
        assertEquals(entity.getIdEsmtMsg(), dto.getIdEsmtMsg());
    }

    @Test
    void testFromDtoToEntity() {
        AppTrkMsgDto dto = new AppTrkMsgDto();
        dto.setNumRefFile(98765L);
        dto.setNumMsg(43210L);
        dto.setProcessStatus("InProgress");
        dto.setProcessStartDate(LocalDateTime.of(2023, 11, 25, 9, 0));
        dto.setIdEsmtMsg(67890L);

        AppTrkMsg entity = mapper.fromDtoToEntity(dto);

        assertEquals(dto.getNumRefFile(), entity.getNumRefFile());
        assertEquals(dto.getNumMsg(), entity.getNumMsg());
        assertEquals(dto.getProcessStatus(), entity.getProcessStatus());
        assertEquals(dto.getProcessStartDate(), entity.getProcessStartDate());
        assertEquals(dto.getIdEsmtMsg(), entity.getIdEsmtMsg());
    }
}
