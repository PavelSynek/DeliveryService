<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Customer">
<jsp:attribute name="body">

    <form method="post" action="${pageContext.request.contextPath}/customer/delete/${customer.id}">
        <button type="submit" class="btn btn-primary">Delete</button>
    </form>


    <table class="table">
        <thead>
        <tr>
            <th>id</th>
            <th>first name</th>
            <th>surname</th>
            <th>email</th>
            <th>phone</th>
            <th>address</th>
            <th>registration date</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>${customer.id}</td>
            <td><c:out value="${customer.firstName}"/></td>
            <td><c:out value="${customer.surname}"/></td>
            <td><c:out value="${customer.email}"/></td>
            <td><c:out value="${customer.phone}"/></td>
            <td><c:out value="${customer.address}"/></td>
            <td><fmt:formatDate value="${customer.registrationDate}" pattern="yyyy-MM-dd"/></td>
        </tr>
        </tbody>
    </table>

        <div class="row">
            <table class="table">
                <caption>Orders</caption>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Number of items</th>
                    <th>Created</th>
                    <th>State</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${orders}" var="order">
                    <tr>
                        <td>${order.id}</td>
                        <td><c:out value="${order.products.lenght()}"/></td>
                        <td><fmt:formatDate value="${order.created}" pattern="yyyy-MM-dd"/></td>
                        <td><c:out value="${order.state}"/></td>
                        <td>
                            <my:a href="/order/detail/${order.id}" class="btn btn-primary">View</my:a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        </div>

</jsp:attribute>
</my:pagetemplate>