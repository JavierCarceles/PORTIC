package net.portic.gestorComprobacionNotificacionMMPP.exceptions;

public class DBConnectionException extends Exception {
    public DBConnectionException(String msg) {
        super(msg);
    }

    public DBConnectionException() {
    }
}
