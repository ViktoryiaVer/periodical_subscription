<%@ page pageEncoding="UTF-8" contentType= "text/html; charset=UTF-8" isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title><spring:message code="msg.main.cart"/></title>
        <link rel="stylesheet" href="/css/styles.css" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css"
              rel="stylesheet"
              integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx"
              crossorigin="anonymous" />
    </head>
    <body>
        <jsp:include page="../navbar.jsp"/>
        <h2 style="text-align: center"><spring:message code="msg.main.cart"/></h2>
        <h4 id="message"><c:out value="${message}"/></h4>
        <c:if test="${cart == null}">
            <p><spring:message code="msg.cart.empty"/></p>
        </c:if>
        <c:if test="${cart != null}">
            <table>
                <tr>
                    <th><spring:message code="msg.general.periodicals"/></th>
                    <th><spring:message code="msg.general.price"/></th>
                    <th><spring:message code="msg.cart.duration"/></th>
                    <th></th>
                </tr>
                <c:forEach var="detail" items="${cart.subscriptionDetailDtos}">
                    <tr>
                        <td><a href="/periodical/${detail.periodicalDto.id}">${detail.periodicalDto.title}</a></td>
                        <td>${detail.periodicalCurrentPrice} <spring:message code="msg.general.usd"/></td>
                        <td> x ${detail.subscriptionDurationInYears} <spring:message code="msg.subscription.years"/></td>
                        <td>
                            <form action="/cart/delete/${detail.periodicalDto.id}" method="post">
                                <button class="btn btn-danger btn-sm rounded-0" type="submit" title="<spring:message code="msg.general.delete"/>"><spring:message code="msg.general.delete"/></button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="3"><h6><spring:message code="msg.general.total.cost"/>: ${cart.totalCost} <spring:message code="msg.general.usd"/></h6> </td>
                    <td colspan="2">
                        <form action="/cart/delete/all" method="post">
                            <button class="btn btn-danger btn-sm rounded-0" type="submit" title="<spring:message code="msg.cart.clear"/>"><spring:message code="msg.cart.clear"/></button>
                        </form>
                    </td>
                </tr>
            </table>
            <form action="/subscription/create" method="post">
                <button type="submit" class="btn btn-primary"><spring:message code="msg.cart.complete"/></button>
            </form>
        </c:if>
    </body>
</html>