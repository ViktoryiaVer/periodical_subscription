<%@ page pageEncoding="UTF-8" contentType= "text/html; charset=UTF-8" isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title><spring:message code="msg.main.payments.detail"/></title>
        <link rel="stylesheet" href="/css/styles.css" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css"
              rel="stylesheet"
              integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx"
              crossorigin="anonymous" />
    </head>
    <body>
        <jsp:include page="../navbar.jsp"/>
        <h2 style="text-align: center"><spring:message code="msg.main.payments.detail"/></h2>
        <h4 id="message"><c:out value="${message}"/></h4>
        <table>
            <tr>
                <th><spring:message code="msg.general.id"/></th>
                <th><spring:message code="msg.general.user"/></th>
                <th><spring:message code="msg.payment.subscription.number"/></th>
                <th><spring:message code="msg.payment.time"/></th>
                <th><spring:message code="msg.payment.method"/></th>
            </tr>
            <tr>
                <td><a href="/payment/${payment.id}">${payment.id}</a></td>
                <td>
                    <a href="/user/${payment.userDto.id}">${payment.userDto.email}</a>
                </td>
                <td>
                    <a href="/subscription/${payment.subscriptionDto.id}"> ${payment.subscriptionDto.id}</a>
                </td>
                <td>
                    ${payment.paymentTime}
                </td>
                <td>
                    ${payment.paymentMethodDto.toString().toLowerCase()}
                </td>
                <td>
                    <form action="/payment/update/${payment.id}">
                        <button class="btn btn-light" type="submit" title="<spring:message code="msg.payment.update"/>"><spring:message code="msg.general.update"/></button>
                    </form>
                </td>
            </tr>
        </table>
    </body>
</html>