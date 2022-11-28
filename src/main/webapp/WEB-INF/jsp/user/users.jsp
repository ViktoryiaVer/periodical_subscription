<%@ page pageEncoding="UTF-8" contentType= "text/html; charset=UTF-8" isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title><spring:message code="msg.main.users.all"/></title>
        <jsp:include page="../links.jsp"/>
    </head>
    <body>
        <jsp:include page="../navbar.jsp"/>
        <main>
            <section>
                <header>
                    <h2><spring:message code="msg.main.users.all"/></h2>
                </header>
                <h4 id="message"><c:out value="${message}"/></h4>
            </section>
            <section>
                <form id="search" action="/users/all">
                    <div class="input-group" style="float:right; width:30%">
                        <input type="search" class="form-control rounded" placeholder="<spring:message code="msg.user.search"/>" aria-label="Search" aria-describedby="search-addon" name="keyword"/>
                        <button type="submit" class="btn btn-outline-dark" ><spring:message code="msg.general.search"/></button>
                    </div>
                </form>
            </section>
            <article>
                <c:if test="${users.size() > 0}">
                    <jsp:include page="../pagination.jsp"/>
                    <table class="table table-sm table-hover" id="users-table">
                        <thead>
                        <tr>
                            <th scope="col"><spring:message code="msg.general.id"/></th>
                            <th scope="col"><spring:message code="msg.general.username"/></th>
                            <th scope="col"><spring:message code="msg.general.email"/></th>
                            <th scope="col"></th>
                        </tr>
                        </thead>
                        <c:forEach var="user" items="${users}">
                            <tr>
                                <th scope="row">${user.id}</th>
                                <td><a href="/users/${user.id}">${user.username}</a></td>
                                <td><a href="/users/${user.id}">${user.email}</a></td>
                                <td>
                                    <ul class="list-inline">
                                        <li class="list-inline-item">
                                            <form action="/users/update/${user.id}">
                                                <button class="btn btn-light" type="submit"><spring:message code="msg.general.update"/></button>
                                            </form>
                                        </li>
                                        <li class="list-inline-item">
                                            <form action="/users/delete/${user.id}" method="post">
                                                <button class="btn btn-light" type="submit" title="<spring:message code="msg.user.delete"/>"><spring:message code="msg.general.delete"/></button>
                                            </form>
                                        </li>
                                    </ul>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:if>
            </article>
        </main>
    </body>
</html>