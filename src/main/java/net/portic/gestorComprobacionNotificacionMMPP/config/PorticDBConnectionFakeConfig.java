package net.portic.gestorComprobacionNotificacionMMPP.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class PorticDBConnectionFakeConfig {

    @Value("${spring.datasource.url}")
    private String dbHost;

    @Value("${spring.datasource.username}")
    private String dbUser;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Bean(name = "dbConnection")
    public DBConnectionH2 dbConnection() {
        return new DBConnectionH2();
    }

}
