/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.superbiz.moviefun.rest;

import org.superbiz.moviefun.Comment;
import org.superbiz.moviefun.Movie;
import org.superbiz.moviefun.MoviesBean;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Path("movies")
@Produces({"application/json"})
@ApplicationScoped
public class MoviesResource {

    private static final Logger LOGGER = Logger.getLogger(MoviesResource.class.getName());

    @EJB
    private MoviesBean service;

    @GET
    @Path("{id}")
    public Movie find(@PathParam("id") Long id) {
        return service.find(id);
    }

    @GET
    public List<Movie> getMovies(@QueryParam("first") Integer first, @QueryParam("max") Integer max,
                                 @QueryParam("field") String field, @QueryParam("searchTerm") String searchTerm) {
        return service.getMovies(first, max, field, searchTerm);
    }

    @POST
    @Consumes("application/json")
    public Movie addMovie(Movie movie) {
        service.addMovie(movie);
        return movie;
    }

    @POST
    @Path("{id}/comment")
    @Consumes("text/plain")
    public Movie addCommentToMovie(
            @PathParam("id") final long id,
            final String comment) {

        final Comment c = new Comment();
        c.setAuthor("username");
        c.setComment(comment);
        c.setEmail("username@superbiz.org");
        c.setTimestamp(new Date());

        return service.addCommentToMovie(id, c);
    }

    @PUT
    @Path("{id}")
    @Consumes("application/json")
    public Movie editMovie(
            @PathParam("id") final long id,
            Movie movie) {
        service.editMovie(movie);
        return movie;
    }

    @DELETE
    @Path("{id}")
    public void deleteMovie(@PathParam("id") long id) {
        service.deleteMovie(id);
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public int count(@QueryParam("field") String field, @QueryParam("searchTerm") String searchTerm) {
        return service.count(field, searchTerm);
    }

}