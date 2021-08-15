package com.example.movies.services;

import com.example.movies.models.MovieRepository;
import com.example.movies.models.Movie;

import com.example.movies.models.TVshow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {


    @Autowired
    private MongoTemplate mongo;
    @Autowired
    private MovieRepository repository;

    public List<Movie> getMovies()
    {
        return repository.findAll();
    }

    public Optional<Movie> getAMovie(String id) throws Exception
    {

        Optional<Movie> movie = repository.findById(id);

        if (!movie.isPresent())
        {
            throw new Exception (" Movie with " + id + " is not available ");
        }


        return movie;

    }

    public Optional<Movie> getMoviesByTitle(String title) throws Exception
    {

        Optional<Movie> movie= repository.findByTitleContainingIgnoreCase(title);

        if (!movie.isPresent())
        {
            throw new Exception ("Unavailable TV show " + title );
        }

        return movie;
    }
    public void addMovie(Movie movie)
    {

        repository.insert(movie);
    }

    public void deleteAMovie( String id)
    {
        repository.deleteById(id);
    }

    public List<Movie> getfeaturedMovies(Boolean r)

    {

        Query query = new Query();
        query.addCriteria(Criteria.where("featured" ).is(r));
        List<Movie> movies = mongo.find(query, Movie.class);
        return movies;
    }


    public Movie editMovie(String id, Movie newMovieData)
    {
          Optional<Movie> movie = repository.findById(id);

        if(movie.isPresent()) {
            movie.get().setTitle(newMovieData.getTitle());
            movie.get().setDescription(newMovieData.getDescription());
            movie.get().setRating(newMovieData.getRating());
            movie.get().setRentPrice(newMovieData.getRentPrice());
            movie.get().setBuyPrice(newMovieData.getBuyPrice());

            Movie updateMovie = repository.save(movie.get());
            return updateMovie;

        }
        else
        {
            Movie updateMovie = null;
            return updateMovie;
        }
    }
}
