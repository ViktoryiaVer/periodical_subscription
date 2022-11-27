<%@ page pageEncoding="UTF-8" contentType= "text/html; charset=UTF-8" isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title><spring:message code="msg.periodical.update"/></title>
        <jsp:include page="../links.jsp"/>
    </head>
    <body>
        <jsp:include page="../navbar.jsp"/>
        <main>
            <section>
                <header>
                    <h2><spring:message code="msg.periodical.update"/></h2>
                </header>
                <div class="message-container">
                    <c:if test="${errors != null}">
                        <div style="color: red">
                            <p><spring:message code="msg.validation.invalid.input"/>:</p>
                            <ul>
                                <c:forEach var="error" items="${errors}">
                                    <li>${error.defaultMessage}</li>
                                </c:forEach>
                            </ul>
                        </div>
                    </c:if>
                </div>
            </section>
            <article>
                <form id="upform" action="/periodicals/update/" enctype="multipart/form-data" method="post">
                    <input name="id" type="hidden" value="${periodical.id}"/>
                    <div class="form-group">
                        <label for="title"><spring:message code="msg.periodical.title"/></label>
                        <input type="text" class="form-control" id="title" name="title" value="${periodical.title}" required>
                    </div>
                    <div class="form-group">
                        <label for="publisher"><spring:message code="msg.periodical.publisher"/></label>
                        <input type="text" class="form-control" id="publisher" name="publisher" value="${periodical.publisher}" required>
                    </div>
                    <div class="form-group">
                        <label for="description"><spring:message code="msg.periodical.description"/></label>
                        <textarea class="form-control" id="description" name="description" required>${periodical.description}</textarea>
                    </div>
                    <div class="form-group">
                        <label for="publicationDate"><spring:message code="msg.periodical.publication.date"/></label>
                        <input type="date" class="form-control" id="publicationDate" name="publicationDate" value="${periodical.publicationDate}" required>
                    </div>
                    <div class="form-group">
                        <label for="issuesAmountInYear"><spring:message code="msg.periodical.issues.year"/></label>
                        <input type="number" class="form-control" id="issuesAmountInYear" name="issuesAmountInYear" value="${periodical.issuesAmountInYear}" required>
                    </div>
                    <div class="form-group">
                        <label for="price"><spring:message code="msg.periodical.price"/></label>
                        <input type="text" class="form-control" id="price" name="price" value="${periodical.price}" required>
                    </div>
                    <label for="language"><spring:message code="msg.periodical.add.specify.language"/>:</label>
                    <select id="language" class="form-select form-select-sm" aria-label=".form-select-sm example" name="language">
                        <option value="English" ${periodical.language=='English' ? 'selected' : ''}><spring:message code="msg.periodical.english"/></option>
                        <option value="German" ${periodical.language=='German' ? 'selected' : ''}><spring:message code="msg.periodical.german"/></option>
                        <option value="French" ${periodical.language=='French' ? 'selected' : ''}><spring:message code="msg.periodical.french"/></option>
                        <option value="Chinese" ${periodical.language=='Chinese' ? 'selected' : ''}><spring:message code="msg.periodical.chinese"/></option>
                        <option value="Russian" ${periodical.language=='Russian' ? 'selected' : ''}><spring:message code="msg.periodical.russian"/></option>
                        <option value="Italian" ${periodical.language=='Italian' ? 'selected' : ''}><spring:message code="msg.periodical.italian"/></option>
                    </select>
                    <div class="form-group">
                        <label for="image" title="Upload new image if necessary"><spring:message code="msg.periodical.image"/></label >
                        <input name="imagePath" type="hidden" value="${periodical.imagePath}"/>
                        <input id="image" name="imageFile" type="file" class="form-control" accept="image/*">
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="typeDto" id="type-radio-magazine" value="MAGAZINE" ${periodical.typeDto=='MAGAZINE' ? 'checked' : ''}/>
                        <label class="form-check-label" for="type-radio-magazine">
                            <spring:message code="msg.periodical.magazine"/>
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="typeDto" id="type-radio-journal" value="JOURNAL" ${periodical.typeDto=='JOURNAL' ? 'checked' : ''}/>
                        <label class="form-check-label" for="type-radio-journal">
                            <spring:message code="msg.periodical.journal"/>
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="typeDto" id="type-radio-newspaper" value="NEWSPAPER" ${periodical.typeDto=='NEWSPAPER' ? 'checked' : ''}/>
                        <label class="form-check-label" for="type-radio-newspaper">
                            <spring:message code="msg.periodical.newspaper"/>
                        </label>
                    </div>
                    <select id="category" class="form-select form-select-sm" aria-label=".form-select-sm example" multiple name="categoryDtos">
                        <option value="ART_AND_ARCHITECTURE"
                                <c:forEach var="category" items="${periodical.categoryDtos}">
                                    ${category.categoryDto=='ART_AND_ARCHITECTURE' ? 'selected' : ''}
                                </c:forEach>
                        ><spring:message code="msg.periodical.art.architecture"/></option>
                        <option value="SCIENCE"
                                <c:forEach var="category" items="${periodical.categoryDtos}">
                                    ${category.categoryDto=='SCIENCE' ? 'selected' : ''}
                                </c:forEach>
                        ><spring:message code="msg.periodical.science"/></option>
                        <option value="BUSINESS_AND_FINANCE"
                                <c:forEach var="category" items="${periodical.categoryDtos}">
                                    ${category.categoryDto=='BUSINESS_AND_FINANCE' ? 'selected' : ''}
                                </c:forEach>
                        ><spring:message code="msg.periodical.business"/></option>
                        <option value="NEWS_AND_POLITICS"
                                <c:forEach var="category" items="${periodical.categoryDtos}">
                                    ${category.categoryDto=='NEWS_AND_POLITICS' ? 'selected' : ''}
                                </c:forEach>
                        ><spring:message code="msg.periodical.news.politics"/></option>
                        <option value="CULTURE_AND_LITERATURE"
                                <c:forEach var="category" items="${periodical.categoryDtos}">
                                    ${category.categoryDto=='CULTURE_AND_LITERATURE' ? 'selected' : ''}
                                </c:forEach>
                        ><spring:message code="msg.periodical.culture.literature"/></option>
                        <option value="TRAVEL_AND_OUTDOOR"
                                <c:forEach var="category" items="${periodical.categoryDtos}">
                                    ${category.categoryDto=='TRAVEL_AND_OUTDOOR' ? 'selected' : ''}
                                </c:forEach>
                        ><spring:message code="msg.periodical.travel.outdoor"/></option>
                    </select>
                    <button type="submit" class="btn btn-primary"><spring:message code="msg.general.update"/></button>
                </form>
            </article>
        </main>
    </body>
</html>