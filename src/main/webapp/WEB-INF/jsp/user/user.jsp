<%@ page pageEncoding="UTF-8" contentType= "text/html; charset=UTF-8" isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>User Detail</title>
        <link rel="stylesheet" href="/css/styles.css" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css"
              rel="stylesheet"
              integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx"
              crossorigin="anonymous" />
    </head>
    <body>
        <jsp:include page="../navbar.jsp"/>
        <h2 style="text-align: center"> User Detail</h2>
        <h4 id="message"><c:out value="${message}"/></h4>
        <table>
            <th>Id</th>
            <th>First name</th>
            <th>Last name</th>
            <th>Email</th>
            <th>Phone number</th>
            <th>Role</th>
                <tr>
                    <td>${user.id}</td>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.email}</td>
                    <td>${user.phoneNumber}</td>
                    <td>${user.roleDto.toString().toLowerCase()}</td>
                    <td>
                        <form action="/user/update/${user.id}">
                            <button class="btn btn-light" type="submit" title="Update user">Update</button>
                        </form>
                    </td>
                    <c:if test="${sessionScope.user.roleDto == 'ADMIN'}">
                        <td>
                            <form action="/user/delete/${user.id}" method="post">
                                <button class="btn btn-light" type="submit" title="Delete user">Delete</button>
                            </form>
                    </c:if>
                </tr>
                <%--<c:out value="${user.firstName} ${user.lastName}" />--%>
        </table>
    </body>
</html>