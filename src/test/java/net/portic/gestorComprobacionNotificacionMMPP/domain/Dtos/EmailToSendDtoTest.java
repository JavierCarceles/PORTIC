package net.portic.gestorComprobacionNotificacionMMPP.domain.Dtos;

import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.EmailToSendDto;
import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.SentEmailDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmailToSendDtoTest {

    private EmailToSendDto emailToSendDto;
    private SentEmailDto customerData;

    @BeforeEach
    public void setUp() {
        customerData = new SentEmailDto();
        emailToSendDto = new EmailToSendDto(customerData, "Test Title", "Test Matter", "Test Content");
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(customerData, emailToSendDto.getCustomerData());
        assertEquals("Test Title", emailToSendDto.getTitle());
        assertEquals("Test Matter", emailToSendDto.getMatter());
        assertEquals("Test Content", emailToSendDto.getContent());
        SentEmailDto newCustomerData = new SentEmailDto();
        emailToSendDto.setCustomerData(newCustomerData);
        assertEquals(newCustomerData, emailToSendDto.getCustomerData());
        emailToSendDto.setTitle("New Title");
        assertEquals("New Title", emailToSendDto.getTitle());
        emailToSendDto.setMatter("New Matter");
        assertEquals("New Matter", emailToSendDto.getMatter());
        emailToSendDto.setContent("New Content");
        assertEquals("New Content", emailToSendDto.getContent());
    }

    @Test
    void testNoArgsConstructor() {
        EmailToSendDto emptyEmailToSendDto = new EmailToSendDto();
        assertNull(emptyEmailToSendDto.getCustomerData());
        assertNull(emptyEmailToSendDto.getTitle());
        assertNull(emptyEmailToSendDto.getMatter());
        assertNull(emptyEmailToSendDto.getContent());
    }

    @Test
    void testAllArgsConstructor() {
        EmailToSendDto completeEmailToSendDto = new EmailToSendDto(customerData, "Complete Title", "Complete Matter", "Complete Content");

        assertEquals(customerData, completeEmailToSendDto.getCustomerData());
        assertEquals("Complete Title", completeEmailToSendDto.getTitle());
        assertEquals("Complete Matter", completeEmailToSendDto.getMatter());
        assertEquals("Complete Content", completeEmailToSendDto.getContent());
    }
}

