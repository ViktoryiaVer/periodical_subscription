<%@ page pageEncoding="UTF-8" contentType= "text/html; charset=UTF-8" isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title><spring:message code="msg.main.periodicals"/></title>
        <link rel="stylesheet" href="/css/styles.css" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css"
              rel="stylesheet"
              integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx"
              crossorigin="anonymous" />
    </head>
    <body>
        <jsp:include page="../navbar.jsp"/>
        <h2 style="text-align: center"><spring:message code="msg.main.periodicals"/></h2>
        <h4 id="message"><c:out value="${message}"/></h4>
        <c:if test="${sessionScope.user.roleDto == 'ADMIN'}">
            <form action="/periodicals/create">
                <button class="btn btn-light" type="submit" title="<spring:message code="msg.periodical.add.title"/>"><spring:message code="msg.periodical.add"/></button>
            </form>
        </c:if>
        <form id="search" action="/periodicals/all">
            <div class="input-group" style="float:right; width:30%">
                <input type="search" class="form-control rounded" placeholder="<spring:message code="msg.periodical.search"/>" aria-label="Search" aria-describedby="search-addon" name="keyword"/>
                <button type="submit" class="btn btn-outline-dark" ><spring:message code="msg.general.search"/></button>
            </div>
        </form>
        <form id="filter-form" action="/periodicals/all">
            <select id="category" class="form-select form-select-sm" aria-label=".form-select-sm example" name="category">
                <option hidden disabled selected value><spring:message code="msg.periodical.select.category"/></option>
                <option value="ART_AND_ARCHITECTURE" ${periodicalFilter.category == 'ART_AND_ARCHITECTURE' ? 'selected' : ''}><spring:message code="msg.periodical.art.architecture"/></option>
                <option value="SCIENCE" ${periodicalFilter.category == 'SCIENCE' ? 'selected' : ''}><spring:message code="msg.periodical.science"/></option>
                <option value="BUSINESS_AND_FINANCE" ${periodicalFilter.category == 'BUSINESS_AND_FINANCE' ? 'selected' : ''}><spring:message code="msg.periodical.business"/></option>
                <option value="NEWS_AND_POLITICS" ${periodicalFilter.category == 'NEWS_AND_POLITICS' ? 'selected' : ''}><spring:message code="msg.periodical.news.politics"/></option>
                <option value="CULTURE_AND_LITERATURE" ${periodicalFilter.category== 'CULTURE_AND_LITERATURE' ? 'selected' : ''}><spring:message code="msg.periodical.culture.literature"/></option>
                <option value="TRAVEL_AND_OUTDOOR" ${periodicalFilter.category == 'TRAVEL_AND_OUTDOOR' ? 'selected' : ''}><spring:message code="msg.periodical.travel.outdoor"/></option>
            </select>
            <select id="type" class="form-select form-select-sm" aria-label=".form-select-sm example" name="type">
                <option hidden disabled selected value><spring:message code="msg.periodical.select.type"/></option>
                <option value="MAGAZINE" ${periodicalFilter.type == 'MAGAZINE' ? 'selected' : ''}><spring:message code="msg.periodical.magazine"/></option>
                <option value="JOURNAL" ${periodicalFilter.type == 'JOURNAL' ? 'selected' : ''}><spring:message code="msg.periodical.journal"/></option>
                <option value="NEWSPAPER" ${periodicalFilter.type== 'NEWSPAPER' ? 'selected' : ''}><spring:message code="msg.periodical.newspaper"/></option>
            </select>
            <button type="submit" class="btn btn-light"><spring:message code="msg.general.filter"/></button>
        </form>
        <c:if test="${periodicals.size() > 0}">
            <table>
                <tr>
                    <th>#</th>
                    <th><spring:message code="msg.periodical.title"/></th>
                    <th><spring:message code="msg.periodical.price"/></th>
                    <th><spring:message code="msg.periodical.category"/></th>
                </tr>
                <c:forEach var="periodical" items="${periodicals}" varStatus="counter">
                    <tr>
                        <td>${counter.count}</td>
                        <td><a href="/periodicals/${periodical.id}">${periodical.title}</a></td>
                        <td>${periodical.price}</td>
                        <td>
                            <c:forEach var="category" items="${periodical.categoryDtos}">
                                ${category.categoryDto.toString().toLowerCase()}
                            </c:forEach>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <jsp:include page="../pagination.jsp"/>
        </c:if>
    </body>
</html>