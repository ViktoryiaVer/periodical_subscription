<%@ page pageEncoding="UTF-8" contentType= "text/html; charset=UTF-8" isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:parseDate pattern="yyyy-MM-dd" value="${periodical.publicationDate}" type="date" var="publicationDate"/>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title><spring:message code="msg.main.periodicals.detail"/></title>
        <jsp:include page="../links.jsp"/>
    </head>
    <body>
        <jsp:include page="../navbar.jsp"/>
        <main>
            <section>
                <header>
                    <h2><spring:message code="msg.main.periodicals.detail"/></h2>
                </header>
                <h4 id="message"><c:out value="${message}"/></h4>
            </section>
            <article>
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <ul class="list-inline">
                        <li class="list-inline-item">
                            <form action="/periodicals/update/${periodical.id}">
                                <button class="btn btn-light" type="submit" title="<spring:message code="msg.periodical.update"/>"><spring:message code="msg.general.update"/></button>
                            </form>
                        </li>
                        <li class="list-inline-item">
                            <form action="/periodicals/delete/${periodical.id}" method="post">
                                <button class="btn btn-light" type="submit" title="<spring:message code="msg.periodical.delete"/>"><spring:message code="msg.general.delete"/></button>
                            </form>
                        </li>
                        <c:if test="${periodical.statusDto == 'AVAILABLE'}">
                            <li class="list-inline-item">
                                <form action="/periodicals/update/${periodical.id}/status" method="post">
                                    <button class="btn btn-light" type="submit" title="<spring:message code="msg.periodical.mark.unavailable.title"/>" name="statusDto" value="UNAVAILABLE"><spring:message code="msg.periodical.mark.unavailable"/></button>
                                </form>
                            </li>
                        </c:if>
                        <c:if test="${periodical.statusDto == 'UNAVAILABLE'}">
                            <li class="list-inline-item">
                                <form action="/periodicals/update/${periodical.id}/status" method="post">
                                    <button class="btn btn-light" type="submit" name="statusDto" value="AVAILABLE"><spring:message code="msg.periodical.mark.available"/></button>
                                </form>
                            </li>
                        </c:if>
                    </ul>
                </sec:authorize>
                <table class="table table-sm">
                    <tr>
                        <td style="width:30%">
                            <table>
                                <tr>
                                    <td>
                                        <img src="/images/periodicals/${periodical.imagePath}" alt="Periodical ${periodical.id}" style="height:400px;width:auto"/></td>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <form id="subscribeForm" action="/cart/add/${periodical.id}" method="post">
                                            <p id="selectDuration"></p>
                                            <div class="form-check" hidden>
                                                <input class="form-check-input" type="radio" name="subscriptionDurationInYears" id="type-radio-1year" value="1" checked/>
                                                <label class="form-check-label" for="type-radio-1year">
                                                    1 <spring:message code="msg.subscription.years"/>
                                                </label>
                                            </div>
                                            <div class="form-check" hidden>
                                                <input class="form-check-input" type="radio" name="subscriptionDurationInYears" id="type-radio-2years" value="2"/>
                                                <label class="form-check-label" for="type-radio-2years">
                                                    2 <spring:message code="msg.subscription.years"/>
                                                </label>
                                            </div>
                                            <div class="form-check" hidden>
                                                <input class="form-check-input" type="radio" name="subscriptionDurationInYears" id="type-radio-3years" value="3"/>
                                                <label class="form-check-label" for="type-radio-3years">
                                                    3 <spring:message code="msg.subscription.years"/>
                                                </label>
                                            </div>
                                            <button id="subscribeButton" type="button" class="btn btn-light" onclick="showDurationInYearsToSelect()"><spring:message code="msg.periodical.subscribe"/></button>
                                            <button id="confirmButton" type="submit" hidden class="btn btn-primary"><spring:message code="msg.subscription.confirm"/></button>
                                        </form>
                                    </td>
                                </tr>
                            </table>
                        <td style="width:70%">
                            <table class="table table-sm">
                                <tr>
                                    <th scope="row"><spring:message code="msg.periodical.title"/></th>
                                </tr>
                                <tr>
                                    <td>${periodical.title}</td>
                                </tr>
                                <tr>
                                    <th scope="row"><spring:message code="msg.periodical.publisher"/></th>
                                </tr>
                                <tr>
                                    <td>${periodical.publisher}</td>
                                </tr>
                                <tr>
                                    <th scope="row"><spring:message code="msg.periodical.description"/></th>
                                </tr>
                                <tr>
                                    <td>${periodical.description}</td>
                                </tr>
                                <tr>
                                    <th scope="row"><spring:message code="msg.periodical.publication.date"/></th>
                                </tr>
                                <tr>
                                    <td><fmt:formatDate type="date" value="${publicationDate}"/></td>
                                </tr>
                                <tr>
                                    <th scope="row"><spring:message code="msg.periodical.issues.year"/></th>
                                </tr>
                                <tr>
                                    <td>${periodical.issuesAmountInYear}</td>
                                </tr>
                                <tr>
                                    <th scope="row"><spring:message code="msg.periodical.price"/></th>
                                </tr>
                                <tr>
                                    <td>${periodical.price} <spring:message code="msg.general.usd"/></td>
                                </tr>
                                <tr>
                                    <th scope="row"><spring:message code="msg.periodical.language"/></th>
                                </tr>
                                <tr>
                                    <td>${periodical.language}</td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </article>
        </main>
    </body>
</html>