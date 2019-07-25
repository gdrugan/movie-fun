<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="language" value="${pageContext.request.locale}"/>
<fmt:setLocale value="${language}"/>

<!DOCTYPE html>
<html lang="${language}">
<head>
    <meta charset="utf-8">
    <title>Moviefun</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <link href="assets/css/bootstrap.css" rel="stylesheet">
    <link href="assets/css/movie.css" rel="stylesheet">
    <style>
        body {
            padding-top: 60px;
            /* 60px to make the container go all the way to the bottom of the topbar */
        }
    </style>
    <link href="assets/css/bootstrap-responsive.css" rel="stylesheet">

</head>

<body>

<div class="navbar navbar-inverse navbar-fixed-top">
    <jsp:include page="../banner.jsp" flush="true"/>

    <form class="navbar-form pull-right">
        <select name="field">
            <option value="title">Title</option>
            <option value="director">Director</option>
            <option value="genre">Genre</option>
        </select> <input type="text" name="key" size="20">
        <button type="submit" class="btn">Search</button>
    </form>

    <!--/.nav-collapse -->
</div>
</div>
</div>

<div class="container">

    <h1>Moviefun</h1>
    <jsp:include page="../messages.jsp" flush="true"/>
    <form class="movie-input-form form-inline" action="moviefun"
          method="post">
        <p>Add Movie</p>
        <input type="text" name="title" placeholder="Title" size="29" value="${title}"/>
        <input type="text" name="director" placeholder="Director" size="17" value="${director}"/>
        <input type="text" name="genre" placeholder="Genre" size="14" value="${genre}"/>
        <input type="text" name="rating" placeholder="Rating" size="7" value="${rating}"/>
        <input type="text" name="year" placeholder="Year" size="4" style="width: 110px;" value="${year}"/>
        <input type="submit" name="action" class="btn btn-primary" value="Add"/>
        ${sessionScope.remove("title")}
        ${sessionScope.remove("director")}
        ${sessionScope.remove("genre")}
        ${sessionScope.remove("rating")}
        ${sessionScope.remove("year")}
    </form>

    <table class="table table-striped table-bordered">
        <thead>
        <tr>
            <th>Title</th>
            <th>Director</th>
            <th>Genre</th>
            <th>Rating</th>
            <th>Year</th>
            <c:if test="${count > 0}">
                <th style="width: 120px;text-align: center">
                    <a class="btn btn-primary" href="?action=Clean">Remove All</a>
                </th>
            </c:if>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${movies}" var="movie">
            <tr>
                <td><c:out value="${movie.title}"/></td>
                <td><c:out value="${movie.director}"/></td>
                <td><c:out value="${movie.genre}"/></td>
                <td><c:out value="${movie.rating}"/></td>
                <td><c:out value="${movie.year}"/></td>
                <td style="text-align: center"><a href="?action=Remove&id=${movie.id}"><i
                        class="icon-trash"></i></a></td>
            </tr>
        </c:forEach>
        </tbody>
        <c:if test="${count eq 0}">
            <tr class="">
                <td colspan="5" style="text-align: center">
                    No movies have been found in the database. Try adding a new one <i class="icon-heart"></i>
                </td>
            </tr>
        </c:if>
    </table>
    <c:if test="${count > 0}">
        <c:if test="${page > 1}">
            <a href="<c:url value="moviefun"><c:param name="page" value="${page - 1}"/><c:param name="field" value="${field}"/><c:param name="key" value="${key}"/></c:url>">&lt;
                Prev</a>&nbsp;
        </c:if>
        Showing records ${start} to ${end} of ${count}
        <c:if test="${page < pageCount}">
            &nbsp;<a href="<c:url value="moviefun"><c:param name="page" value="${page + 1}"/><c:param name="field"
                                                                                                      value="${field}"/><c:param
                name="key"
                value="${key}"/></c:url>">Next &gt;</a>
        </c:if>
    </c:if>
</div>
<!-- /container -->
</body>
</html>
