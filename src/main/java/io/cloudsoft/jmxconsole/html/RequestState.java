package io.cloudsoft.jmxconsole.html;

import io.cloudsoft.jmxconsole.control.Server;

import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestState {
    
    public static final Logger log = LoggerFactory.getLogger(RequestState.class);
    
    //URL is eg "service:jmx:rmi:///jndi/rmi://10.0.0.1:10100/jmxrmi"
    public static final String JMXCONSOLE_WEBAPP_TARGET_URL = "jmxconsole.webapp.target.url";
    
    SessionState sessionState;
    HttpServletRequest request;
    
    public RequestState(HttpServletRequest request) {
        this.request = request;
        sessionState = SessionState.getInstance(request);
    }
    
    public static RequestState getInstance(HttpServletRequest request) {
        RequestState state = (RequestState) request.getAttribute("jmxconsole.request.state");
        if (state!=null) return state;
        state = new RequestState(request);
        request.setAttribute("jmxconsole.request.state", state);
        return state;
    }
    
    public synchronized MBeanServerConnection getMBeanServerConnection() {
        MBeanServerConnection mbs = null;
        String urlS = null;
        
        //synchronize on the session to prevent concurrent creations
        synchronized (sessionState.getSession()) {
            if (urlS==null) urlS = request.getParameter(JMXCONSOLE_WEBAPP_TARGET_URL);
            if (urlS==null) urlS = (String) request.getAttribute(JMXCONSOLE_WEBAPP_TARGET_URL);
            if (urlS==null) {
                mbs = (MBeanServerConnection) request.getAttribute("jmxconsole.last.mbeanserver");
                if (useMbs(mbs)) return mbs;
            }
            if (urlS==null) {
                //see if session already has an instance
                mbs = sessionState.getLastMBeanServer();
                if (useMbs(mbs)) return mbs;
            }
            //see if there is a system property
            if (urlS==null) urlS = System.getProperty(JMXCONSOLE_WEBAPP_TARGET_URL);
            if (urlS==null) urlS = System.getenv(JMXCONSOLE_WEBAPP_TARGET_URL);

            //connect to a remote VM using JMX RMI
            if (urlS!=null) try {
                mbs = sessionState.lookupMbs(urlS);
                if (useMbs(mbs)) return mbs;

                log.info("JMX console webapp will use JMX URL "+urlS);
                JMXServiceURL url = new JMXServiceURL( urlS );
                JMXConnector jmxConnector;
                try {
                	jmxConnector = JMXConnectorFactory.connect(url);
                } catch (Exception e1) {
                	// workaround for JBoss -- https://issues.jboss.org/browse/AS7-2138
                	Map<String, String> env = new HashMap<String, String>();
                	try {
                		log.warn("unable to use JMXConnectorFactory ("+e1+"), trying workaround #1");
                		// gives same error
                		jmxConnector = JMXConnectorFactory.connect(url, env);
                	} catch (Exception e2) {
                		try {
                			log.warn("unable to use JMXConnectorFactory ("+e2+"), trying workaround #2");
                			// probably works when jboss recompiled
                			env.put(InitialContext.INITIAL_CONTEXT_FACTORY, RMIContextFactory.class.getName());
                			jmxConnector = JMXConnectorFactory.connect(url, env);
                		} catch (Exception e3) {
                			log.warn("unable to use JMXConnectorFactory ("+e3+"), out of workarounds");
                			throw new IllegalStateException("Cannot connect to RMI URL. If on JBoss you need >=7.1.2:"+e1, e1);
                		}
                	}
                }
                mbs = jmxConnector.getMBeanServerConnection();
                if (useMbs(mbs)) {
                    sessionState.registerMbs(urlS, mbs);
                    return mbs;
                }
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            log.info("JMX console webapp had no JMX URL specified, will use local JVM platform mbean server");

            mbs = ManagementFactory.getPlatformMBeanServer();
            useMbs(mbs);
            return mbs;
        }
    }
    
    private boolean useMbs(MBeanServerConnection mbs) {
        if (mbs!=null) {
            request.setAttribute("jmxconsole.last.mbeanserver", mbs);
            sessionState.setLastMbeanServer(mbs);
            return true;
        }
        return false;
    }

    public Server getServer() {
        return new Server(getMBeanServerConnection());
    }
}
