<%@ page pageEncoding="UTF-8" contentType= "text/html; charset=UTF-8" isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title><spring:message code="msg.main.periodicals"/></title>
        <jsp:include page="../links.jsp"/>
    </head>
    <body>
        <main>
            <jsp:include page="../navbar.jsp"/>
            <section>
                <header>
                    <h2><spring:message code="msg.main.periodicals"/></h2>
                </header>
                <div class="message-container">
                    <h4 id="message"><c:out value="${message}"/></h4>
                </div>
            </section>
            <section>
                <div class="search-container">
                    <form id="search" action="/periodicals/all">
                        <div class="input-group" style="float:right; width:30%">
                            <input type="search" class="form-control rounded" placeholder="<spring:message code="msg.periodical.search"/>" aria-label="Search" aria-describedby="search-addon" name="keyword"/>
                            <button type="submit" class="btn btn-outline-dark" ><spring:message code="msg.general.search"/></button>
                        </div>
                    </form>
                </div>
                <div id="add-periodical-container">
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <form action="/periodicals/create">
                            <button class="btn btn-light" type="submit" title="<spring:message code="msg.periodical.add.title"/>"><spring:message code="msg.periodical.add"/></button>
                        </form>
                    </sec:authorize>
                </div>
            </section>
            <div class="filter-container">
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
            </div>
            <article>
                <c:if test="${periodicals.size() > 0}">
                    <jsp:include page="../pagination.jsp"/>
                    <div id="periodical-container">
                        <ul id="periodical-list">
                            <c:forEach var="periodical" items="${periodicals}">
                                <li>
                                    <div class="card">
                                        <img src="/images/periodicals/${periodical.imagePath}" alt="Periodical ${periodical.id}" style="height:400px;width:auto"/>
                                        <div class="card-body">
                                            <h5 class="card-title">${periodical.title}</h5>
                                            <p class="card-text">${periodical.price} <spring:message code="msg.general.usd"/></p>
                                            <a href="/periodicals/${periodical.id}" class="btn btn-primary"><spring:message code="msg.general.learn.more"/> </a>
                                        </div>
                                    </div>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </c:if>
                <br>
            </article>
        </main>
    </body>
</html>