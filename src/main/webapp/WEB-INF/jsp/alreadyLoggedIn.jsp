<%@ page pageEncoding="UTF-8" contentType= "text/html; charset=UTF-8" isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title><spring:message code="msg.main.login"/></title>
        <jsp:include page="links.jsp"/>
    </head>
    <body>
        <jsp:include page="navbar.jsp"/>
        <main>
            <header>
                <h2><spring:message code="msg.main.login.already"/></h2>
            </header>
        </main>
    </body>
</html>