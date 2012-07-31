package io.cloudsoft.jmxconsole;

import java.io.IOException;
import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;

/**
 * Invoke with:
 * <pre>

-Dcom.sun.management.jmxremote.port=14144
-Dcom.sun.management.jmxremote.authenticate=false
-Dcom.sun.management.jmxremote.ssl=false

 * </pre>
 * Then contact at:  service:jmx:rmi:///jndi/rmi://localhost:14144/jmxrmi
 * 
 * @author alex
 */
public class SampleMBeanServingApp {

    public static void main(String[] args) throws IOException {
        @SuppressWarnings("unused")
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        System.in.read();
    }
    
}
