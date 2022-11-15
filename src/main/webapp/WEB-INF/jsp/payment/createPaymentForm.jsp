<%@ page pageEncoding="UTF-8" contentType= "text/html; charset=UTF-8" isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title><spring:message code="msg.payment.register"/></title>
        <link rel="stylesheet" href="/css/styles.css" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css"
              rel="stylesheet"
              integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx"
              crossorigin="anonymous" />
    </head>
    <body>
        <jsp:include page="../navbar.jsp"/>
        <h2><spring:message code="msg.payment.register.title"/></h2>
        <form id="pform" action="/payments/register/" method="post">
            <input name="subscriptionId" type="hidden" value="${subscriptionId}"/>
            <div class="form-group">
                <label for="paymentTime"><spring:message code="msg.payment.time"/></label>
                <input type="datetime-local" class="form-control" id="paymentTime" name="paymentTime" placeholder="<spring:message code="msg.payment.time.specify"/>" required>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="paymentMethodDto" id="paymentMethod-radio-cash" value="CASH"/>
                <label class="form-check-label" for="paymentMethod-radio-cash">
                    <spring:message code="msg.payment.cash"/>
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="paymentMethodDto" id="paymentMethod-radio-check" value="CHECK"/>
                <label class="form-check-label" for="paymentMethod-radio-check">
                    <spring:message code="msg.payment.check"/>
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="paymentMethodDto" id="paymentMethod-radio-card" value="CREDIT_OR_DEBIT_CARD" checked/>
                <label class="form-check-label" for="paymentMethod-radio-card">
                    <spring:message code="msg.payment.credit.debit"/>
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="paymentMethodDto" id="paymentMethod-radio-online" value="ONLINE_PAYMENT_SERVICE"/>
                <label class="form-check-label" for="paymentMethod-radio-online">
                    <spring:message code="msg.payment.online"/>
                </label>
            </div>
            <button type="submit" class="btn btn-primary"><spring:message code="msg.general.save"/></button>
        </form>
    </body>
</html>