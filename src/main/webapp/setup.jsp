<%@ page import=" org.superbiz.moviefun.Movie" %>
<%@ page import="org.superbiz.moviefun.MoviesBean" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    InitialContext initialContext = new InitialContext();
    MoviesBean moviesBean = (MoviesBean) initialContext.lookup("java:comp/env/org.superbiz.moviefun.ActionServlet/moviesBean");

    moviesBean.addMovie(new Movie("Wedding Crashers", "David Dobkin", "Comedy", 7, 2005));
    moviesBean.addMovie(new Movie("Starsky & Hutch", "Todd Phillips", "Action", 6, 2004));
    moviesBean.addMovie(new Movie("Shanghai Knights", "David Dobkin", "Action", 6, 2003));
    moviesBean.addMovie(new Movie("I-Spy", "Betty Thomas", "Adventure", 5, 2002));
    moviesBean.addMovie(new Movie("The Royal Tenenbaums", "Wes Anderson", "Comedy", 8, 2001));
    moviesBean.addMovie(new Movie("Zoolander", "Ben Stiller", "Comedy", 6, 2001));
    moviesBean.addMovie(new Movie("Shanghai Noon", "Tom Dey", "Comedy", 7, 2000));
%>
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
    <jsp:include page="banner.jsp" flush="true"/>
</div>
<div class="container">

    <h1>Moviefun</h1>

    <h2>Seeded Database with the Following movies</h2>
    <table width="500">
        <tr>
            <td><b>Title</b></td>
            <td><b>Director</b></td>
            <td><b>Genre</b></td>
        </tr>
        <%
            List<Movie> movies = moviesBean.getMovies();
            for (Iterator<Movie> iterator = movies.iterator(); iterator.hasNext(); ) {
                Movie movie = (Movie) iterator.next();
        %>
        <tr>
            <td><%=movie.getTitle()%>
            </td>
            <td><%=movie.getDirector()%>
            </td>
            <td><%=movie.getGenre()%>
            </td>
        </tr>

        <%
            }
        %>
    </table>

    <h2>Continue</h2>
    <a href="moviefun">Go to main app</a>
</div>
<!-- /container -->
</body>
</html>
