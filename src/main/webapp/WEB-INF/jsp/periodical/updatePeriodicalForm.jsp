<%@ page pageEncoding="UTF-8" contentType= "text/html; charset=UTF-8" isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Edit periodical</title>
        <link rel="stylesheet" href="/css/styles.css" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css"
              rel="stylesheet"
              integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx"
              crossorigin="anonymous" />
    </head>
    <body>
        <jsp:include page="../navbar.jsp"/>
        <h2>Edit periodical</h2>
        <form id="sform" action="/periodical/update/" enctype="multipart/form-data" method="post">
            <input name="id" type="hidden" value="${periodical.id}"/>
            <div class="form-group">
                <label for="title">Title</label>
                <input type="text" class="form-control" id="title" name="title" value="${periodical.title}" required>
            </div>
            <div class="form-group">
                <label for="publisher">Publisher</label>
                <input type="text" class="form-control" id="publisher" name="publisher" value="${periodical.publisher}" required>
            </div>
            <div class="form-group">
                <label for="description">Description</label>
                <input type="text" class="form-control" id="description" name="description" value="${periodical.description}" required>
            </div>
            <div class="form-group">
                <label for="publicationDate">Publication date</label>
                <input type="date" class="form-control" id="publicationDate" name="publicationDate" value="${periodical.publicationDate}" required>
            </div>
            <div class="form-group">
                <label for="issuesAmountInYear">Issues amount in year</label>
                <input type="number" class="form-control" id="issuesAmountInYear" name="issuesAmountInYear" value="${periodical.issuesAmountInYear}" required>
            </div>
            <div class="form-group">
                <label for="price">Price</label>
                <input type="text" class="form-control" id="price" name="price" value="${periodical.price}" required>
            </div>
            <label for="language">Choose a language:</label>
            <select id="language" class="form-select form-select-sm" aria-label=".form-select-sm example" name="language">
                <option value="English" ${periodical.language=='English' ? 'selected' : ''}>English</option>
                <option value="German" ${periodical.language=='German' ? 'selected' : ''}>German</option>
                <option value="French" ${periodical.language=='French' ? 'selected' : ''}>French</option>
                <option value="Chinese" ${periodical.language=='Chinese' ? 'selected' : ''}>Chinese</option>
                <option value="Russian" ${periodical.language=='Russian' ? 'selected' : ''}>Russian</option>
                <option value="Italian" ${periodical.language=='Italian' ? 'selected' : ''}>Italian</option>
            </select>
            <div class="form-group">
                <label for="image" title="Upload new image if necessary">Image</label >
                <input name="imagePath" type="hidden" value="${periodical.imagePath}"/>
                <input id="image" name="imageFile" type="file" class="form-control" accept="image/*">
                </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="typeDto" id="type-radio-magazine" value="MAGAZINE" ${periodical.typeDto=='MAGAZINE' ? 'checked' : ''}/>
                <label class="form-check-label" for="type-radio-magazine">
                    Magazine
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="typeDto" id="type-radio-journal" value="JOURNAL" ${periodical.typeDto=='JOURNAL' ? 'checked' : ''}/>
                <label class="form-check-label" for="type-radio-journal">
                    Journal
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="typeDto" id="type-radio-newspaper" value="NEWSPAPER" ${periodical.typeDto=='NEWSPAPER' ? 'checked' : ''}/>
                <label class="form-check-label" for="type-radio-newspaper">
                    Newspaper
                </label>
            </div>
            <select id="category" class="form-select form-select-sm" aria-label=".form-select-sm example" multiple name="categoryDtos">
                <option value="ART_AND_ARCHITECTURE"
                    <c:forEach var="category" items="${periodical.categoryDtos}">
                        ${category.categoryDto=='ART_AND_ARCHITECTURE' ? 'selected' : ''}
                    </c:forEach>
                >Art and Architecture</option>
                <option value="SCIENCE"
                    <c:forEach var="category" items="${periodical.categoryDtos}">
                        ${category.categoryDto=='SCIENCE' ? 'selected' : ''}
                    </c:forEach>
                >Science</option>
                <option value="BUSINESS_AND_FINANCE"
                    <c:forEach var="category" items="${periodical.categoryDtos}">
                        ${category.categoryDto=='BUSINESS_AND_FINANCE' ? 'selected' : ''}
                    </c:forEach>
                >Business and Finance</option>
                <option value="NEWS_AND_POLITICS"
                    <c:forEach var="category" items="${periodical.categoryDtos}">
                        ${category.categoryDto=='NEWS_AND_POLITICS' ? 'selected' : ''}
                    </c:forEach>
                >News and Politics</option>
                <option value="CULTURE_AND_LITERATURE"
                    <c:forEach var="category" items="${periodical.categoryDtos}">
                        ${category.categoryDto=='CULTURE_AND_LITERATURE' ? 'selected' : ''}
                    </c:forEach>
                >Culture and Literature</option>
                <option value="TRAVEL_AND_OUTDOOR"
                    <c:forEach var="category" items="${periodical.categoryDtos}">
                        ${category.categoryDto=='TRAVEL_AND_OUTDOOR' ? 'selected' : ''}
                    </c:forEach>
                >Travel and Outdoor</option>
            </select>
            <button type="submit" class="btn btn-primary">Submit</button>
        </form>
    </body>
</html>