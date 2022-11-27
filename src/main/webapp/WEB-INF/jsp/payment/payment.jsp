<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm" value="${payment.paymentTime}" type="date" var="paymentTime"/>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title><spring:message code="msg.main.payments.detail"/></title>
        <jsp:include page="../links.jsp"/>
    </head>
    <body>
        <jsp:include page="../navbar.jsp"/>
        <main>
            <section>
                <header>
                    <h2><spring:message code="msg.main.payments.detail"/></h2>
                </header>
                <h4 id="message"><c:out value="${message}"/></h4>
            </section>
            <article>
                <table class="table table-sm table-hover" id="payment-table">
                    <thead>
                    <tr>
                        <th scope="col"><spring:message code="msg.general.field"/></th>
                        <th scope="col"><spring:message code="msg.general.value"/></th>
                    </tr>
                    </thead>
                    <tr>
                        <th scope="row"><spring:message code="msg.general.id"/></th>
                        <td><a href="/payments/${payment.id}">${payment.id}</a></td>
                    </tr>
                    <tr>
                        <th scope="row"><spring:message code="msg.general.user"/></th>
                        <td>
                            <a href="/users/${payment.userDto.id}">${payment.userDto.email}</a>
                        </td>
                    </tr>
                    <tr>
                        <th scope="row"><spring:message code="msg.payment.subscription.number"/></th>
                        <td>
                            <a href="/subscriptions/${payment.subscriptionDto.id}"> ${payment.subscriptionDto.id}</a>
                        </td>
                    </tr>
                    <tr>
                        <th scope="row"><spring:message code="msg.payment.time"/></th>
                        <td>
                            <fmt:formatDate type="both" value="${paymentTime}"/>
                        </td>
                    </tr>
                    <tr>
                        <th scope="row"><spring:message code="msg.payment.method"/></th>
                        <td>
                            ${payment.paymentMethodDto.toString().toLowerCase()}
                        </td>
                    </tr>
                </table>
                <ul class="list-inline">
                    <li class="list-inline-item">
                        <form action="/payments/update/${payment.id}">
                            <button class="btn btn-light" type="submit" title="<spring:message code="msg.payment.update"/>">
                                <spring:message code="msg.general.update"/></button>
                        </form>
                    </li>
                </ul>
            </article>
        </main>
    </body>
</html>
