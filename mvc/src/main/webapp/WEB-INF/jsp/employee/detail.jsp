<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Employee">
<jsp:attribute name="body">

    <form method="post" action="${pageContext.request.contextPath}/employee/delete/${employee.id}">
        <button type="submit" class="btn btn-primary">Delete</button>
    </form>


    <table class="table table-striped">
        <thead>
        <tr>
            <th>id</th>
            <th>first name</th>
            <th>surname</th>
            <th>email</th>
            <th>phone</th>
            <th>registration date</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>${employee.id}</td>
            <td><c:out value="${employee.firstName}"/></td>
            <td><c:out value="${employee.surname}"/></td>
            <td><c:out value="${employee.email}"/></td>
            <td><c:out value="${employee.phone}"/></td>
            <td><my:LocalDate date="${employee.registrationDate}"/></td>
        </tr>
        </tbody>
    </table>

    <table class="table table-striped">
        <caption>Orders</caption>
        <thead>
        <tr>
            <th>ID</th>
            <th>Created</th>
            <th>State</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${orders}" var="order">
            <tr>
                <td>${order.id}</td>
                <td><my:LocalDate date="${order.created}"/></td>
                <td><c:out value="${order.state}"/></td>
                <td>
                    <my:a href="/order/detail/id=${order.id}" class="btn btn-primary">View</my:a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</jsp:attribute>
</my:pagetemplate>