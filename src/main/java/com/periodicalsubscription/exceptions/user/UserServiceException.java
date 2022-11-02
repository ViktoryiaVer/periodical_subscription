package com.periodicalsubscription.exceptions.user;

import com.periodicalsubscription.exceptions.ServiceException;

public class UserServiceException extends ServiceException {
    public UserServiceException() {
    }

    public UserServiceException(String message) {
        super(message);
    }

    public UserServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
