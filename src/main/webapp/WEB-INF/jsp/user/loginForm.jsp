<%@ page pageEncoding="UTF-8" contentType= "text/html; charset=UTF-8" isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title><spring:message code="msg.main.login"/></title>
        <jsp:include page="../links.jsp"/>
    </head>
    <body>
        <jsp:include page="../navbar.jsp"/>
        <main>
            <section>
                <header>
                    <h2><spring:message code="msg.main.login"/></h2>
                </header>
                <div class="message-container">
                    <h4 id="message"><c:out value="${message}"/></h4>
                    <c:if test="${param.timeout}">
                        <p style="color:red"><spring:message code="msg.error.timeout"/></p>
                    </c:if>
                    <c:if test="${param.error}">
                        <p style="color:red"><spring:message code="msg.error.login"/></p>
                    </c:if>
                    <c:if test="${param.logout}">
                        <h4 id="message"><spring:message code="msg.success.user.logged.out"/></h4>
                    </c:if>
                </div>
            </section>
            <article>
                <form id="lform" action="/login" method="post">
                    <div class="form-group">
                        <label for="username"><spring:message code="msg.general.username"/></label>
                        <input type="text" class="form-control" id="username" name="username" placeholder="<spring:message code="msg.login.enter.username"/>" required/>
                    </div>
                    <div class="form-group">
                        <label for="password"><spring:message code="msg.general.password"/></label>
                        <input type="password" class="form-control" id="password" name="password" placeholder="<spring:message code="msg.login.enter.password"/>" min="8" max="20" required/>
                    </div>
                    <button type="submit" class="btn btn-primary"><spring:message code="msg.general.submit"/></button>
                </form>
            </article>
        </main>
    </body>
</html>