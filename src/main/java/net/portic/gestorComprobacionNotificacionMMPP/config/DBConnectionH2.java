package net.portic.gestorComprobacionNotificacionMMPP.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import net.portic.gestorComprobacionNotificacionMMPP.exceptions.DBConnectionException;

@Configuration
@Profile("test")
public class DBConnectionH2 extends DBConnection {
    @Value("${spring.datasource.url}")
    private String connectionUrl;

    @Value("${spring.datasource.username}")
    private String user;

    @Value("${spring.datasource.password}")
    private String password;

    @Override
    public Connection getConnection() throws DBConnectionException {

        try {
            return DriverManager.getConnection(connectionUrl, user, password);

        } catch (SQLException sqlException) {
            throw new DBConnectionException(sqlException.getMessage());
        }

    }
}
