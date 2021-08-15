package com.example.movies.models;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TVshowRepository extends MongoRepository<TVshow,String> {
    Optional<TVshow> findByTitleContainingIgnoreCase(String title);
}
