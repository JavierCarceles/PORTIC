package net.portic.gestorComprobacionNotificacionMMPP.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "buzones")
public class BuzonesProperties {

    //Notification sending params //
    private String destESMTApache;


    private String portESMTApache;


    private String uriNotifyServlet;

    //Email sending params //
    private String reboteMailhost;

    //Contructor for email sending
    public BuzonesProperties(String reboteMailhost) {
        this.reboteMailhost = reboteMailhost;
    }

    @Autowired
    public BuzonesProperties(){}

    public BuzonesProperties(String destESMTApache, String portESMTApache, String uriNotifyServlet) {
        this.destESMTApache = destESMTApache;
        this.portESMTApache = portESMTApache;
        this.uriNotifyServlet = uriNotifyServlet;
    }

    public String getDestESMTApache() {
        return destESMTApache;
    }

    public String getPortESMTApache() {
        return portESMTApache;
    }

    public String getUriNotifyServlet() {
        return uriNotifyServlet;
    }

    public String getReboteMailhost() {
        return reboteMailhost;
    }

    public void setDestESMTApache(String destESMTApache) {
        this.destESMTApache = destESMTApache;
    }

    public void setPortESMTApache(String portESMTApache) {
        this.portESMTApache = portESMTApache;
    }

    public void setUriNotifyServlet(String uriNotifyServlet) {
        this.uriNotifyServlet = uriNotifyServlet;
    }

    public void setReboteMailhost(String reboteMailhost) {
        this.reboteMailhost = reboteMailhost;
    }
}

