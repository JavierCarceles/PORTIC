package net.portic.gestorComprobacionNotificacionMMPP.config;

import net.portic.gestorComprobacionNotificacionMMPP.exceptions.DBConnectionException;

import oracle.jdbc.driver.OracleDriver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DBConnectionLocalGestor extends DBConnectionLocal{

    private final Logger logger = LoggerFactory.getLogger(DBConnectionLocal.class);

    public DBConnectionLocalGestor() {}

    public DBConnectionLocalGestor(String DB_HOST, String DB_PORT, String DB_SID, String DB_USER, String DB_PASSWORD) {
        super(DB_HOST,DB_PORT,DB_SID,DB_USER,DB_PASSWORD);
    }

    @Override
    public Connection getConnection() throws DBConnectionException {
        try {
            if (this.oCon == null || this.oCon.isClosed()) {

                String host = (String)super.vParams.get(0);
                String port = (String)super.vParams.get(1);
                String SID = (String)super.vParams.get(2);
                String user = (String)super.vParams.get(3);
                String password = (String)super.vParams.get(4);
                DriverManager.registerDriver(new OracleDriver());
                logger.debug(String.format("%s - Opening db connection with string: jdbc:oracle:thin:@//%s:%s/%s user: %s",
                        getClass().getName(), host, port, SID, user));
                Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@//" + host + ":" + port + "/" + SID, user, password);
                this.oCon = conn;
                if (!this.oCon.isClosed()) {
                    logger.debug(getClass().getName() + "Connection established successfully");
                }
            }
        } catch (SQLException var7) {
            logger.error(getClass().getName() + "Connection could not be open. Exception: " +var7.getMessage() );

            throw new DBConnectionException(var7.getMessage());
        }

        return this.oCon;
    }
}
