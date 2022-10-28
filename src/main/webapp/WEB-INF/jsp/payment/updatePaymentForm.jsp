<%@ page pageEncoding="UTF-8" contentType= "text/html; charset=UTF-8" isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Update payment</title>
        <link rel="stylesheet" href="/css/styles.css" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css"
              rel="stylesheet"
              integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx"
              crossorigin="anonymous" />
    </head>
    <body>
        <jsp:include page="../navbar.jsp"/>
        <h2>Update payment for Subscription ${payment.subscriptionDto.id}</h2>
        <form id="pform" action="/payment/update/" method="post">
            <input name="paymentId" type="hidden" value="${payment.id}"/>
            <div class="form-group">
                <label for="paymentTime">Payment time</label>
                <input type="datetime-local" class="form-control" id="paymentTime" name="paymentTime" value="${payment.paymentTime}" required>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="paymentMethodDto" id="paymentMethod-radio-cash" value="CASH" ${payment.paymentMethodDto == 'CASH' ? 'checked' : ''}/>
                <label class="form-check-label" for="paymentMethod-radio-cash">
                    Cash
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="paymentMethodDto" id="paymentMethod-radio-check" value="CHECK" ${payment.paymentMethodDto == 'CHECK' ? 'checked' : ''}/>
                <label class="form-check-label" for="paymentMethod-radio-check">
                    Check
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="paymentMethodDto" id="paymentMethod-radio-card" value="CREDIT_OR_DEBIT_CARD" ${payment.paymentMethodDto == 'CREDIT_OR_DEBIT_CARD' ? 'checked' : ''}/>
                <label class="form-check-label" for="paymentMethod-radio-card">
                    Credit or debit card
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="paymentMethodDto" id="paymentMethod-radio-online" value="ONLINE_PAYMENT_SERVICE" ${payment.paymentMethodDto == 'ONLINE_PAYMENT_SERVICE' ? 'checked' : ''}/>
                <label class="form-check-label" for="paymentMethod-radio-online">
                    Online payment service
                </label>
            </div>
            <button type="submit" class="btn btn-primary">Update</button>
        </form>
    </body>
</html>