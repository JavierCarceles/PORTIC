package net.portic.gestorComprobacionNotificacionMMPP.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test")
public class PorticDBConnectionConfig {

    @Value("${spring.datasource.url}")
    private String dbHost;

    @Value("${spring.datasource.username}")
    private String dbUser;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Bean(name = "dbConnection")
    public DBConnectionLocalGestor dbConnection() {
        return new DBConnectionLocalGestor(getDbHost(dbHost), "1521", getDbSid(dbHost), dbUser, dbPassword);
    }
    /**
     @Bean(name = "DbConnectionReset")
     public DBConnection dbConnectionReset() {
     return new DBConnectionLocal(getDbHost(dbHost), "1521", getDbSid(dbHost), dbUser, dbPassword);
     }
     */

    private String getDbHost(String url) {
        return url.split("//")[1].split("/")[0];
    }

    private String getDbSid(String url) {
        return url.split("//")[1].split("/")[1];
    }

}
