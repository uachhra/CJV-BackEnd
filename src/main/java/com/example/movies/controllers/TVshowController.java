package com.example.movies.controllers;

import com.example.movies.CustomizedResponse;
import com.example.movies.models.Movie;
import com.example.movies.models.TVshow;
import com.example.movies.services.TVshowService;
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
public class TVshowController {
        @Autowired
        private TVshowService tvSeries;

        @GetMapping("/tvshows")
        public ResponseEntity<TVshow> getTVshows()
        {
            return new ResponseEntity(tvSeries.getTVshows(),HttpStatus.OK);

        }

        @GetMapping("/tvshows/{id}")
        public ResponseEntity getTvshowById(@PathVariable("id") String id) {

                Optional<TVshow> tvshow=null;
                try {
                        tvshow=tvSeries.getTvshowById(id);

                } catch (Exception e) {
                        return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity(tvshow, HttpStatus.OK);
        }

        @PostMapping(value= "/tvshows", consumes={MediaType.APPLICATION_JSON_VALUE})
        public ResponseEntity addTVshow(@RequestBody TVshow tvshow)
        {
                tvSeries.addTVshow(tvshow);
                return new ResponseEntity(tvshow,HttpStatus.OK);

        }

        @GetMapping("/tvshows/featured")
        public ResponseEntity getfeaturedTVshows()
        {
                return new ResponseEntity(tvSeries.getfeaturedTVshows(true), HttpStatus.OK);
        }

        @GetMapping("/tvshows/name/{title}")
        public ResponseEntity getTVshowsByTitle(@PathVariable("title") String title) {

                Optional<TVshow> tvshow=null;
                try {
                        tvshow=tvSeries.getTVshowsByTitle(title);

                } catch (Exception e) {
                        return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity(tvshow, HttpStatus.OK);
        }

        @PutMapping(value="/tvshows/{id}", consumes={MediaType.APPLICATION_JSON_VALUE})
        public ResponseEntity editTVshow(@PathVariable("id") String id,@RequestBody TVshow newTVshow)
        {
                var customizedResponse = new CustomizedResponse(" Updated TVShow with ID:  " + id , Collections.singletonList(tvSeries.editTVshow(id, newTVshow)));

                if((tvSeries.editTVshow(id, newTVshow)==null))
                {
                        return new ResponseEntity("Invalid Id", HttpStatus.NOT_FOUND);
                }
                else
                {
                        return new ResponseEntity(customizedResponse, HttpStatus.OK);
                }

        }

        @DeleteMapping("/tvshows/{id}")
        public ResponseEntity deleteATVshow(@PathVariable("id") String id)
        {
                tvSeries.deleteATVshow(id);
                return new ResponseEntity(HttpStatus.OK);


        }
}
