<%@ page pageEncoding="UTF-8" contentType= "text/html; charset=UTF-8" isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>All payments</title>
        <link rel="stylesheet" href="/css/styles.css" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css"
              rel="stylesheet"
              integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx"
              crossorigin="anonymous" />
    </head>
    <body>
        <jsp:include page="../navbar.jsp"/>
        <h2 style="text-align: center"> All payments</h2>
        <h4 id="message"><c:out value="${message}"/></h4>
        <c:if test="${payments.size() > 0}">
            <table>
                <th>Id</th>
                <th>User</th>
                <th>Subscription number</th>
                <c:forEach var="payment" items="${payments}">
                    <tr>
                        <td><a href="/payment/${payment.id}">${payment.id}</a></td>
                        <td>
                            <a href="/user/${payment.userDto.id}">${payment.userDto.email}</a>
                        </td>
                        <td>
                            <a href="/subscription/${payment.subscriptionDto.id}"> ${payment.subscriptionDto.id}</a>
                        </td>
                        <td>
                            <form action="/payment/update/${payment.id}">
                                <button class="btn btn-light" type="submit" title="Update payment">Update</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </body>
</html>