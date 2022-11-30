package com.periodicalsubscription.exceptions.user;

/**
 * class of exception thrown when not able to find a user
 */
public class UserNotFoundException extends UserServiceException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
