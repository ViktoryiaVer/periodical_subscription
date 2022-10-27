<%@ page pageEncoding="UTF-8" contentType= "text/html; charset=UTF-8" isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>All periodicals</title>
        <link rel="stylesheet" href="/css/styles.css" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css"
              rel="stylesheet"
              integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx"
              crossorigin="anonymous" />
    </head>
    <body>
        <jsp:include page="../navbar.jsp"/>
        <h2 style="text-align: center"> All periodicals</h2>
        <h4 id="message"><c:out value="${message}"/></h4>
        <c:if test="${sessionScope.user.roleDto == 'ADMIN'}">
            <td>
                <form action="/periodical/create">
                    <button class="btn btn-light" type="submit" title="Add periodical">Add</button>
                </form>
            </td>
        </c:if>
        <table>
            <th>#</th>
            <th>Title</th>
            <th>Price</th>
            <th>Category</th>
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
                <%--<c:out value="${user.firstName} ${user.lastName}" />--%>
            </c:forEach>
        </table>
    </body>
</html>