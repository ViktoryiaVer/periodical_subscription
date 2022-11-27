<%@ page pageEncoding="UTF-8" contentType= "text/html; charset=UTF-8" isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>
            <sec:authorize access="hasRole('ROLE_READER')">
                <spring:message code="msg.main.my.profile"/>
            </sec:authorize>
            <sec:authorize access="hasRole('ROLE_ADMIN')">
                <spring:message code="msg.main.users.detail"/>
            </sec:authorize>
        </title>
        <jsp:include page="../links.jsp"/>
    </head>
    <body>
        <jsp:include page="../navbar.jsp"/>
        <main>
            <section>
                <header>
                    <h2>
                        <sec:authorize access="hasRole('ROLE_READER')">
                            <spring:message code="msg.main.my.profile"/>
                        </sec:authorize>
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                            <spring:message code="msg.main.users.detail"/>
                        </sec:authorize>
                    </h2>
                </header>
                <h4 id="message"><c:out value="${message}"/></h4>
            </section>
            <article>
                <table class="table table-sm table-hover" id="user-table">
                    <thead>
                    <tr>
                        <th scope="col"><spring:message code="msg.general.field"/></th>
                        <th scope="col"><spring:message code="msg.general.value"/></th>
                    </tr>
                    </thead>
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <tr>
                            <th scope="row"><spring:message code="msg.general.id"/></th>
                            <td>${user.id}</td>
                        </tr>
                    </sec:authorize>
                    <tr>
                        <th scope="row"><spring:message code="msg.general.first.name"/></th>
                        <td>${user.firstName}</td>
                    </tr>
                    <tr>
                        <th scope="row"><spring:message code="msg.general.last.name"/></th>
                        <td>${user.lastName}</td>
                    </tr>
                    <tr>
                        <th scope="row"><spring:message code="msg.general.email"/></th>
                        <td>${user.email}</td>
                    </tr>
                    <tr>
                        <th scope="row"><spring:message code="msg.general.username"/></th>
                        <td>${user.username}</td>
                    </tr>
                    <tr>
                        <th scope="row"><spring:message code="msg.general.phone"/></th>
                        <td>${user.phoneNumber}</td>
                    </tr>
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <tr>
                            <th scope="row"><spring:message code="msg.user.role"/></th>
                            <td>${user.roleDto.toString().toLowerCase()}</td>
                        </tr>
                    </sec:authorize>
                </table>
                <ul class="list-inline">
                    <li class="list-inline-item">
                        <form action="/users/update/${user.id}">
                            <button class="btn btn-light" type="submit"><spring:message code="msg.general.update"/></button>
                        </form>
                    </li>
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <li class="list-inline-item">
                            <form action="/users/delete/${user.id}" method="post">
                                <button class="btn btn-light" type="submit" title="<spring:message code="msg.user.delete"/>"><spring:message code="msg.general.delete"/></button>
                            </form>
                        </li>
                    </sec:authorize>
                </ul>
            </article>
        </main>
    </body>
</html>