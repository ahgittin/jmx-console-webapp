package io.cloudsoft.jmxconsole.html;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.management.MBeanServerConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionState {

    private HttpSession session;

    public SessionState(HttpSession session) {
        this.session = session;
    }

    public static SessionState getInstance(HttpServletRequest request) {
        HttpSession session = request.getSession();
        synchronized (session) {
            SessionState state = (SessionState) session.getAttribute("jmxconsole.session.state");
            if (state!=null) return state;
            state = new SessionState(session);
            session.setAttribute("jmxconsole.session.state", state);
            return state;
        }
    }

    public HttpSession getSession() {
        return session;
    }
    
    MBeanServerConnection lastMbs;
    
    public MBeanServerConnection getLastMBeanServer() {
        return lastMbs;
    }

    public void setLastMbeanServer(MBeanServerConnection mbs) {
        lastMbs = mbs;
    }

    Map<String, MBeanServerConnection> cachedMbs = new LinkedHashMap<String, MBeanServerConnection>();
    
    public void registerMbs(String urlS, MBeanServerConnection mbs) {
        cachedMbs.put(urlS, mbs);
    }

    public MBeanServerConnection lookupMbs(String urlS) {
        return cachedMbs.get(urlS);
    }

}
