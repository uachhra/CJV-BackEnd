package com.example.movies.models;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends MongoRepository<Movie,String> {
    Optional<Movie> findByTitleContainingIgnoreCase(String title);
}
