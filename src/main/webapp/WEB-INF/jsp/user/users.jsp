<%@ page pageEncoding="UTF-8" contentType= "text/html; charset=UTF-8" isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title><spring:message code="msg.main.users.all"/></title>
        <link rel="stylesheet" href="/css/styles.css" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css"
              rel="stylesheet"
              integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx"
              crossorigin="anonymous" />
    </head>
    <body>
        <jsp:include page="../navbar.jsp"/>
        <h2 style="text-align: center"><spring:message code="msg.main.users.all"/></h2>
        <h4 id="message"><c:out value="${message}"/></h4>
        <form id="search" action="/user/all">
            <div class="input-group" style="float:right; width:30%">
                <input type="search" class="form-control rounded" placeholder="<spring:message code="msg.user.search"/>" aria-label="Search" aria-describedby="search-addon" name="keyword"/>
                <button type="submit" class="btn btn-outline-dark" ><spring:message code="msg.general.search"/></button>
            </div>
        </form>
        <c:if test="${users.size() > 0}">
            <table>
                <th><spring:message code="msg.general.id"/></th>
                <th><spring:message code="msg.general.email"/></th>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td>${user.id}</td>
                        <td><a href="/user/${user.id}">${user.email}</a></td>
                    </tr>
                </c:forEach>
            </table>
            <jsp:include page="../pagination.jsp"/>
        </c:if>
    </body>
</html>