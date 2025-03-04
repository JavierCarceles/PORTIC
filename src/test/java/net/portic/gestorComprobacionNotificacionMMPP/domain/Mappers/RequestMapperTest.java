package net.portic.gestorComprobacionNotificacionMMPP.domain.Mappers;

import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.RequestDto;
import net.portic.gestorComprobacionNotificacionMMPP.domain.mappers.RequestMapper;
import net.portic.gestorComprobacionNotificacionMMPP.domain.models.Request;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RequestMapperTest {

    private final RequestMapper mapper = RequestMapper.INSTANCE;

    @Test
    void shouldMapDtoToEntity() {
        RequestDto requestDto = new RequestDto();
        requestDto.setInternalDoc(1L);
        requestDto.setInternalVs(10);
        requestDto.setIdExpEntry(123L);
        requestDto.setIdExpDeparture(456L);
        requestDto.setIdExpTerrestrial(789L);
        requestDto.setStatus("OK");
        requestDto.setDate(new Date());
        requestDto.setDocumentNumber("DOC123");
        requestDto.setNumRefTrk(100L);
        requestDto.setNumMsgTrk(200L);

        Request result = mapper.fromDtoToEntity(requestDto);

        assertNotNull(result);
        assertEquals(requestDto.getInternalDoc(), result.getInternalDoc());
        assertEquals(requestDto.getInternalVs(), result.getInternalVs());
        assertEquals(requestDto.getIdExpEntry(), result.getIdExpEntry());
        assertEquals(requestDto.getIdExpDeparture(), result.getIdExpDeparture());
        assertEquals(requestDto.getIdExpTerrestrial(), result.getIdExpTerrestrial());
        assertEquals(requestDto.getStatus(), result.getStatus());
        assertEquals(requestDto.getDate(), result.getDate());
        assertEquals(requestDto.getDocumentNumber(), result.getDocumentNumber());
        assertEquals(requestDto.getNumRefTrk(), result.getNumRefTrk());
        assertEquals(requestDto.getNumMsgTrk(), result.getNumMsgTrk());
    }

    @Test
    void shouldMapEntityToDto() {
        Request request = new Request();
        request.setInternalDoc(1L);
        request.setInternalVs(10);
        request.setIdExpEntry(123L);
        request.setIdExpDeparture(456L);
        request.setIdExpTerrestrial(789L);
        request.setStatus("OK");
        request.setDate(new Date());
        request.setDocumentNumber("DOC123");
        request.setNumRefTrk(100L);
        request.setNumMsgTrk(200L);

        RequestDto result = mapper.fromEntityToDto(request);

        assertNotNull(result);
        assertEquals(request.getInternalDoc(), result.getInternalDoc());
        assertEquals(request.getInternalVs(), result.getInternalVs());
        assertEquals(request.getIdExpEntry(), result.getIdExpEntry());
        assertEquals(request.getIdExpDeparture(), result.getIdExpDeparture());
        assertEquals(request.getIdExpTerrestrial(), result.getIdExpTerrestrial());
        assertEquals(request.getStatus(), result.getStatus());
        assertEquals(request.getDate(), result.getDate());
        assertEquals(request.getDocumentNumber(), result.getDocumentNumber());
        assertEquals(request.getNumRefTrk(), result.getNumRefTrk());
        assertEquals(request.getNumMsgTrk(), result.getNumMsgTrk());
    }
}
