<%@ page pageEncoding="UTF-8" contentType= "text/html; charset=UTF-8" isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="isAuthenticated()">
    <sec:authentication var="authenticatedUser" property="principal"/>
</sec:authorize>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark" id="myNav">
    <div class="container-fluid">
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDarkDropdown" aria-controls="navbarNavDarkDropdown" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavDarkDropdown">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="/"><spring:message code="msg.main.home"/></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/periodicals/all"><spring:message code="msg.main.periodicals"/></a>
                </li>
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <li class="nav-item">
                        <a class="nav-link" href="/users/all"><spring:message code="msg.main.users.all"/></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/subscriptions/all"><spring:message code="msg.main.subscriptions.all"/></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/payments/all"><spring:message code="msg.main.payments.all"/></a>
                    </li>
                </sec:authorize>
                <sec:authorize access="hasRole('ROLE_READER')">
                    <li class="nav-item" style="float: right;">
                        <a class="nav-link" href="/users/${authenticatedUser.id}"><spring:message code="msg.main.my.profile"/></a>
                    </li>
                    <li class="nav-item" style="float: right;">
                        <a class="nav-link" href="/subscriptions/user/${authenticatedUser.id}"><spring:message code="msg.main.my.subscriptions"/></a>
                    </li>
                </sec:authorize>
                <li class="nav-item">
                    <a class="nav-link" href="/cart/show"><spring:message code="msg.main.cart"/></a>
                </li>
                <sec:authorize access="isAuthenticated()">
                    <li class="nav-item" id="logout-tab">
                        <a class="nav-link" href="/logout"><spring:message code="msg.main.logout"/></a>
                    </li>
                </sec:authorize>
                <sec:authorize access="isAnonymous()">
                    <li class="nav-item">
                        <a class="nav-link" href="/login"><spring:message code="msg.main.login"/></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/users/create"><spring:message code="msg.main.signup"/></a>
                    </li>
                </sec:authorize>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDarkDropdownMenuLink" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        <spring:message code="msg.periodical.language"/>
                    </a>
                    <ul class="dropdown-menu dropdown-menu-dark" aria-labelledby="navbarDarkDropdownMenuLink">
                        <li><a class="dropdown-item" href="?lang=en">EN</a></li>
                        <li><a class="dropdown-item" href="?lang=de">DE</a></li>
                        <li><a class="dropdown-item" href="?lang=ru">RU</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>
<script>
    const header = document.getElementById("myNav");
    const links = header.getElementsByClassName("nav-link");
    const url = window.location.origin + window.location.pathname;
    const current = [...links].find(x => x.href === url);
    current.className += " active";
</script>
