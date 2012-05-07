package io.cloudsoft.jmxconsole.compatibility;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;

public class MBeanServerLocator {

    public static MBeanServer locateMBeanServer() {
        return ManagementFactory.getPlatformMBeanServer();
    }

}
