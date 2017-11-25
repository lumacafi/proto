package br.com.proto;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.servlet.ServletContainer;


public class WebServer {

    private static final Logger LOGGER = LogManager.getLogger();

    private static Server server;


    public static void start() {
        LOGGER.debug("Starting webserver");
        server = new Server(8080);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        ServletHolder servletHolder = new ServletHolder(new ServletContainer());
        servletHolder.setInitParameter(ServerProperties.PROVIDER_PACKAGES, "br.com.proto.handlers");
        servletHolder.setInitParameter("com.sun.jersey.api.json.POJOMappingFeature","true");
        servletHolder.setInitOrder(1);
        context.addServlet(servletHolder, "/*");
        server.setDumpAfterStart(true);

        try {
            server.start();
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }


    public static void stop() {
        server.destroy();
    }

}
