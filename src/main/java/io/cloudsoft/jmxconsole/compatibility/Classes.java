package io.cloudsoft.jmxconsole.compatibility;

import java.lang.reflect.Constructor;

public class Classes {

    public static Class getPrimitiveTypeForName(String attrType) {
        // TODO copy routines from brooklyn or jboss
        return null;
    }

    public static Object convertValue(String value, String type) throws ClassNotFoundException {
        if (value==null) return null;
        
        if ("int".equals(type)) return Integer.parseInt(value);
        if ("boolean".equals(type)) return Boolean.parseBoolean(value);
        if ("long".equals(type)) return Long.parseLong(value);
        if ("java.lang.String".equals(type)) return value;

        Class clazz = Class.forName(type);
        try {
            Constructor c = clazz.getConstructor(String.class);
            return c.newInstance(value);
        } catch (Exception e) {}

        if ("null".equals(value) || value.length()==0) return null;
        if ("java.lang.Object".equals(type)) return value;
        
        throw new UnsupportedOperationException("don't know how to make "+type+" from string");
    }

}
