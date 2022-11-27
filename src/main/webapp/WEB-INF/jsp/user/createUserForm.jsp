<%@ page pageEncoding="UTF-8" contentType= "text/html; charset=UTF-8" isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title><spring:message code="msg.main.signup"/></title>
        <jsp:include page="../links.jsp"/>
    </head>
    <body>
        <jsp:include page="../navbar.jsp"/>
        <main>
            <section>
                <header>
                    <h2><spring:message code="msg.main.signup"/></h2>
                </header>
                <div class="message-container">
                    <c:if test="${errors != null}">
                        <div style="color: red">
                            <p><spring:message code="msg.validation.invalid.input"/>:</p>
                            <ul>
                                <c:forEach var="error" items="${errors}">
                                    <li>${error.defaultMessage}</li>
                                </c:forEach>
                            </ul>
                        </div>
                    </c:if>
                </div>
            </section>
            <article>
                <form id="sform" action="/users/create/" method="post">
                    <div class="form-group">
                        <label for="firstName"><spring:message code="msg.general.first.name"/></label>
                        <input type="text" class="form-control" id="firstName" name="firstName" placeholder="<spring:message code="msg.signup.enter.first.name"/>" pattern="^[A-Za-z-А-Яа-я]+" >
                    </div>
                    <div class="form-group">
                        <label for="lastName"><spring:message code="msg.general.last.name"/></label>
                        <input type="text" class="form-control" id="lastName" name="lastName" placeholder="<spring:message code="msg.signup.enter.last.name"/>" pattern="^[A-Za-z-А-Яа-я]+" required>
                    </div>
                    <div class="form-group">
                        <label for="email"><spring:message code="msg.general.email"/></label>
                        <input type="email" class="form-control" id="email" name="email" placeholder="<spring:message code="msg.login.enter.email"/>" required>
                    </div>
                    <div class="form-group">
                        <label for="username"><spring:message code="msg.general.username"/></label>
                        <input type="text" class="form-control" id="username" name="username" placeholder="<spring:message code="msg.login.enter.username"/>" required/>
                    </div>
                    <div class="form-group">
                        <label for="password"><spring:message code="msg.general.password"/></label>
                        <input type="password" class="form-control" id="password" name="password" placeholder="<spring:message code="msg.login.enter.password"/>" min="8" max="20" required>
                    </div>
                    <div class="form-group">
                        <label for="phoneNumber"><spring:message code="msg.general.phone"/></label>
                        <input type="tel" class="form-control" id="phoneNumber" name="phoneNumber" placeholder="<spring:message code="msg.signup.enter.phone"/>" min="10" required>
                    </div>
                    <button type="submit" class="btn btn-primary"><spring:message code="msg.general.submit"/></button>
                </form>
            </article>
        </main>
    </body>
</html>