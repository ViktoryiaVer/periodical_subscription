<%@ page pageEncoding="UTF-8" contentType= "text/html; charset=UTF-8" isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div id="pagination">
    <c:if test="${totalPages > 1}">
        <c:if test="${currentPage > 1}">
            <a href="${command}?page=1&page_size=${pageSize}"><spring:message code="msg.main.pagination.first"/></a>
            <a href="${command}?page=${currentPage - 1}&page_size=${pageSize}">&lsaquo;</a>
        </c:if>
        <span><spring:message code="msg.main.pagination.page"/> ${currentPage} <spring:message code="msg.main.pagination.out.of"/> ${totalPages}</span>
        <c:if test="${currentPage < totalPages}">
            <a href="${command}?page=${currentPage + 1}&page_size=${pageSize}">&rsaquo;</a>
            <a href="${command}?page=${totalPages}&page_size=${pageSize}"><spring:message code="msg.main.pagination.last"/></a>
        </c:if>
    </c:if>
    <form id="page-size-form" action="${command}">
        <input type="hidden" name="page" value="1">
        <select id="page-size-select" class="form-select form-select-sm" aria-label=".form-select-sm example" name="page_size">
            <option value="5"${pageSize == 5 ? 'selected' : ''}>5</option>
            <option value="10"${pageSize == 10 ? 'selected' : ''}>10</option>
            <option value="20"${pageSize == 20 ? 'selected' : ''}>20</option>
            <option value="30"${pageSize == 30 ? 'selected' : ''}>30</option>
            <option value="50"${pageSize == 50 ? 'selected' : ''}>50</option>
        </select>
        <button class="btn btn-light" type="submit"><spring:message code="msg.main.pagination.show"/></button>
    </form>
</div>
