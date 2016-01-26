<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="New order">
<jsp:attribute name="body">

    <form:form method="post" action="${pageContext.request.contextPath}/order/create" cssClass="form-horizontal">
               <%--modelAttribute="orderCreate" --%>


        <my:a href="/product/new" class="btn btn-primary">
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
            New product
        </my:a>

        <table class="table">
            <caption>Products in order:</caption>
            <thead>
            <tr>
                <%--<th>Unique ID</th>--%>
                <th>Product Name</th>
                <th>Date of Takeover</th>
                <th>Weight</th>
                <th>Price</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${products}" var="product">
                <tr>
                    <%--<td>${product.id}</td>--%>
                    <td><c:out value="${product.name}"/></td>
                    <td><my:LocalDate date="${product.addedDate}"/></td>
                    <td><c:out value="${product.weight}"/></td>
                    <td><c:out value="${product.price}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>


        <button class="btn btn-primary" type="submit">Create order</button>
    </form:form>

</jsp:attribute>
</my:pagetemplate>