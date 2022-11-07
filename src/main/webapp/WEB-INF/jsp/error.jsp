<%@ page pageEncoding="UTF-8" contentType= "text/html; charset=UTF-8" isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title><spring:message code="msg.main.error"/></title>
        <link rel="stylesheet" href="/css/styles.css" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css"
              rel="stylesheet"
              integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx"
              crossorigin="anonymous" />
    </head>
    <body>
        <jsp:include page="navbar.jsp"/>
        <h2><spring:message code="msg.main.error.occur"/></h2>
        <h4 id="message"><c:out value="${message}"/></h4>
        <c:if test="${message == null}">
            <h4 id="message"><spring:message code="msg.main.error.status"/>: <c:out value="${status}"/></h4>
            <h4 id="message"><spring:message code="msg.main.error"/>: <c:out value="${error}"/></h4>
        </c:if>
    </body>
</html>