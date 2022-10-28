<%@ page pageEncoding="UTF-8" contentType= "text/html; charset=UTF-8" isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav>
    <ul>
        <li><a class="active" href="/">Home</a></li>
        <li><a href="/periodical/all">Periodicals</a></li>
        <c:if test="${sessionScope.user.roleDto == 'ADMIN'}">
            <li><a href="/user/all">Users</a></li>
            <li><a href="/subscription/all">Subscriptions</a></li>
            <li><a href="/payment/all">Payments</a></li>
        </c:if>
        <c:if test="${sessionScope.user.roleDto == 'READER'}">
            <li><a href="/user/${sessionScope.user.id}">My profile</a></li>
            <li><a href="/subscription/user/${sessionScope.user.id}">My subscriptions</a></li>
        </c:if>

        <li><a href="/cart/show">Cart</a></li>
        <c:if test="${sessionScope.user != null}">
            <li style="float: right"><a href="/logout">Log out</a></li>
        </c:if>
        <c:if test="${sessionScope.user == null}">
            <li style="float: right"><a href="/login">Log in</a></li>
            <li style="float: right"><a href="/user/create">Sign up</a></li>
        </c:if>
    </ul>
</nav>