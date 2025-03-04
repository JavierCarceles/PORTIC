package net.portic.gestorComprobacionNotificacionMMPP.config;

import net.portic.gestorComprobacionNotificacionMMPP.exceptions.DBConnectionException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

public abstract class DBConnection {
    protected Connection oCon = null;
    protected Vector vParams = new Vector();

    protected DBConnection() {
    }

    public void init(Vector vParams) {
        this.vParams = vParams;
    }

    public abstract Connection getConnection() throws DBConnectionException;

    public boolean freeConnection() throws DBConnectionException {
        boolean _return = true;
        if (this.oCon != null) {
            try {
                this.oCon.close();
                this.oCon = null;
            } catch (SQLException var3) {
                throw new DBConnectionException(var3.getMessage());
            }
        }

        return _return;
    }
}
