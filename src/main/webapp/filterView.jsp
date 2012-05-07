<%@page 
    contentType="text/html" 
    import="java.util.*,io.cloudsoft.jmxconsole.control.*,io.cloudsoft.jmxconsole.html.*,io.cloudsoft.jmxconsole.model.*,java.io.*" %>
<html>
<head>
    <title>JMX Console WebApp Object Index</title>
    <link rel="stylesheet" href="style_master.css" type="text/css">
    <meta http-equiv="cache-control" content="no-cache"/>
</head>

<body leftmargin="10" rightmargin="10" topmargin="10">

<table width="226" cellspacing="0" cellpadding="0" border="0">
<tr>
<td align="center" width="226"><img src="images/logo.png" border="0" width="226" alt="Brooklyn"/></td>
</tr>
</table>

&nbsp;

<table width="226" cellspacing="0" cellpadding="0" border="0" style="background-color: #E0E0E0; padding: 6px;">
<tr><td><h2>ObjectName Instances</h2></td></tr>
<tr><td> <br/> </td></tr>
<%
   Iterator mbeans = (Iterator) RequestState.getInstance(request).getServer().getDomainData("");
   int i=0;
   while( mbeans.hasNext() )
   {
      DomainData domainData = (DomainData) mbeans.next();
      out.println(" <tr>");
      out.println("  <td>");
      out.println("   <li><a href=\"HtmlAdaptor?action=displayMBeans&filter="+domainData.getDomainName()+"\" target=\"ObjectNodeView\">"+domainData.getDomainName()+"</a></li>");
      out.println("  </td>");
      out.println(" </tr>");
   }
%>
<tr><td> <br/> </td></tr>
<tr><td><i><b><a href="HtmlAdaptor?action=displayMBeans&filter=" target="ObjectNodeView">Remove Object Name Filter</a></b></i></td></tr>
</table>

</body>
</html>
