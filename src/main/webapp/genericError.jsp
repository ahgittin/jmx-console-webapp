<%-- A simple error page. --%>

<%@ page isErrorPage="true" %>

<html>
    <head>
        <style>
            <!--H1 {font-family:Tahoma, Arial, sans-serif; color:white; 
                    background-color:#525D76; font-size:22px;} 
                H3 {font-family:Tahoma, Arial, sans-serif; color:white; 
                    background-color:#525D76; font-size:14px;}  
                HR {color:#525D76;} 
                .errorText {font-family:Tahoma, Arial, sans-serif; font-size:16px; } -->
        </style>
        <title>
            JMX Console Error
        </title>
    </head>
    <body>
        <h1>
        <%= pageContext.getErrorData().getStatusCode() %>
        </h1>
        <hr size=\"1\" noshade=\"noshade\">
        <span class="errorText">An error has occurred.</span>
        <hr size=\"1\" noshade=\"noshade\">
    </body>
</html>

