package main;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlet.SignUpServlet;
import servlet.SingInServlet;
import utils.AccountManager;

import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) throws Exception {
        AccountManager accountManager = new AccountManager();

        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.addServlet(new ServletHolder(new SingInServlet(accountManager)), "/signin");
        servletContextHandler.addServlet(new ServletHolder(new SignUpServlet(accountManager)), "/signup");

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("pages");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers( new Handler[]{resourceHandler, servletContextHandler} );

        Server server = new Server(8080);
        server.setHandler(handlers);

        server.start();
        System.out.println("Server started");
        server.join();
    }
}
