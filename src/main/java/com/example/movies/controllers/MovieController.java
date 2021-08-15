package com.example.movies.controllers;

import com.example.movies.CustomizedResponse;
import com.example.movies.models.Movie;
import com.example.movies.models.TVshow;
import com.example.movies.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class MovieController {

    @Autowired
    private MovieService service;

    @GetMapping("/movies")
    public ResponseEntity getMovies()
    {
        return new ResponseEntity( service.getMovies(), HttpStatus.OK);
    }

     @GetMapping("/movies/{id}")
    public ResponseEntity getAMovie(@PathVariable("id") String id) {

        Optional<Movie> movie=null;

        try {
            movie=service.getAMovie(id);

        } catch (Exception e) {

            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(movie, HttpStatus.OK);
    }

    @GetMapping("/movies/name/{title}")
    public ResponseEntity getTVshowsByTitle(@PathVariable("title") String title) {

        Optional<Movie> movie=null;
        try {
            movie=service.getMoviesByTitle(title);

        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(movie, HttpStatus.OK);
    }

    @PostMapping(value= "/movies", consumes={MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity  addMovie(@RequestBody Movie movie)
    {
        service.addMovie(movie);
        return new ResponseEntity(movie,HttpStatus.OK);

    }

    @GetMapping("/movies/featured")
    public ResponseEntity getfeaturedMovies()
    {

        return new ResponseEntity(service.getfeaturedMovies(true), HttpStatus.OK);
    }

    @PutMapping(value = "/movies/{id}", consumes = {
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity editMovie(@PathVariable("id") String id, @RequestBody Movie newMovie )

    {
        var customizedResponse = new CustomizedResponse(" Updated Movie with ID:  " + id , Collections.singletonList(service.editMovie(id, newMovie)));

        if((service.editMovie(id, newMovie)==null))
        {
            return new ResponseEntity("Invalid Id", HttpStatus.NOT_FOUND);
        }
        else
        {
            return new ResponseEntity(customizedResponse, HttpStatus.OK);
        }
    }

    @DeleteMapping("/movies/{id}")
    public ResponseEntity deleteAMovie(@PathVariable("id") String id)
    {

        service.deleteAMovie(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
