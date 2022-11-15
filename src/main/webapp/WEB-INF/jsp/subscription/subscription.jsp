<%@ page pageEncoding="UTF-8" contentType= "text/html; charset=UTF-8" isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title><spring:message code="msg.main.subscriptions.detail"/></title>
        <link rel="stylesheet" href="/css/styles.css" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css"
              rel="stylesheet"
              integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx"
              crossorigin="anonymous" />
    </head>
    <body>
        <jsp:include page="../navbar.jsp"/>
        <h2 style="text-align: center"><spring:message code="msg.main.subscriptions.detail"/></h2>
        <h4 id="message"><c:out value="${message}"/></h4>
        <table>
            <tr>
                <th><spring:message code="msg.general.id"/></th>
                <th><spring:message code="msg.general.user"/></th>
                <th><spring:message code="msg.general.periodicals"/></th>
                <th><spring:message code="msg.general.status"/></th>
            </tr>
            <tr>
                <td><a href="/subscriptions/${subscription.id}">${subscription.id}</a></td>
                <td>
                    <a href="/users/${subscription.userDto.id}">${subscription.userDto.email}</a>
                </td>
                <td>
                    <c:forEach var="detail" items="${subscription.subscriptionDetailDtos}">
                        <a href="/periodicals/${detail.periodicalDto.id}">${detail.periodicalDto.title}</a>(${detail.periodicalCurrentPrice} <spring:message code="msg.general.usd"/> x ${detail.subscriptionDurationInYears}
                        <spring:message code="msg.subscription.years"/>)
                        <br>
                            <c:if test="${subscription.statusDto == 'PAYED' || subscription.statusDto == 'COMPLETED'}">
                                <spring:message code="msg.subscription.start.date"/>: ${detail.subscriptionStartDate}
                                <br>
                                <spring:message code="msg.subscription.end.date"/>: ${detail.subscriptionEndDate}
                            </c:if>
                    </c:forEach>
                    <h6><spring:message code="msg.general.total.cost"/>: ${subscription.totalCost} <spring:message code="msg.general.usd"/></h6>
                </td>
                <td>
                    ${subscription.statusDto}
                </td>
                <c:if test="${sessionScope.user.roleDto == 'ADMIN'}">
                    <c:if test="${subscription.statusDto == 'PENDING'}">
                        <td>
                            <form action="/subscriptions/update/${subscription.id}" method="post">
                                <button class="btn btn-light" type="submit" name="statusDto" value="AWAITING_PAYMENT" title="<spring:message code="msg.subscription.confirm.title"/>"><spring:message code="msg.subscription.confirm"/></button>
                            </form>
                            <form action="/subscriptions/update/${subscription.id}" method="post">
                                <button class="btn btn-light" type="submit" name="statusDto" value="CANCELED" title="<spring:message code="msg.subscription.reject.title"/>"><spring:message code="msg.subscription.reject"/></button>
                            </form>
                        </td>
                    </c:if>
                    <c:if test="${subscription.statusDto == 'AWAITING_PAYMENT'}">
                        <td>
                            <form action="/payments/register/${subscription.id}">
                                <button class="btn btn-light" type="submit" title="<spring:message code="msg.payment.register"/>"><spring:message code="msg.payment.register"/></button>
                            </form>
                        </td>
                    </c:if>
                    <c:if test="${subscription.statusDto == 'PAYED'}">
                        <td>
                            <form action="/subscriptions/update/${subscription.id}" method="post">
                                <button class="btn btn-light" type="submit" name="statusDto" value="COMPLETED"><spring:message code="msg.subscription.mark.completed"/></button>
                            </form>
                        </td>
                    </c:if>
                </c:if>

                <c:if test="${sessionScope.user.roleDto == 'READER' && subscription.statusDto != 'CANCELED'}">
                    <td>
                        <form action="/subscriptions/update/${subscription.id}" method="post">
                            <button class="btn btn-light" type="submit" name="statusDto" value="CANCELED" title="<spring:message code="msg.subscription.cancel.title"/>"><spring:message code="msg.subscription.cancel"/></button>
                        </form>
                    </td>
                </c:if>
            </tr>
        </table>
    </body>
</html>