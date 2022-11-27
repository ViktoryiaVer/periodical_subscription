<%@ page pageEncoding="UTF-8" contentType= "text/html; charset=UTF-8" isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="isAuthenticated()">
    <sec:authentication var="authenticatedUser" property="principal"/>
</sec:authorize>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title><spring:message code="msg.main.periodical.subscription"/> </title>
        <jsp:include page="links.jsp"/>
    </head>
    <body>
        <jsp:include page="navbar.jsp"/>
        <main>
            <section>
                <header>
                    <h2><spring:message code="msg.main.periodical.subscription"/></h2>
                </header>
                <div class="message-container">
                    <h4 id="message"><c:out value="${message}"/></h4>
                    <c:if test="${param.login}">
                        <h4 id="message"><spring:message code="msg.success.user.logged.in"/>
                        </h4>
                    </c:if>
                </div>
                <div class="greeting">
                    <p><spring:message code="msg.main.welcome"/>
                        ${authenticatedUser.firstName}
                        <sec:authorize access="isAnonymous()">
                            <spring:message code="msg.main.welcome.guest"/>
                        </sec:authorize>
                    </p>
                </div>
            </section>
            <div class="main-image">
                <img src="/images/homepage_image.jpg" alt="Homepage image" style="width:100%"/>
                <div class="container">
                    <p><spring:message code="msg.main.find.periodical"/></p>
                </div>
            </div>
        </main>
    </body>
</html>