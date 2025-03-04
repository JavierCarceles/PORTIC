package net.portic.gestorComprobacionNotificacionMMPP;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
class GestorComprobacionNotificacionMmppApplicationTests {

	@Test
	void contextLoads() {
		//ContextLoads
	}

}
