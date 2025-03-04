package net.portic.gestorComprobacionNotificacionMMPP.domain.Dtos;

import static org.junit.jupiter.api.Assertions.*;

import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.ExpiredProgDto;
import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.ProgrammingDto;
import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.SentEmailDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class ExpiredProgDtoTest {

    private ExpiredProgDto expiredProgDto;
    private LocalDateTime now;
    private ProgrammingDto progData;
    private List<SentEmailDto> sentEmails;

    @BeforeEach
    public void setUp() {
        now = LocalDateTime.now();
        progData = new ProgrammingDto();
        sentEmails = new ArrayList<>();
        expiredProgDto = new ExpiredProgDto();
        expiredProgDto.setId(1);
        expiredProgDto.setExpirationDate(now);
        expiredProgDto.setCustomerName("John Doe");
        expiredProgDto.setProgData(progData);
        expiredProgDto.setSentEmails(sentEmails);
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(1, expiredProgDto.getId());
        assertEquals(now, expiredProgDto.getExpirationDate());
        assertEquals("John Doe", expiredProgDto.getCustomerName());
        assertEquals(progData, expiredProgDto.getProgData());
        assertEquals(sentEmails, expiredProgDto.getSentEmails());
        expiredProgDto.setId(2);
        assertEquals(2, expiredProgDto.getId());
        LocalDateTime newExpirationDate = now.plusDays(30);
        expiredProgDto.setExpirationDate(newExpirationDate);
        assertEquals(newExpirationDate, expiredProgDto.getExpirationDate());
        expiredProgDto.setCustomerName("Jane Smith");
        assertEquals("Jane Smith", expiredProgDto.getCustomerName());
        ProgrammingDto newProgData = new ProgrammingDto();
        expiredProgDto.setProgData(newProgData);
        assertEquals(newProgData, expiredProgDto.getProgData());
        List<SentEmailDto> newSentEmails = new ArrayList<>();
        expiredProgDto.setSentEmails(newSentEmails);
        assertEquals(newSentEmails, expiredProgDto.getSentEmails());
    }

    @Test
    void testDefaultValues() {
        ExpiredProgDto defaultExpiredProgDto = new ExpiredProgDto();
        assertEquals(0, defaultExpiredProgDto.getId());
        assertNull(defaultExpiredProgDto.getExpirationDate());
        assertNull(defaultExpiredProgDto.getCustomerName());
        assertNull(defaultExpiredProgDto.getProgData());
        assertNull(defaultExpiredProgDto.getSentEmails());
    }
}

