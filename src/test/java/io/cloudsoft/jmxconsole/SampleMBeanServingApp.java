package io.cloudsoft.jmxconsole;

import java.io.IOException;
import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;

public class SampleMBeanServingApp {

    public static void main(String[] args) throws IOException {
//                -Dcom.sun.management.jmxremote.port=14144
//                -Dcom.sun.management.jmxremote.authenticate=false
//                -Dcom.sun.management.jmxremote.ssl=false
        @SuppressWarnings("unused")
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        System.in.read();
    }
    
}
