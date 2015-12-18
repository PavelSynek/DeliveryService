<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Orders">
<jsp:attribute name="body">

    <table class="table">
        <caption>products</caption>
        <thead>
        <tr>
            <th>id</th>
            <th>employee</th>
            <th>created</th>
            <th>state</th>
            <th>customer</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${order}" var="order">
            <tr>
                <td>${order.id}</td>
                <td><c:out value="${order.employee.name}"/></td>
                <td><my:LocalDate date="${order.created}"/></td>
                <td><c:out value="${order.orderState}"/></td>
                <td><c:out value="${order.price}"/></td>
                <td><c:out value="${order.customer.name}"/></td>
                <td>
                    <my:a href="/order/detail/id=${order.id}" class="btn btn-primary">View</my:a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</jsp:attribute>
</my:pagetemplate>