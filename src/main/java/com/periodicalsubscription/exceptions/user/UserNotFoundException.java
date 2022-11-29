package com.periodicalsubscription.exceptions.user;

public class UserNotFoundException extends UserServiceException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
