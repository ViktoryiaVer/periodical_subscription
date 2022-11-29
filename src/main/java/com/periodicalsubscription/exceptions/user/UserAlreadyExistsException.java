package com.periodicalsubscription.exceptions.user;

public class UserAlreadyExistsException extends UserServiceException{
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
