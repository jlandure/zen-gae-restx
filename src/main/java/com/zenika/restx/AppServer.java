package com.zenika.restx;

import com.google.common.base.Optional;
import restx.server.JettyWebServer;
import restx.server.WebServer;

public class AppServer {
    public static final String WEB_INF_LOCATION = "src/main/webapp/WEB-INF/web.xml";
    public static final String WEB_APP_LOCATION = "src/main/webapp";

    public static void main(String[] args) throws Exception {
        int port = Integer.valueOf(Optional.fromNullable(System.getenv("PORT")).or("8080"));
        WebServer server = new JettyWebServer(WEB_INF_LOCATION, WEB_APP_LOCATION, port, "0.0.0.0");

        //server = SimpleWebServer.simpleWebServerSupplier().newWebServer(port);
        /*
         * load mode from system property if defined, or default to dev
         * be careful with that setting, if you use this class to launch your server in production, make sure to launch
         * it with -Drestx.mode=prod or change the default here
         */
        System.setProperty("restx.mode", System.getProperty("restx.mode", "dev"));
        System.setProperty("restx.app.package", "com.zenika.restx");
        //System.setProperty("restx.baseUri", server.baseUrl() + "/api");

        server.startAndAwait();
    }
}