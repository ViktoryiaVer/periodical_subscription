<%@ page pageEncoding="UTF-8" contentType= "text/html; charset=UTF-8" isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Update user</title>
        <link rel="stylesheet" href="/css/styles.css" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css"
              rel="stylesheet"
              integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx"
              crossorigin="anonymous" />
    </head>
    <body>
    <jsp:include page="../navbar.jsp"/>
    <h2>Update user</h2>
    <c:if test="${errors != null}">
        <div style="color: red">
            <p>INVALID INPUT:</p>
            <ul>
                <c:forEach var="error" items="${errors}">
                    <li>${error.defaultMessage}</li>
                </c:forEach>
            </ul>
        </div>
    </c:if>
    <form id="sform" action="/user/update/" method="post">
        <input name="id" type="hidden" value="${user.id}"/>
        <div class="form-group">
            <label for="firstName">First name</label>
            <input type="text" class="form-control" id="firstName" name="firstName" value="${user.firstName}" pattern="^[A-Za-z-А-Яа-я]+" required>
        </div>
        <div class="form-group">
            <label for="lastName">Last name</label>
            <input type="text" class="form-control" id="lastName" name="lastName" value="${user.lastName}" pattern="^[A-Za-z-А-Яа-я]+" required>
        </div>
        <div class="form-group">
            <label for="email">Email</label>
            <input type="email" class="form-control" id="email" name="email" value="${user.email}" required>
        </div>
        <input name="password" type="hidden" value="${user.password}"/>
        <div class="form-group">
            <label for="phoneNumber">Phone number</label>
            <input type="tel" class="form-control" id="phoneNumber" name="phoneNumber" value="${user.phoneNumber}" min="10" required>
        </div>

        <c:if test="${sessionScope.user.roleDto == 'READER'}">
            <input name="roleDto" type="hidden" value="${requestScope.user.roleDto}"/>
        </c:if>

        <c:if test="${sessionScope.user.roleDto == 'ADMIN'}">
            <div class="form-check">
                <input class="form-check-input" type="radio" name="roleDto" id="role-radio-admin" value="ADMIN" ${requestScope.user.roleDto=='ADMIN' ? 'checked' : ''}/>
                <label class="form-check-label" for="role-radio-admin">
                    Admin
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="roleDto" id="role-radio-reader" value="READER" ${requestScope.user.roleDto=='READER' ? 'checked' : ''}>
                <label class="form-check-label" for="role-radio-reader">
                    Reader
                </label>
            </div>
        </c:if>

        <button type="submit" class="btn btn-primary">Update</button>
    </form>
    </body>
</html>