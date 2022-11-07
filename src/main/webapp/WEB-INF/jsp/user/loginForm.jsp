<%@ page pageEncoding="UTF-8" contentType= "text/html; charset=UTF-8" isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title><spring:message code="msg.main.login"/></title>
        <link rel="stylesheet" href="/css/styles.css" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css"
              rel="stylesheet"
              integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx"
              crossorigin="anonymous" />
    </head>
    <body>
        <jsp:include page="../navbar.jsp"/>
        <h2><spring:message code="msg.main.login"/></h2>
        <h4 id="message"><c:out value="${message}"/></h4>
        <form id="lform" action="/login" method="post">
            <div class="form-group">
                <label for="email"><spring:message code="msg.general.email"/></label>
                <input type="email" class="form-control" id="email" name="email" placeholder="<spring:message code="msg.login.enter.email"/>" required>
            </div>
            <div class="form-group">
                <label for="password"><spring:message code="msg.general.password"/></label>
                <input type="password" class="form-control" id="password" name="password" placeholder="<spring:message code="msg.login.enter.password"/>" min="8" max="20" required>
            </div>
            <button type="submit" class="btn btn-primary"><spring:message code="msg.general.submit"/></button>
        </form>
    </body>
</html>