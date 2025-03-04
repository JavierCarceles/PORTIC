package net.portic.gestorComprobacionNotificacionMMPP.config;

import net.portic.gestorComprobacionNotificacionMMPP.config.DBConnection;
import net.portic.gestorComprobacionNotificacionMMPP.exceptions.DBConnectionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import oracle.jdbc.driver.OracleDriver;

public class DBConnectionLocal extends DBConnection {
    public DBConnectionLocal() {
    }

    public DBConnectionLocal(String DB_HOST, String DB_PORT, String DB_SID, String DB_USER, String DB_PASSWORD) {
        int i = 0;
        super.vParams.add(i++, DB_HOST);
        super.vParams.add(i++, DB_PORT);
        super.vParams.add(i++, DB_SID);
        super.vParams.add(i++, DB_USER);
        super.vParams.add(i, DB_PASSWORD);
    }

    public Connection getConnection() throws DBConnectionException {
        try {
            if (this.oCon == null) {
                String host = (String)super.vParams.get(0);
                String port = (String)super.vParams.get(1);
                String SID = (String)super.vParams.get(2);
                String user = (String)super.vParams.get(3);
                String password = (String)super.vParams.get(4);
                DriverManager.registerDriver(new OracleDriver());
                Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@//" + host + ":" + port + "/" + SID, user, password);
                this.oCon = conn;
            }
        } catch (SQLException var7) {
            throw new DBConnectionException(var7.getMessage());
        }

        return this.oCon;
    }

    public Connection getDBConnection(String DB_HOST, String DB_PORT, String DB_SID, String DB_USER, String DB_PASSWORD) throws DBConnectionException {
        try {
            int i = 0;
            super.vParams.add(i++, DB_HOST);
            super.vParams.add(i++, DB_PORT);
            super.vParams.add(i++, DB_SID);
            super.vParams.add(i++, DB_USER);
            super.vParams.add(i, DB_PASSWORD);
            return this.getConnection();
        } catch (Exception var7) {
            throw new DBConnectionException(var7.getMessage());
        }
    }

}
