
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import db.H2User;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlet.LoginServlet;
import servlet.LogoutServlet;

import servlet.MenuServlet;
import servlet.PrivatePageServlet;
import servlet.PublicPageServlet;
import servlet.UserServlet;

/**
 *
 * @author Iuri Insali 
 */
public class Runner {
    

    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(Runner.class);

    private static final int PORT = 9003;
    
      private final H2User h2User;

    

  

    private Runner() {
       
        h2User = new H2User();
    }

    private void start() throws Exception {
        Server server = new Server(PORT);

        ServletContextHandler handler = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);
        handler.setContextPath("/");
        handler.setInitParameter("org.eclipse.jetty.servlet.Default." + "resourceBase", "src/main/resources/webapp");
        
        handler.addServlet(new ServletHolder(new UserServlet(h2User)), "/register.html");
        handler.addServlet(new ServletHolder(new UserServlet(h2User)), "/add"); // we post to here

        handler.addServlet(new ServletHolder(new PublicPageServlet()), "/public");
        handler.addServlet(new ServletHolder(new PrivatePageServlet()), "/private");
        handler.addServlet(new ServletHolder(new LoginServlet(h2User)), "/login");
        handler.addServlet(new ServletHolder(new LogoutServlet()), "/logout");

        DefaultServlet ds = new DefaultServlet();
        handler.addServlet(new ServletHolder(ds), "/");

         MenuServlet menuServlet = new MenuServlet();
        handler.addServlet(new ServletHolder(menuServlet), "/menu");


        server.start();
        LOG.info("Server started, will run until terminated");
        server.join();

    }

    public static void main(String[] args) {
        try {
            LOG.info("server starting on 9003...");
            new Runner().start();
        } catch (Exception e) {
            LOG.error("Unexpected error running: " + e.getMessage());
        }
    }
}
