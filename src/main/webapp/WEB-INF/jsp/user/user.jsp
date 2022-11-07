<%@ page pageEncoding="UTF-8" contentType= "text/html; charset=UTF-8" isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>
            <c:choose>
                <c:when test="${sessionScope.user.roleDto =='READER'}">
                    <spring:message code="msg.main.my.profile"/>
                </c:when>
                <c:otherwise>
                    <spring:message code="msg.main.users.detail"/>
                </c:otherwise>
            </c:choose>
        </title>
        <link rel="stylesheet" href="/css/styles.css" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css"
              rel="stylesheet"
              integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx"
              crossorigin="anonymous" />
    </head>
    <body>
        <jsp:include page="../navbar.jsp"/>
        <h2 style="text-align: center">
            <c:choose>
                <c:when test="${sessionScope.user.roleDto =='READER'}">
                    <spring:message code="msg.main.my.profile"/>
                </c:when>
                <c:otherwise>
                    <spring:message code="msg.main.users.detail"/>
                </c:otherwise>
            </c:choose>
        </h2>
        <h4 id="message"><c:out value="${message}"/></h4>
        <table>
            <c:if test="${sessionScope.user.roleDto == 'ADMIN'}">
                <th><spring:message code="msg.general.id"/></th>
            </c:if>
            <th><spring:message code="msg.general.first.name"/></th>
            <th><spring:message code="msg.general.last.name"/></th>
            <th><spring:message code="msg.general.email"/></th>
            <th><spring:message code="msg.general.phone"/></th>
            <c:if test="${sessionScope.user.roleDto == 'ADMIN'}">
                <th><spring:message code="msg.user.role"/></th>
            </c:if>
                <tr>
                    <c:if test="${sessionScope.user.roleDto == 'ADMIN'}">
                        <td>${user.id}</td>
                    </c:if>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.email}</td>
                    <td>${user.phoneNumber}</td>
                    <c:if test="${sessionScope.user.roleDto == 'ADMIN'}">
                        <td>${user.roleDto.toString().toLowerCase()}</td>
                    </c:if>
                    <td>
                        <form action="/user/update/${user.id}">
                            <button class="btn btn-light" type="submit"><spring:message code="msg.general.update"/></button>
                        </form>
                    </td>
                    <c:if test="${sessionScope.user.roleDto == 'ADMIN'}">
                        <td>
                            <form action="/user/delete/${user.id}" method="post">
                                <button class="btn btn-light" type="submit" title="<spring:message code="msg.user.delete"/>"><spring:message code="msg.general.delete"/></button>
                            </form>
                    </c:if>
                </tr>
        </table>
    </body>
</html>