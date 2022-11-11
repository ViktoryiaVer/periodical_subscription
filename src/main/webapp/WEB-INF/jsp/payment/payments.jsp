<%@ page pageEncoding="UTF-8" contentType= "text/html; charset=UTF-8" isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title><spring:message code="msg.main.payments.all"/></title>
        <link rel="stylesheet" href="/css/styles.css" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css"
              rel="stylesheet"
              integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx"
              crossorigin="anonymous" />
    </head>
    <body>
        <jsp:include page="../navbar.jsp"/>
        <h2 style="text-align: center"><spring:message code="msg.main.payments.all"/></h2>
        <h4 id="message"><c:out value="${message}"/></h4>
        <form id="search" action="/payment/all">
            <div class="input-group" style="float:right; width:30%">
                <input type="search" class="form-control rounded" placeholder="<spring:message code="msg.payment.search"/>" aria-label="Search" aria-describedby="search-addon" name="keyword"/>
                <button type="submit" class="btn btn-outline-dark" ><spring:message code="msg.general.search"/></button>
            </div>
        </form>
        <form id="filter-form" action="/payment/all">
            <select id="paymentMethod" class="form-select form-select-sm" aria-label=".form-select-sm example" name="paymentMethod">
                <option hidden disabled selected value><spring:message code="msg.payment.select.payment.method"/></option>
                <option value="CASH" ${paymentFilter.paymentMethod == 'CASH' ? 'selected' : ''}><spring:message code="msg.payment.cash"/></option>
                <option value="CHECK" ${paymentFilter.paymentMethod == 'CHECK' ? 'selected' : ''}><spring:message code="msg.payment.check"/></option>
                <option value="CREDIT_OR_DEBIT_CARD" ${paymentFilter.paymentMethod == 'CREDIT_OR_DEBIT_CARD' ? 'selected' : ''}><spring:message code="msg.payment.credit.debit"/></option>
                <option value="ONLINE_PAYMENT_SERVICE" ${paymentFilter.paymentMethod == 'ONLINE_PAYMENT_SERVICE' ? 'selected' : ''}><spring:message code="msg.payment.online"/></option>
            </select>
            <div class="form-group">
                <label for="paymentDate"><spring:message code="msg.payment.date.specify"/></label>
                <input type="date" class="form-control" id="paymentDate" name="paymentDate"/>
            </div>
            <button type="submit" class="btn btn-light"><spring:message code="msg.general.filter"/></button>
        </form>
        <c:if test="${payments.size() > 0}">
            <table>
                <th><spring:message code="msg.general.id"/></th>
                <th><spring:message code="msg.general.user"/></th>
                <th><spring:message code="msg.payment.subscription.number"/></th>
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
                                <button class="btn btn-light" type="submit" title="<spring:message code="msg.payment.update"/>"><spring:message code="msg.general.update"/></button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <jsp:include page="../pagination.jsp"/>
        </c:if>
    </body>
</html>