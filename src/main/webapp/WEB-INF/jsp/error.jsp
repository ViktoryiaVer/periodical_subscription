<%@ page pageEncoding="UTF-8" contentType= "text/html; charset=UTF-8" isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title><spring:message code="msg.main.error"/></title>
        <jsp:include page="links.jsp"/>
    </head>
    <body>
        <main>
            <jsp:include page="navbar.jsp"/>
            <section>
                <header>
                    <h2><spring:message code="msg.main.error.occur"/></h2>
                </header>
                <div class="message-container">
                    <h4 id="message"><c:out value="${message}"/></h4>
                    <c:if test="${message == null}">
                        <h4 id="message"><spring:message code="msg.main.error.status"/>: <c:out value="${status}"/></h4>
                        <h4 id="message"><spring:message code="msg.main.error"/>: <c:out value="${error}"/></h4>
                    </c:if>
                </div>
            </section>
        </main>
    </body>
</html>