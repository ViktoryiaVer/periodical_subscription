<%@ page pageEncoding="UTF-8" contentType= "text/html; charset=UTF-8" isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Add periodical</title>
        <link rel="stylesheet" href="/css/styles.css" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css"
              rel="stylesheet"
              integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx"
              crossorigin="anonymous" />
    </head>
    <body>
    <jsp:include page="../navbar.jsp"/>
    <h2>Add periodical</h2>
    <form id="pform" enctype="multipart/form-data" action="/periodical/create/" method="post">
        <div class="form-group">
            <label for="title">Title</label>
            <input type="text" class="form-control" id="title" name="title" placeholder="Specify title" required>
        </div>
        <div class="form-group">
            <label for="publisher">Publisher</label>
            <input type="text" class="form-control" id="publisher" name="publisher" placeholder="Specify publisher" required>
        </div>
        <div class="form-group">
            <label for="description">Description</label>
            <input type="text" class="form-control" id="description" name="description" placeholder="Specify description" required>
        </div>
        <div class="form-group">
            <label for="publicationDate">Publication date</label>
            <input type="date" class="form-control" id="publicationDate" name="publicationDate" placeholder="Specify publication date" required>
        </div>
        <div class="form-group">
            <label for="issuesAmountInYear">Issues amount in year</label>
            <input type="number" class="form-control" id="issuesAmountInYear" name="issuesAmountInYear" placeholder="Specify issues amount" required>
        </div>
        <div class="form-group">
            <label for="price">Price</label>
            <input type="text" class="form-control" id="price" name="price" placeholder="Specify price" required>
        </div>
        <label for="language">Choose a language:</label>
        <select id="language" class="form-select form-select-sm" aria-label=".form-select-sm example" name="language">
            <option selected>English</option>
            <option value="German">German</option>
            <option value="French">French</option>
            <option value="Chinese">Chinese</option>
            <option value="Russian">Russian</option>
            <option value="Italian">Italian</option>
        </select>
        <div class="form-group">
            <label for="imageFile">Image</label>
            <input type="file" class="form-control" id="imageFile" name="imageFile" placeholder="Upload image" accept="image/*" required>
        </div>
        <div class="form-check">
            <input class="form-check-input" type="radio" name="typeDto" id="type-radio-magazine" value="MAGAZINE" checked/>
            <label class="form-check-label" for="type-radio-magazine">
                Magazine
            </label>
        </div>
        <div class="form-check">
            <input class="form-check-input" type="radio" name="typeDto" id="type-radio-journal" value="JOURNAL"/>
            <label class="form-check-label" for="type-radio-journal">
                Journal
            </label>
        </div>
        <div class="form-check">
            <input class="form-check-input" type="radio" name="typeDto" id="type-radio-newspaper" value="NEWSPAPER"/>
            <label class="form-check-label" for="type-radio-newspaper">
                Newspaper
            </label>
        </div>
        <select id="category" class="form-select form-select-sm" aria-label=".form-select-sm example" multiple name="categoryDtos">
            <option value="ART_AND_ARCHITECTURE" selected>Art and Architecture</option>
            <option value="SCIENCE">Science</option>
            <option value="BUSINESS_AND_FINANCE">Business and Finance</option>
            <option value="NEWS_AND_POLITICS">News and Politics</option>
            <option value="CULTURE_AND_LITERATURE">Culture and Literature</option>
            <option value="TRAVEL_AND_OUTDOOR">Travel and Outdoor</option>
        </select>
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
    </body>
</html>