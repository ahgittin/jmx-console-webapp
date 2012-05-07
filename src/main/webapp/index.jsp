<?xml version="1.0"?>
<%@page contentType="text/html"
   import="java.net.*,java.util.*,io.cloudsoft.jmxconsole.model.*,java.io.*"
%>
<!DOCTYPE html 
    PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
      String bindAddress = "";
      String serverName = "";
      try
      {
         bindAddress = System.getProperty("io.cloudsoft.jmxconsole.bind.address", "");
         serverName = System.getProperty("io.cloudsoft.jmxconsole.server.name", "");
      }
      catch (SecurityException se) {}

      String hostname = "";
      try
      {
         hostname = InetAddress.getLocalHost().getHostName();
      }
      catch(IOException e)  {}

      String hostInfo = hostname;
      if (!bindAddress.equals(""))
      {
         hostInfo = hostInfo + " (" + bindAddress + ")";
      }
   %>
<html>
<head>
<title>JMX Console WebApp - <%= hostInfo %></title>
</head>
<!-- frames -->
<frameset  cols="295,*">
    <frame name="ObjectFilterView" src="filterView.jsp"                   marginwidth="10" marginheight="10" scrolling="auto" frameborder="0">
    <frame name="ObjectNodeView"   src="HtmlAdaptor?action=displayMBeans" marginwidth="10"  marginheight="10" scrolling="auto" frameborder="0">
    <noframes>A frames enabled browser is required for the main view</noframes>
</frameset>
</html>
