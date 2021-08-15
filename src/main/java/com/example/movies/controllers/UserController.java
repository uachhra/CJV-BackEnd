package com.example.movies.controllers;

import com.example.movies.models.UserModel;
import com.example.movies.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UserController {


    @Autowired
    private UserService user;

    @GetMapping("/users")
    public ResponseEntity<UserModel> getUsers()
    {
        return new ResponseEntity(user.getUsers(),HttpStatus.OK);

    }

    @GetMapping(value="/users/authenticate", consumes={MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserModel> getUser(@RequestBody UserModel userModel)
    {
        return new ResponseEntity(user.getUser(userModel),HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity getAUser(@PathVariable("id") String id) {

        return new ResponseEntity(user.getAUser(id), HttpStatus.OK);

    }

    @GetMapping("/users/email/{email}")

    public ResponseEntity getUserByEmail(@PathVariable("email")  String email){
        return new ResponseEntity(user.getUserByEmail(email), HttpStatus.OK);

    }
    @PostMapping(value= "/users", consumes={MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity addUser(@RequestBody UserModel userModel)
    {
        user.addUser(userModel);
        return new ResponseEntity(userModel,HttpStatus.OK);

    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteAUser(@PathVariable("id") String id)
    {

        user.deleteAUser(id);
        return new ResponseEntity(HttpStatus.OK);

    }
}
