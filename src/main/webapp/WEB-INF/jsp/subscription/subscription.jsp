<%@ page pageEncoding="UTF-8" contentType= "text/html; charset=UTF-8" isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Subscription Detail</title>
        <link rel="stylesheet" href="/css/styles.css" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css"
              rel="stylesheet"
              integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx"
              crossorigin="anonymous" />
    </head>
    <body>
        <jsp:include page="../navbar.jsp"/>
        <h2 style="text-align: center"> Subscription Detail</h2>
        <h4 id="message"><c:out value="${message}"/></h4>
        <table>
            <th>Id</th>
            <th>User</th>
            <th>Periodicals</th>
            <th>Status</th>
                <tr>
                    <td><a href="/subscription/${subscription.id}">${subscription.id}</a></td>
                    <td>
                        <a href="/user/${subscription.userDto.id}">${subscription.userDto.email}</a>
                    </td>
                    <td>
                        <c:forEach var="detail" items="${subscription.subscriptionDetailDtos}">
                            <a href="/periodical/${detail.periodicalDto.id}">${detail.periodicalDto.title}</a>(${detail.periodicalCurrentPrice} USD for ${detail.subscriptionDurationInYears} years)
                            <br>
                                <c:if test="${subscription.statusDto == 'PAYED'}">
                                    Subscription start date: ${detail.subscriptionStartDate}
                                    <br>
                                    Subscription end date: ${detail.subscriptionEndDate}
                                </c:if>
                        </c:forEach>
                        <h6>TOTAL COST: ${subscription.totalCost} USD</h6>
                    </td>
                    <td>
                        ${subscription.statusDto}
                    </td>
                </tr>
                <%--<c:out value="${user.firstName} ${user.lastName}" />--%>
        </table>
    </body>
</html>