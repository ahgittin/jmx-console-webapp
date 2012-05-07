package io.cloudsoft.jmxconsole.compatibility;

/** for legacy compatibility */
public class Logger {

    private org.slf4j.Logger logger;

    public Logger(org.slf4j.Logger logger) {
        this.logger = logger;
    }

    public static Logger getLogger(Class<?> clazz) {
        return new Logger(org.slf4j.LoggerFactory.getLogger(clazz));
    }

    public void trace(String msg) {
        logger.trace(msg);
    }

}
