package com.ztcollins.JavaEducator.exception;

/*
 * I haven't gotten far enough in the video to explain this yet.
 */
public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Long id){
        super("Could not found the user with id "+ id);
    }
}
