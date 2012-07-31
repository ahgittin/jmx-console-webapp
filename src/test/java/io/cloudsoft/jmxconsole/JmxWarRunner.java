package io.cloudsoft.jmxconsole;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Starts the web-app running, connected to the given management context */
public class JmxWarRunner {
    private static final Logger log = LoggerFactory.getLogger(JmxWarRunner.class);
    
    private Server server;
    private int port;
    private File war;
    
    public JmxWarRunner(File war, int port) {
        setWar(war);
        setPort(port);
    }

    public JmxWarRunner setPort(int port) {
        this.port = port;
        return this;
    }
    public JmxWarRunner setWar(File war) {
        if (!war.exists()) throw new IllegalStateException("WAR file not found at "+war);
        this.war = war;
        return this;
    }

    /** Starts the embedded web application server. */
    public void start() throws Exception {
        log.info("Starting jmx console at http://localhost:" + port+", running "+war);

        server = new Server(port);
        List<Handler> handlers = new ArrayList<Handler>();
                
        WebAppContext context = new WebAppContext();
            
        context.getSecurityHandler().setLoginService( new HashLoginService("TEST-SECURITY-REALM") );
//        context.setAttribute(BrooklynServiceAttributes.BROOKLYN_MANAGEMENT_CONTEXT, managementContext)
        context.setWar(war.getAbsolutePath());
        context.setContextPath("/");
        context.setParentLoaderPriority(true);
        handlers.add(context);
        
        HandlerList hl = new HandlerList();
        hl.setHandlers(handlers.toArray(new Handler[0]));
        server.setHandler(hl);
        
        server.start();

        log.info("Started jmx web console at http://localhost:" + port+", running "+war);
    }

    /** Asks the app server to stop and waits for it to finish up. */
    public void stop() throws Exception {
        log.info("Stopping jmx web console at http://localhost:" + port);
        server.stop();
        try { server.join(); } catch (Exception e) { /* NPE may be thrown e.g. if threadpool not started */ }
        log.info("Stopped jmx web console at http://localhost:" + port);
    }
    
    public Server getServer() {
        return server;
    }
    
    public static void main(String[] args) throws Exception {
        ManagementFactory.getPlatformMBeanServer();  //create local mbean server for test
        new JmxWarRunner(new File("/tmp/jmx-console-webapp.war"), 8200).start();
    }
    
}
