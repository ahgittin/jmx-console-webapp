package io.cloudsoft.jmxconsole.compatibility;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;

public class MBeanServerLocator {

    /*
     * system properties used in the code:
     * 
     * io.cloudsoft.jmxconsole.bind.address
     * io.cloudsoft.jmxconsole.server.name
     */
    public static MBeanServer locateMBeanServer() {
        return ManagementFactory.getPlatformMBeanServer();
    }

}
