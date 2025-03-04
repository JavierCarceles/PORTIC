package net.portic.gestorComprobacionNotificacionMMPP.domain.Dtos;

import static org.junit.jupiter.api.Assertions.*;

import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.EntryRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EntryRequestDtoTest {

    private EntryRequestDto entryRequestDto;

    @BeforeEach
    public void setUp() {
        entryRequestDto = new EntryRequestDto();
    }

    @Test
    void testGettersAndSetters() {
        entryRequestDto.setDateFilter("2024-10-03");
        entryRequestDto.setHasAudit(true);
        assertEquals("2024-10-03", entryRequestDto.getDateFilter());
        assertTrue(entryRequestDto.isHasAudit());
        entryRequestDto.setDateFilter("2024-10-04");
        entryRequestDto.setHasAudit(false);
        assertEquals("2024-10-04", entryRequestDto.getDateFilter());
        assertFalse(entryRequestDto.isHasAudit());
    }

    @Test
    void testDefaultValues() {
        assertNull(entryRequestDto.getDateFilter());
        assertFalse(entryRequestDto.isHasAudit());
    }
}

