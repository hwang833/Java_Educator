package com.ztcollins.JavaEducator.controller;

import com.ztcollins.JavaEducator.exception.UserNotFoundException;
import com.ztcollins.JavaEducator.model.User;
import com.ztcollins.JavaEducator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * All the CRUD operations for the user.
 */
@RestController
@CrossOrigin("http://localhost:5173")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    /*
     * POST method for creating a new user.
     * If the new user has an existing email, don't create a new user.
     */
    @PostMapping("/user")
    String newUser(@RequestBody User newUser) {
        if(!userRepository.existsByEmail(newUser.getEmail())) {
            userRepository.save(newUser);
            return "new user created!";
        }
        else {
            return "user currently exists!";
        }
    }

    /*
     * GET method for reading all users.
     */
    @GetMapping("/users")
    List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    /* 
     * GET method for reading user by id.
    */
    @GetMapping("/user/{id}")
    User getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    /*
     * UPDATE method for updating a user by id.
     */
    /*@PutMapping("/user/{id}")
    User updateUser(@RequestBody User newUser, @PathVariable String email) {
    
        return userRepository.findByEmail(email)
                .map(user -> {
                    user.setOperatingMode(newUser.getOperatingMode());
                    user.setName(newUser.getName());
                    user.setEmail(newUser.getEmail());
                    user.setAdminStatus(newUser.getAdminStatus());
                    return userRepository.save(user);
                }).orElseThrow(() -> new UserNotFoundException(id));
    }*/

    public User findByEmail(@PathVariable String email){
        return userRepository.findByEmail(email);
    }

    /*
     * DELETE method for deleting a user by id
     */
    @DeleteMapping("/user/{id}")
    String deleteUser(@PathVariable Long id){
        if(!userRepository.existsById(id)){
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
        return  "User with id "+id+" has been deleted success.";
    }

	

}
