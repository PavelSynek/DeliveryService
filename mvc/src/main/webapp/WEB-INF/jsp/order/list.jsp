<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Orders">
<jsp:attribute name="body">

    <table class="table">
        <caption>Orders</caption>
        <thead>
        <tr>
            <th>Unique ID</th>
            <th>Assigned Employee</th>
            <th>Date of Creation</th>
            <th>State</th>
            <th>Customer</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${orders}" var="order">
            <tr>
                <td>${order.id}</td>
                <td><c:out value="${order.employee.firstName} ${order.employee.surname}"/></td>
                <td><my:LocalDate date="${order.created}"/></td>
                <td><c:out value="${order.state}"/></td>
                <%--<td><c:out value="${order.price}"/></td>--%>
                <td><c:out value="${order.customer.firstName} ${order.customer.surname}"/></td>
                <td>
                    <my:a href="/order/detail/id=${order.id}" class="btn btn-primary">View</my:a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</jsp:attribute>
</my:pagetemplate>