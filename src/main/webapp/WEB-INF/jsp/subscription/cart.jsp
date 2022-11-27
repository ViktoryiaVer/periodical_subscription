<%@ page pageEncoding="UTF-8" contentType= "text/html; charset=UTF-8" isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title><spring:message code="msg.main.cart"/></title>
        <jsp:include page="../links.jsp"/>
    </head>
    <body>
        <jsp:include page="../navbar.jsp"/>
        <main>
            <section>
                <header>
                    <h2><spring:message code="msg.main.cart"/></h2>
                </header>
                <div class="message-container">
                    <h4 id="message"><c:out value="${message}"/></h4>
                    <c:if test="${cart == null}">
                        <p><spring:message code="msg.cart.empty"/></p>
                    </c:if>
                </div>
            </section>
            <article>
                <c:if test="${cart != null}">
                    <table class="table table-sm table-hover" id="cart-table">
                        <thead>
                        <tr>
                            <th scope="col"><spring:message code="msg.general.periodicals"/></th>
                            <th scope="col"><spring:message code="msg.general.price"/></th>
                            <th scope="col"><spring:message code="msg.cart.duration"/></th>
                            <th scope="col"></th>
                        </tr>
                        </thead>
                        <c:forEach var="detail" items="${cart.subscriptionDetailDtos}">
                            <tr>
                                <td><a href="/periodicals/${detail.periodicalDto.id}">${detail.periodicalDto.title}</a></td>
                                <td>${detail.periodicalCurrentPrice} <spring:message code="msg.general.usd"/></td>
                                <td> x ${detail.subscriptionDurationInYears} <spring:message code="msg.subscription.years"/></td>
                                <td>
                                    <form action="/cart/delete/${detail.periodicalDto.id}" method="post">
                                        <button class="btn btn-secondary" type="submit" title="<spring:message code="msg.general.delete"/>"><spring:message code="msg.general.delete"/></button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td colspan="4"><h6><spring:message code="msg.general.total.cost"/>: ${cart.totalCost} <spring:message code="msg.general.usd"/></h6> </td>
                        </tr>
                    </table>
                    <ul class="list-inline">
                        <li class="list-inline-item">
                            <form action="/subscriptions/create" method="post">
                                <button type="submit" class="btn btn-primary"><spring:message code="msg.cart.complete"/></button>
                            </form>
                        </li>
                        <li class="list-inline-item">
                            <form action="/cart/delete/all" method="post">
                                <button class="btn btn-secondary" type="submit" title="<spring:message code="msg.cart.clear"/>"><spring:message code="msg.cart.clear"/></button>
                            </form>
                        </li>
                    </ul>
                </c:if>
            </article>
        </main>
    </body>
</html>