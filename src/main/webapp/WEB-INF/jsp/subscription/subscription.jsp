<%@ page pageEncoding="UTF-8" contentType= "text/html; charset=UTF-8" isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title><spring:message code="msg.main.subscriptions.detail"/></title>
        <jsp:include page="../links.jsp"/>
    </head>
    <body>
        <jsp:include page="../navbar.jsp"/>
        <main>
            <section>
                <header>
                    <h2><spring:message code="msg.main.subscriptions.detail"/></h2>
                </header>
                <h4 id="message"><c:out value="${message}"/></h4>
            </section>
            <article>
                <table class="table table-sm table-hover" id="subscription-table">
                    <thead>
                    <tr>
                        <th scope="col"><spring:message code="msg.general.field"/></th>
                        <th scope="col"><spring:message code="msg.general.value"/></th>
                    </tr>
                    </thead>
                    <tr>
                        <th scope="row"><spring:message code="msg.general.id"/></th>
                        <td><a href="/subscriptions/${subscription.id}">${subscription.id}</a></td>
                    </tr>
                    <tr>
                        <th scope="row"><spring:message code="msg.general.user"/></th>
                        <td>
                            <a href="/users/${subscription.userDto.id}">${subscription.userDto.email}</a>
                        </td>
                    </tr>
                    <tr>
                        <th scope="row"><spring:message code="msg.general.periodicals"/></th>
                        <td>
                            <c:forEach var="detail" items="${subscription.subscriptionDetailDtos}">
                                <a href="/periodicals/${detail.periodicalDto.id}">${detail.periodicalDto.title}</a>(${detail.periodicalCurrentPrice} <spring:message code="msg.general.usd"/> x ${detail.subscriptionDurationInYears}
                                <spring:message code="msg.subscription.years"/>)
                                <br>
                                <c:if test="${subscription.statusDto == 'PAYED' || subscription.statusDto == 'COMPLETED'}">
                                    <fmt:parseDate pattern="yyyy-MM-dd" value="${detail.subscriptionStartDate}" type="date" var="startDate"/>
                                    <fmt:parseDate pattern="yyyy-MM-dd" value="${detail.subscriptionEndDate}" type="date" var="endDate"/>
                                    <spring:message code="msg.subscription.start.date"/>: <fmt:formatDate type="date" value="${startDate}"/>
                                    <br>
                                    <spring:message code="msg.subscription.end.date"/>: <fmt:formatDate type="date" value="${endDate}"/>
                                </c:if>
                            </c:forEach>
                            <h6><spring:message code="msg.general.total.cost"/>: ${subscription.totalCost} <spring:message code="msg.general.usd"/></h6>
                        </td>
                    </tr>
                    <tr>
                        <th scope="row"><spring:message code="msg.general.status"/></th>
                        <td>${subscription.statusDto}</td>
                    </tr>
                </table>
                <ul class="list-inline">
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <c:if test="${subscription.statusDto == 'PENDING'}">
                            <li class="list-inline-item">
                                <form action="/subscriptions/update/${subscription.id}" method="post">
                                    <button class="btn btn-light" type="submit" name="statusDto" value="AWAITING_PAYMENT" title="<spring:message code="msg.subscription.confirm.title"/>"><spring:message code="msg.subscription.confirm"/></button>
                                </form>
                            </li>
                            <li class="list-inline-item">
                                <form action="/subscriptions/update/${subscription.id}" method="post">
                                    <button class="btn btn-light" type="submit" name="statusDto" value="CANCELED" title="<spring:message code="msg.subscription.reject.title"/>"><spring:message code="msg.subscription.reject"/></button>
                                </form>
                            </li>
                        </c:if>
                        <c:if test="${subscription.statusDto == 'AWAITING_PAYMENT'}">
                            <li class="list-inline-item">
                                <form action="/payments/register/${subscription.id}">
                                    <button class="btn btn-light" type="submit" title="<spring:message code="msg.payment.register"/>"><spring:message code="msg.payment.register"/></button>
                                </form>
                            </li>
                        </c:if>
                        <c:if test="${subscription.statusDto == 'PAYED'}">
                            <li class="list-inline-item">
                                <form action="/subscriptions/update/${subscription.id}" method="post">
                                    <button class="btn btn-light" type="submit" name="statusDto" value="COMPLETED"><spring:message code="msg.subscription.mark.completed"/></button>
                                </form>
                            </li>
                        </c:if>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_READER')">
                        <c:if test="${subscription.statusDto != 'CANCELED'}">
                            <li class="list-inline-item">
                                <form action="/subscriptions/update/${subscription.id}" method="post">
                                    <button class="btn btn-light" type="submit" name="statusDto" value="CANCELED" title="<spring:message code="msg.subscription.cancel.title"/>"><spring:message code="msg.subscription.cancel"/></button>
                                </form>
                            </li>
                        </c:if>
                    </sec:authorize>
                </ul>
            </article>
        </main>
    </body>
</html>