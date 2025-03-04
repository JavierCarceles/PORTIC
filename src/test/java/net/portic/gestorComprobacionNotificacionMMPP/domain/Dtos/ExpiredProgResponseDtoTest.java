package net.portic.gestorComprobacionNotificacionMMPP.domain.Dtos;

import static org.junit.jupiter.api.Assertions.*;

import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.AuditDto;
import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.ExpiredProgDto;
import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.ExpiredProgResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

class ExpiredProgResponseDtoTest {

    private ExpiredProgResponseDto expiredProgResponseDto;
    private List<ExpiredProgDto> expiredProgDtoList;
    private AuditDto auditDto;

    @BeforeEach
    public void setUp() {
        expiredProgResponseDto = new ExpiredProgResponseDto();
        expiredProgResponseDto.setCode(HttpStatus.OK);
        expiredProgResponseDto.setMessage("Success");
        expiredProgDtoList = new ArrayList<>();
        expiredProgResponseDto.setData(expiredProgDtoList);
        auditDto = new AuditDto();
        expiredProgResponseDto.setAudit(auditDto);
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(HttpStatus.OK, expiredProgResponseDto.getCode());
        assertEquals("Success", expiredProgResponseDto.getMessage());
        assertEquals(expiredProgDtoList, expiredProgResponseDto.getData());
        assertEquals(auditDto, expiredProgResponseDto.getAudit());
        expiredProgResponseDto.setCode(HttpStatus.BAD_REQUEST);
        assertEquals(HttpStatus.BAD_REQUEST, expiredProgResponseDto.getCode());
        expiredProgResponseDto.setMessage("Error");
        assertEquals("Error", expiredProgResponseDto.getMessage());
        List<ExpiredProgDto> newExpiredProgDtoList = new ArrayList<>();
        expiredProgResponseDto.setData(newExpiredProgDtoList);
        assertEquals(newExpiredProgDtoList, expiredProgResponseDto.getData());
        AuditDto newAuditDto = new AuditDto();
        expiredProgResponseDto.setAudit(newAuditDto);
        assertEquals(newAuditDto, expiredProgResponseDto.getAudit());
    }

    @Test
    void testDefaultValues() {
        ExpiredProgResponseDto defaultExpiredProgResponseDto = new ExpiredProgResponseDto();
        assertNull(defaultExpiredProgResponseDto.getCode());
        assertNull(defaultExpiredProgResponseDto.getMessage());
        assertNull(defaultExpiredProgResponseDto.getData());
        assertNull(defaultExpiredProgResponseDto.getAudit());
    }
}

