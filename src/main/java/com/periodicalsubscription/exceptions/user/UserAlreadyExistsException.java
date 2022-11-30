package com.periodicalsubscription.exceptions.user;

/**
 * class of exception thrown when user already exists
 */
public class UserAlreadyExistsException extends UserServiceException{
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
