<%@ page pageEncoding="UTF-8" contentType= "text/html; charset=UTF-8" isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<nav>
    <ul>
        <li><a class="active" href="/"><spring:message code="msg.main.home"/></a></li>
        <li><a href="/periodical/all"><spring:message code="msg.main.periodicals"/></a></li>
        <c:if test="${sessionScope.user.roleDto == 'ADMIN'}">
            <li><a href="/user/all"><spring:message code="msg.main.users.all"/></a></li>
            <li><a href="/subscription/all"><spring:message code="msg.main.subscriptions.all"/></a></li>
            <li><a href="/payment/all"><spring:message code="msg.main.payments.all"/></a></li>
        </c:if>
        <c:if test="${sessionScope.user.roleDto == 'READER'}">
            <li><a href="/user/${sessionScope.user.id}"><spring:message code="msg.main.my.profile"/></a></li>
            <li><a href="/subscription/user/${sessionScope.user.id}"><spring:message code="msg.main.my.subscriptions"/></a></li>
        </c:if>

        <li><a href="/cart/show"><spring:message code="msg.main.cart"/></a></li>
        <c:if test="${sessionScope.user != null}">
            <li style="float: right"><a href="/logout"><spring:message code="msg.main.logout"/></a></li>
        </c:if>
        <c:if test="${sessionScope.user == null}">
            <li style="float: right"><a href="/login"><spring:message code="msg.main.login"/></a></li>
            <li style="float: right"><a href="/user/create"><spring:message code="msg.main.signup"/></a></li>
        </c:if>
        <li style="float: right"><a href="?lang=de">DE</a></li>
        <li style="float: right"><a href="?lang=ru">RU</a></li>
        <li style="float: right"><a href="?lang=en">EN</a></li>
    </ul>
</nav>