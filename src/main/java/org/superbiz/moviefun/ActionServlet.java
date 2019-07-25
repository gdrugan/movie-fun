/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.superbiz.moviefun;

import org.apache.commons.lang.StringUtils;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @version $Revision$ $Date$
 */
public class ActionServlet extends HttpServlet {

    private static final long serialVersionUID = -5832176047021911038L;

    public static int PAGE_SIZE = 5;

    @EJB
    private MoviesBean moviesBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("Add".equals(action)) {
            String message = null;
            for (int i = 0; i <= 0; i++) {
                String title = request.getParameter("title");
                request.getSession().setAttribute("title", request.getParameter("title"));
                if (StringUtils.isEmpty(title)) {
                    message = "You must add a title!";
                    break;
                }
                String director = request.getParameter("director");
                request.getSession().setAttribute("director", request.getParameter("director"));
                if (StringUtils.isEmpty(director)) {
                    message = "You must add a director!";
                    break;
                }
                String genre = request.getParameter("genre");
                request.getSession().setAttribute("genre", request.getParameter("genre"));
                if (StringUtils.isEmpty(genre)) {
                    message = "You must add a genre!";
                    break;
                }
                int rating = 0;
                try {
                    request.getSession().setAttribute("rating", request.getParameter("rating"));
                    rating = Integer.parseInt(request.getParameter("rating"));
                } catch (Exception e) {
                    if (StringUtils.isEmpty(request.getParameter("rating"))) {
                        message = "You must add a rating!";
                        break;
                    }
                    message = "Invalid rating score!";
                    break;
                }
                int year = 0;
                try {
                    request.getSession().setAttribute("year", request.getParameter("year"));
                    year = Integer.parseInt(request.getParameter("year"));
                } catch (Exception e) {
                    if (StringUtils.isEmpty(request.getParameter("year"))) {
                        message = "You must add a year!";
                        break;
                    }
                    message = "Invalid year!";
                    break;
                }

                Movie movie = new Movie(title, director, genre, rating, year);
                moviesBean.addMovie(movie);
                request.getSession().setAttribute("infoMessage", "Movie added successfully.");
                request.getSession().removeAttribute("title");
                request.getSession().removeAttribute("director");
                request.getSession().removeAttribute("genre");
                request.getSession().removeAttribute("rating");
                request.getSession().removeAttribute("year");
            }
            request.getSession().setAttribute("errorMessage", message);
            response.sendRedirect("moviefun");
            return;
        } else if ("Remove".equals(action)) {
            String[] ids = request.getParameterValues("id");
            for (String id : ids) {
                moviesBean.deleteMovieId(new Long(id));
            }
            request.getSession().setAttribute("infoMessage", "Movie has been deleted.");
            response.sendRedirect("moviefun");
            return;

        } else if ("Clean".equals(action)) {
            moviesBean.clean();
            request.getSession().setAttribute("infoMessage", "All movies have been deleted.");
            response.sendRedirect("moviefun");
            return;

        } else {
            String key = request.getParameter("key");
            String field = request.getParameter("field");

            int count = 0;

            if (StringUtils.isEmpty(key) || StringUtils.isEmpty(field)) {
                count = moviesBean.countAll();
                key = "";
                field = "";
            } else {
                count = moviesBean.count(field, key);
            }

            int page = 1;

            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (Exception e) {
            }

            int pageCount = (count / PAGE_SIZE);
            if (pageCount == 0 || count % PAGE_SIZE != 0) {
                pageCount++;
            }

            if (page < 1) {
                page = 1;
            }

            if (page > pageCount) {
                page = pageCount;
            }

            int start = (page - 1) * PAGE_SIZE;
            List<Movie> range;

            if (StringUtils.isEmpty(key) || StringUtils.isEmpty(field)) {
                range = moviesBean.findAll(start, PAGE_SIZE);
            } else {
                range = moviesBean.findRange(field, key, start, PAGE_SIZE);
            }

            int end = start + range.size();

            request.setAttribute("count", count);
            request.setAttribute("start", start + 1);
            request.setAttribute("end", end);
            request.setAttribute("page", page);
            request.setAttribute("pageCount", pageCount);
            request.setAttribute("movies", range);
            request.setAttribute("key", key);
            request.setAttribute("field", field);
        }

        request.getRequestDispatcher("WEB-INF/moviefun.jsp").forward(request, response);
    }

}
