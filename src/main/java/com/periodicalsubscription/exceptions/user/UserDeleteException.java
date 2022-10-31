package com.periodicalsubscription.exceptions.user;

public class UserDeleteException extends UserServiceException{
    public UserDeleteException() {
    }

    public UserDeleteException(String message) {
        super(message);
    }

    public UserDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}
