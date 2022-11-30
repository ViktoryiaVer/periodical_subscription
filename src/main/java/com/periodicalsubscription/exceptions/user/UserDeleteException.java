package com.periodicalsubscription.exceptions.user;

/**
 * class of exception thrown when problem while deleting user occurs
 */
public class UserDeleteException extends UserServiceException{
    public UserDeleteException(String message) {
        super(message);
    }
}
