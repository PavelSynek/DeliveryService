<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Employees">
<jsp:attribute name="body">

    <table class="table">
        <caption>Employees</caption>
        <thead>
        <tr>
            <th>id</th>
            <th>firstName</th>
            <th>surname</th>
            <th>email</th>
            <th>phone</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${employees}" var="employee">
            <tr>
                <td>${employee.id}</td>
                <td><c:out value="${employee.firstName}"/></td>
                <td><c:out value="${employee.surname}"/></td>
                <td><c:out value="${employee.email}"/></td>
                <td><c:out value="${employee.phone}"/></td>
                <td>
                    <my:a href="/employee/detail/id=${employee.id}" class="btn btn-primary">View</my:a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</jsp:attribute>
</my:pagetemplate>