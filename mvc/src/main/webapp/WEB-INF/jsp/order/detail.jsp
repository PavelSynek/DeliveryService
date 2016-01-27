<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<my:pagetemplate title="Order">
<jsp:attribute name="body">

    <form method="post" action="${pageContext.request.contextPath}/order/delete/${order.id}">
        <button type="submit" class="btn btn-primary">Cancel Order</button>
    </form>

    <c:if test="${isEmployee}">

        <form method="post" action="${pageContext.request.contextPath}/order/assign/${order.id}">
            <button type="submit" class="btn btn-primary">Assign order</button>
        </form>

    </c:if>

    <c:if test="${isEmployee && canShip}">

        <form method="post" action="${pageContext.request.contextPath}/order/ship/${order.id}">
            <button type="submit" class="btn btn-primary">Ship order</button>
        </form>

    </c:if>

    <%--<th>products</th>--%>
    <%--<c:forEach begin="0" end="${fn:length(centralityList) - 1}" var="index">--%>
    <%--<tr>--%>
    <%--<td><c:out value="${centralityList[index]}"/></td>--%>
    <%--<td><c:out value="${labelList[index]}"/></td>--%>
    <%--</tr>--%>
    <%--</c:forEach>--%>


    <table class="table table-striped">
        <caption>Summary</caption>
        <thead>
        <tr>
            <th>Unique ID</th>
            <th>Assigned Employee</th>
            <th>State</th>
            <th>Customer</th>
            <th>Date of Creation</th>
            <th>Total Weight</th>
            <th>Total Price</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>${order.id}</td>
            <td><c:out value="${order.employee.firstName} ${order.employee.surname}"/></td>
            <td><c:out value="${order.state}"/></td>
            <td><c:out value="${order.customer.firstName} ${order.customer.surname}"/></td>
            <td><my:LocalDate date="${order.created}"/></td>
            <td>${weight} kg</td>
            <td>${price} €</td>
        </tr>
        </tbody>
    </table>

    <table class="table table-striped">
        <caption>Ordered Products</caption>
        <thead>
        <tr>
            <th>Unique ID</th>
            <th>Product Name</th>
            <th>Date of Takeover</th>
            <th>Weight</th>
            <th>Price</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${products}" var="product">
            <tr>
                <td>${product.id}</td>
                <td><c:out value="${product.name}"/></td>
                <td><my:LocalDate date="${product.addedDate}"/></td>
                <td><c:out value="${product.weight}"/> kg</td>
                <td><c:out value="${product.price}"/> €</td>
                <td>
                    <my:a href="/product/detail/id=${product.id}" class="btn btn-primary">View</my:a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>


</jsp:attribute>
</my:pagetemplate>