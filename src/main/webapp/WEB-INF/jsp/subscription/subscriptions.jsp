<%@ page pageEncoding="UTF-8" contentType= "text/html; charset=UTF-8" isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>
            <sec:authorize access="hasRole('ROLE_READER')">
                <spring:message code="msg.main.my.subscriptions"/>
            </sec:authorize>
            <sec:authorize access="hasRole('ROLE_ADMIN')">
                <spring:message code="msg.main.subscriptions.all"/>
            </sec:authorize>
        </title>
        <jsp:include page="../links.jsp"/>
    </head>
    <body>
        <jsp:include page="../navbar.jsp"/>
        <main>
            <section>
                <header>
                    <h2>
                        <sec:authorize access="hasRole('ROLE_READER')">
                            <spring:message code="msg.main.my.subscriptions"/>
                        </sec:authorize>
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                            <spring:message code="msg.main.subscriptions.all"/>
                        </sec:authorize>
                    </h2>
                </header>
                <h4 id="message"><c:out value="${message}"/></h4>
            </section>
            <section>
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <form id="search" action="/subscriptions/all">
                        <div class="input-group" style="float:right; width:30%">
                            <input type="search" class="form-control rounded" placeholder="<spring:message code="msg.subscription.search"/>" aria-label="Search" aria-describedby="search-addon" name="keyword"/>
                            <button type="submit" class="btn btn-outline-dark" ><spring:message code="msg.general.search"/></button>
                        </div>
                    </form>
                    <form id="filter-form" action="/subscriptions/all/">
                        <select id="status" class="form-select form-select-sm" aria-label=".form-select-sm example" name="status">
                            <option hidden>Select status</option>
                            <option value="PENDING" ${subscriptionFilter == 'PENDING' ? 'selected' : ''}>Pending</option>
                            <option value="AWAITING_PAYMENT" ${subscriptionFilter == 'AWAITING_PAYMENT' ? 'selected' : ''}>Awaiting Payment</option>
                            <option value="PAYED" ${subscriptionFilter == 'PAYED' ? 'selected' : ''}>Payed</option>
                            <option value="CANCELED" ${subscriptionFilter == 'CANCELED' ? 'selected' : ''}>Canceled</option>
                            <option value="COMPLETED" ${subscriptionFilter == 'COMPLETED' ? 'selected' : ''}>Completed</option>
                        </select>
                        <button type="submit" class="btn btn-light"><spring:message code="msg.general.filter"/></button>
                    </form>
                </sec:authorize>
            </section>
            <article>
                <c:if test="${subscriptions.size() > 0}">
                    <jsp:include page="../pagination.jsp"/>
                    <table class="table table-sm table-hover" id="subscriptions-table">
                        <thead>
                        <tr>
                            <th scope="col"><spring:message code="msg.general.id"/></th>
                            <th scope="col"><spring:message code="msg.general.user"/></th>
                            <th scope="col"><spring:message code="msg.general.total.cost"/></th>
                            <th scope="col"><spring:message code="msg.general.status"/></th>
                            <th scope="col"></th>
                        </tr>
                        </thead>
                        <c:forEach var="subscription" items="${subscriptions}">
                            <tr>
                                <th scope="row"><a href="/subscriptions/${subscription.id}">${subscription.id}</a></th>
                                <td>
                                    <a href="/users/${subscription.userDto.id}">${subscription.userDto.email}</a>
                                </td>
                                <td>${subscription.totalCost} <spring:message code="msg.general.usd"/></td>
                                <td>
                                        ${subscription.statusDto}
                                </td>
                                <td>
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
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:if>
            </article>
        </main>
    </body>
</html>