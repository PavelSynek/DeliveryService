<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Order">
<jsp:attribute name="body">

    <form method="post" action="${pageContext.request.contextPath}/order/delete/${order.id}">
        <button type="submit" class="btn btn-primary">Delete</button>
    </form>

    <table class="table">
        <thead>
        <tr>
            <th>id</th>
            <th>name</th>
            <th>added date</th>
            <th>weight</th>
            <th>price</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>${order.id}</td>
            <td><c:out value="${order.name}"/></td>
            <td><my:LocalDate date="${order.addedDate}"/></td>
            <td><c:out value="${order.weight}"/></td>
            <td><c:out value="${order.price}"/></td>
        </tr>
        </tbody>
    </table>


</jsp:attribute>
</my:pagetemplate>