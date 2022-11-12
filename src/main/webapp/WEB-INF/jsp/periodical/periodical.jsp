<%@ page pageEncoding="UTF-8" contentType= "text/html; charset=UTF-8" isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title><spring:message code="msg.main.periodicals.detail"/></title>
        <link rel="stylesheet" href="/css/styles.css" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css"
              rel="stylesheet"
              integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx"
              crossorigin="anonymous" />
        <script src="/js/script.js"></script>
    </head>
    <body>
        <jsp:include page="../navbar.jsp"/>
        <h2 style="text-align: center"><spring:message code="msg.main.periodicals.detail"/></h2>
        <h4 id="message"><c:out value="${message}"/></h4>
        <c:if test="${sessionScope.user.roleDto == 'ADMIN'}">
            <form action="/periodical/update/${periodical.id}">
                <button class="btn btn-light" type="submit" title="<spring:message code="msg.periodical.update"/>"><spring:message code="msg.general.update"/></button>
            </form>
            <form action="/periodical/delete/${periodical.id}" method="post">
                <button class="btn btn-light" type="submit" title="<spring:message code="msg.periodical.delete"/>"><spring:message code="msg.general.delete"/></button>
            </form>
            <c:if test="${periodical.statusDto == 'AVAILABLE'}">
                <form action="/periodical/update/${periodical.id}/status" method="post">
                    <button class="btn btn-light" type="submit" title="<spring:message code="msg.periodical.mark.unavailable.title"/>" name="statusDto" value="UNAVAILABLE"><spring:message code="msg.periodical.mark.unavailable"/></button>
                </form>
            </c:if>
            <c:if test="${periodical.statusDto == 'UNAVAILABLE'}">
                <form action="/periodical/update/${periodical.id}/status" method="post">
                    <button class="btn btn-light" type="submit" name="statusDto" value="AVAILABLE"><spring:message code="msg.periodical.mark.available"/></button>
                </form>
            </c:if>
        </c:if>
        <table>
            <tr>
                <th></th>
                <th><spring:message code="msg.periodical.title"/></th>
                <th><spring:message code="msg.periodical.publisher"/></th>
                <th><spring:message code="msg.periodical.description"/></th>
                <th><spring:message code="msg.periodical.publication.date"/></th>
                <th><spring:message code="msg.periodical.issues.year"/></th>
                <th><spring:message code="msg.periodical.price"/></th>
                <th><spring:message code="msg.periodical.language"/></th>
                <th><spring:message code="msg.periodical.type"/></th>
                <th><spring:message code="msg.periodical.category"/></th>
            </tr>
            <tr>
                <td><img src="/images/periodicals/${periodical.imagePath}" alt="Periodical ${periodical.id}" style="height:400px;width:auto"/></td>
                <td>${periodical.title}</td>
                <td>${periodical.publisher}</td>
                <td>${periodical.description}</td>
                <td>${periodical.publicationDate}</td>
                <td>${periodical.issuesAmountInYear}</td>
                <td>${periodical.price}</td>
                <td>${periodical.language}</td>
                <td>${periodical.typeDto.toString().toLowerCase()}</td>
                <td>
                    <c:forEach var="category" items="${periodical.categoryDtos}">
                        ${category.categoryDto.toString().toLowerCase()}
                    </c:forEach>
                </td>
            </tr>
        </table>
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
    </body>
</html>