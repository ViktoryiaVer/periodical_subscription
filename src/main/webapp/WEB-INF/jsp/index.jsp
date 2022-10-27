<%@ page pageEncoding="UTF-8" contentType= "text/html; charset=UTF-8" isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Online periodicals subscription</title>
        <link rel="stylesheet" href="/css/styles.css" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css"
              rel="stylesheet"
              integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx"
              crossorigin="anonymous" />
    </head>
    <body>
        <jsp:include page="navbar.jsp"/>
        <h2>Online periodicals subscription</h2>
        <h4 id="message"><c:out value="${message}"/></h4>
        <p> We are glad you're here,
            <c:choose>
                <c:when test="${user != null}">
                    ${user.firstName}!
                </c:when>
                <c:otherwise>
                    dear Guest!
                </c:otherwise>
            </c:choose>
        </p>
        <img src="/images/homepage_image.jpg" alt="Homepage image" style="max-height: 500px; width:auto"/>
    </body>
</html>