<%@ page pageEncoding="UTF-8" contentType= "text/html; charset=UTF-8" isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title><spring:message code="msg.user.update"/></title>
        <jsp:include page="../links.jsp"/>
    </head>
    <body>
        <jsp:include page="../navbar.jsp"/>
        <main>
            <section>
                <header>
                    <h2><spring:message code="msg.user.update"/></h2>
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
                <form id="uuform" action="/users/update/" method="post">
                    <input name="id" type="hidden" value="${requestScope.user.id}"/>
                    <div class="form-group">
                        <label for="firstName"><spring:message code="msg.general.first.name"/></label>
                        <input type="text" class="form-control" id="firstName" name="firstName" value="${requestScope.user.firstName}" pattern="^[A-Za-z-А-Яа-я]+" required>
                    </div>
                    <div class="form-group">
                        <label for="lastName"><spring:message code="msg.general.last.name"/></label>
                        <input type="text" class="form-control" id="lastName" name="lastName" value="${requestScope.user.lastName}" pattern="^[A-Za-z-А-Яа-я]+" required>
                    </div>
                    <div class="form-group">
                        <label for="email"><spring:message code="msg.general.email"/></label>
                        <input type="email" class="form-control" id="email" name="email" value="${requestScope.user.email}" required>
                    </div>
                    <div class="form-group">
                        <label for="username"><spring:message code="msg.general.username"/></label>
                        <input type="text" class="form-control" id="username" name="username" value="${requestScope.user.username}" required/>
                    </div>
                    <input name="password" type="hidden" value="${requestScope.user.password}"/>
                    <div class="form-group">
                        <label for="phoneNumber"><spring:message code="msg.general.phone"/></label>
                        <input type="tel" class="form-control" id="phoneNumber" name="phoneNumber" value="${requestScope.user.phoneNumber}" min="10" required>
                    </div>
                    <sec:authorize access="hasRole('ROLE_READER')">
                        <input name="roleDto" type="hidden" value="${requestScope.user.roleDto}"/>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="roleDto" id="role-radio-admin" value="ROLE_ADMIN" ${requestScope.user.roleDto=='ROLE_ADMIN' ? 'checked' : ''}/>
                            <label class="form-check-label" for="role-radio-admin">
                                <spring:message code="msg.user.admin"/>
                            </label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="roleDto" id="role-radio-reader" value="ROLE_READER" ${requestScope.user.roleDto=='ROLE_READER' ? 'checked' : ''}>
                            <label class="form-check-label" for="role-radio-reader">
                                <spring:message code="msg.user.reader"/>
                            </label>
                        </div>
                    </sec:authorize>
                    <button type="submit" class="btn btn-primary"><spring:message code="msg.general.save"/></button>
                </form>
            </article>
        </main>
    </body>
</html>