package com.example.movies.services;

import com.example.movies.models.TVshow;
import com.example.movies.models.TVshowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TVshowService {

    @Autowired
    private TVshowRepository repository;
    @Autowired
    private MongoTemplate mongo;

    public List<TVshow> getTVshows()
    {
        return repository.findAll();
    }

    public Optional<TVshow> getTvshowById(String id) throws Exception
    {

        Optional<TVshow> tvshow= repository.findById(id);

        if (!tvshow.isPresent())
        {
            throw new Exception ("Unavailable TV show " + id );
        }

        return tvshow;
    }

    public void addTVshow(TVshow tvshow)
    {

        repository.insert(tvshow);
    }


    public Optional<TVshow> getTVshowsByTitle(String title) throws Exception
    {

        Optional<TVshow> tvshow= repository.findByTitleContainingIgnoreCase(title);

        if (!tvshow.isPresent())
        {
            throw new Exception ("Unavailable TV show " + title );
        }

        return tvshow;
    }

    public List<TVshow> getfeaturedTVshows(Boolean r)

    {
        Query query = new Query();
        query.addCriteria(Criteria.where("featured" ).is(r));
        List<TVshow> tvshows = mongo.find(query, TVshow.class);
        return tvshows;

    }
    public TVshow editTVshow(String id,TVshow newtvshow)
    {
        Optional<TVshow> tvshow = repository.findById(id);

        if(tvshow.isPresent()) {
            tvshow.get().setTitle(newtvshow.getTitle());
            tvshow.get().setDescription(newtvshow.getDescription());
            tvshow.get().setRating(newtvshow.getRating());
            tvshow.get().setRentPrice(newtvshow.getRentPrice());
            tvshow.get().setBuyPrice(newtvshow.getBuyPrice());

            TVshow updateTVshow = repository.save(tvshow.get());
            return updateTVshow;

        }
        else
        {
            TVshow updateTVshow = null;
            return updateTVshow;
        }

    }

    public void deleteATVshow( String id)
    {

        repository.deleteById(id);
    }
}
