package io.cloudsoft.jmxconsole.html;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;

public class RMIContextFactory implements InitialContextFactory {

	// workaround for JBoss -- https://issues.jboss.org/browse/AS7-2138

    @SuppressWarnings("restriction")
	public Context getInitialContext(Hashtable<?, ?> environment) throws NamingException {
        return new com.sun.jndi.url.rmi.rmiURLContext(environment);
    }

}
