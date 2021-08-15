package com.example.movies.services;


import com.example.movies.models.UserModel;
import com.example.movies.models.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.MongoTemplate;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private MongoTemplate mongo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<UserModel> getUsers()
    {
        return repository.findAll();

    }

    public List<UserModel> getUser(UserModel userModel)
    {
        Query query = new Query();
        query.addCriteria(Criteria.where("username" ).is(userModel.getUsername()));
        query.addCriteria(Criteria.where("password" ).is(userModel.getPassword()));
        List<UserModel> userreturn=mongo.find(query, UserModel.class);

        return userreturn;
    }

    public Optional<UserModel> getAUser(String id)// throws Exception
    {

        Optional<UserModel> user = repository.findById(id);
        return user;

    }

    public List<UserModel> getUserByEmail(String email)// throws Exception
    {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(email));
        List<UserModel> user = mongo.find(query, UserModel.class);
        return user;
    }

    public void addUser(UserModel userModel)
    {
        String encodedPassword = bCryptPasswordEncoder.encode(userModel.getPassword());

        userModel.setPassword(encodedPassword);

        repository.insert(userModel);
    }
    public void deleteAUser( String id)
    {
        repository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel foundUserModel = repository.findByUsername(username);

        String userN = foundUserModel.getUsername();
        String password = foundUserModel.getPassword();

        return new User(userN, password, new ArrayList<>());
    }
}
