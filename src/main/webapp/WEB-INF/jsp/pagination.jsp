<%@ page pageEncoding="UTF-8" contentType= "text/html; charset=UTF-8" isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div id="pagination">
    <c:if test="${totalPages > 1}">
        <c:if test="${currentPage > 1}">
            <c:choose>
                <c:when test="${periodicalFilter != null}">
                    <a href="${command}?page=1&page_size=${pageSize}&category=${periodicalFilter.category}&type=${periodicalFilter.type}"><spring:message code="msg.main.pagination.first"/></a>
                    <a href="${command}?page=${currentPage - 1}&page_size=${pageSize}&category=${periodicalFilter.category}&type=${periodicalFilter.type}">&lsaquo;</a>
                </c:when>
                <c:when test="${subscriptionFilter != null}">
                    <a href="${command}?page=1&page_size=${pageSize}&status=${subscriptionFilter}"><spring:message code="msg.main.pagination.first"/></a>
                    <a href="${command}?page=${currentPage - 1}&page_size=${pageSize}&status=${subscriptionFilter}">&lsaquo;</a>
                </c:when>
                <c:when test="${paymentFilter != null}">
                    <a href="${command}?page=1&page_size=${pageSize}&paymentMethod=${paymentFilter.paymentMethod}&paymentDate=${paymentFilter.paymentDate}"><spring:message code="msg.main.pagination.first"/></a>
                    <a href="${command}?page=${currentPage - 1}&page_size=${pageSize}&&paymentMethod=${paymentFilter.paymentMethod}&paymentDate=${paymentFilter.paymentDate}">&lsaquo;</a>
                </c:when>
                <c:when test="${search != null}">
                    <a href="${command}?page=1&page_size=${pageSize}&keyword=${search}"><spring:message code="msg.main.pagination.first"/></a>
                    <a href="${command}?page=${currentPage - 1}&page_size=${pageSize}&keyword=${search}">&lsaquo;</a>
                </c:when>
                <c:otherwise>
                    <a href="${command}?page=1&page_size=${pageSize}"><spring:message code="msg.main.pagination.first"/></a>
                    <a href="${command}?page=${currentPage - 1}&page_size=${pageSize}">&lsaquo;</a>
                </c:otherwise>
            </c:choose>
        </c:if>
        <span><spring:message code="msg.main.pagination.page"/> ${currentPage} <spring:message code="msg.main.pagination.out.of"/> ${totalPages}</span>
        <c:if test="${currentPage < totalPages}">
            <c:choose>
                <c:when test="${subscriptionFilter != null}">
                    <a href="${command}?page=${currentPage + 1}&page_size=${pageSize}&status=${subscriptionFilter}">&rsaquo;</a>
                    <a href="${command}?page=${totalPages}&page_size=${pageSize}&status=${subscriptionFilter}}"><spring:message code="msg.main.pagination.last"/></a>
                </c:when>
                <c:when test="${periodicalFilter != null}">
                    <a href="${command}?page=${currentPage + 1}&page_size=${pageSize}&category=${periodicalFilter.category}&type=${periodicalFilter.type}">&rsaquo;</a>
                    <a href="${command}?page=${totalPages}&page_size=${pageSize}&category=${periodicalFilter.category}&type=${periodicalFilter.type}"><spring:message code="msg.main.pagination.last"/></a>
                </c:when>
                <c:when test="${paymentFilter != null}">
                    <a href="${command}?page=${currentPage + 1}&page_size=${pageSize}&paymentMethod=${paymentFilter.paymentMethod}&paymentDate=${paymentFilter.paymentDate}">&rsaquo;</a>
                    <a href="${command}?page=${totalPages}&page_size=${pageSize}&paymentMethod=${paymentFilter.paymentMethod}&paymentDate=${paymentFilter.paymentDate}"><spring:message code="msg.main.pagination.last"/></a>
                </c:when>
                <c:when test="${search != null}">
                    <a href="${command}?page=${currentPage + 1}&page_size=${pageSize}&keyword=${search}">&rsaquo;</a>
                    <a href="${command}?page=${totalPages}&page_size=${pageSize}&keyword=${search}}"><spring:message code="msg.main.pagination.last"/></a>
                </c:when>
                <c:otherwise>
                    <a href="${command}?page=${currentPage + 1}&page_size=${pageSize}">&rsaquo;</a>
                    <a href="${command}?page=${totalPages}&page_size=${pageSize}"><spring:message code="msg.main.pagination.last"/></a>
                </c:otherwise>
            </c:choose>>
        </c:if>
    </c:if>
        <form id="page-size-form" action="${command}">
            <c:if test="${periodicalFilter != null}">
                <input type="hidden" name="category" value="${periodicalFilter.category}"/>
                <input type="hidden" name="type" value="${periodicalFilter.type}"/>
            </c:if>
            <c:if test="${subscriptionFilter != null}">
                <input type="hidden" name="status" value="${subscriptionFilter}"/>
            </c:if>
            <c:if test="${paymentFilter != null}">
                <input type="hidden" name="paymentMethod" value="${paymentFilter.paymentMethod}"/>
                <input type="hidden" name="paymentDate" value="${paymentFilter.paymentDate}"/>
            </c:if>
            <c:if test="${search != null}">
                <input type="hidden" name="keyword" value="${search}"/>
            </c:if>
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
