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
                    <spring:message code="msg.main.my.subscriptions"/>
                </c:when>
                <c:otherwise>
                    <spring:message code="msg.main.subscriptions.all"/>
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
                    <spring:message code="msg.main.my.subscriptions"/>
                </c:when>
                <c:otherwise>
                    <spring:message code="msg.main.subscriptions.all"/>
                </c:otherwise>
            </c:choose>
        </h2>
        <h4 id="message"><c:out value="${message}"/></h4>
        <c:if test="${subscriptions.size() > 0}">
            <table>
                <th><spring:message code="msg.general.id"/></th>
                <th><spring:message code="msg.general.user"/></th>
                <th><spring:message code="msg.general.total.cost"/></th>
                <th><spring:message code="msg.general.status"/></th>
                <c:forEach var="subscription" items="${subscriptions}">
                    <tr>
                        <td><a href="/subscription/${subscription.id}">${subscription.id}</a></td>
                        <td>
                            <a href="/user/${subscription.userDto.id}">${subscription.userDto.email}</a>
                        </td>
                        <td>${subscription.totalCost}</td>
                        <td>
                                ${subscription.statusDto}
                        </td>
                        <c:if test="${sessionScope.user.roleDto =='ADMIN'}">
                            <c:if test="${subscription.statusDto == 'PENDING'}">
                                <td>
                                    <form action="/subscription/update/${subscription.id}" method="post">
                                        <button class="btn btn-light" type="submit" name="statusDto" value="AWAITING_PAYMENT" title="<spring:message code="msg.subscription.confirm.title"/>"><spring:message code="msg.subscription.confirm"/></button>
                                    </form>
                                    <form action="/subscription/update/${subscription.id}" method="post">
                                        <button class="btn btn-light" type="submit" name="statusDto" value="CANCELED" title="<spring:message code="msg.subscription.reject.title"/>"><spring:message code="msg.subscription.reject"/></button>
                                    </form>
                                </td>
                            </c:if>
                            <c:if test="${subscription.statusDto == 'AWAITING_PAYMENT'}">
                                <td>
                                    <form action="/payment/register/${subscription.id}">
                                        <button class="btn btn-light" type="submit" title="<spring:message code="msg.payment.register"/>"><spring:message code="msg.payment.register"/></button>
                                    </form>
                                </td>
                            </c:if>
                        </c:if>

                        <c:if test="${sessionScope.user.roleDto == 'READER' && subscription.statusDto != 'CANCELED'}">
                            <td>
                                <form action="/subscription/update/${subscription.id}" method="post">
                                    <button class="btn btn-light" type="submit" name="statusDto" value="CANCELED" title="<spring:message code="msg.subscription.cancel.title"/>"><spring:message code="msg.subscription.cancel"/></button>
                                </form>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </table>
            <jsp:include page="../pagination.jsp"/>
        </c:if>
    </body>
</html>