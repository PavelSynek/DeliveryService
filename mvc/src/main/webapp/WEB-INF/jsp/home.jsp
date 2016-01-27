<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="DeliveryService">
<jsp:attribute name="body">

        <p class="lead">Log in to continue</p>
        <p><a class="btn btn-lg btn-success" href="${pageContext.request.contextPath}/order/list"
              role="button">Login</a></p>

</jsp:attribute>
</my:pagetemplate>
