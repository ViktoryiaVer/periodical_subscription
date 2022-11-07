<%@ page pageEncoding="UTF-8" contentType= "text/html; charset=UTF-8" isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title><spring:message code="msg.main.periodicals"/></title>
        <link rel="stylesheet" href="/css/styles.css" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css"
              rel="stylesheet"
              integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx"
              crossorigin="anonymous" />
    </head>
    <body>
        <jsp:include page="../navbar.jsp"/>
        <h2 style="text-align: center"><spring:message code="msg.main.periodicals"/></h2>
        <h4 id="message"><c:out value="${message}"/></h4>
        <c:if test="${sessionScope.user.roleDto == 'ADMIN'}">
            <form action="/periodical/create">
                <button class="btn btn-light" type="submit" title="<spring:message code="msg.periodical.add.title"/>"><spring:message code="msg.periodical.add"/></button>
            </form>
        </c:if>
        <c:if test="${periodicals.size() > 0}">
            <table>
                <tr>
                    <th>#</th>
                    <th><spring:message code="msg.periodical.title"/></th>
                    <th><spring:message code="msg.periodical.price"/></th>
                    <th><spring:message code="msg.periodical.category"/></th>
                </tr>
                <c:forEach var="periodical" items="${periodicals}" varStatus="counter">
                    <tr>
                        <td>${counter.count}</td>
                        <td><a href="/periodical/${periodical.id}">${periodical.title}</a></td>
                        <td>${periodical.price}</td>
                        <td>
                            <c:forEach var="category" items="${periodical.categoryDtos}">
                                ${category.categoryDto.toString().toLowerCase()}
                            </c:forEach>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <jsp:include page="../pagination.jsp"/>
        </c:if>
    </body>
</html>