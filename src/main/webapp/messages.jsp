<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<c:if test="${errorMessage ne null}">
    <h3 style="color: red">${errorMessage}</h3>
    ${sessionScope.remove("errorMessage")}
</c:if>
<c:if test="${infoMessage ne null}">
    <h3 style="color: darkolivegreen">${infoMessage}</h3>
    ${sessionScope.remove("infoMessage")}
</c:if>
