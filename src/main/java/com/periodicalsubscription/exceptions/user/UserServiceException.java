package com.periodicalsubscription.exceptions.user;

import com.periodicalsubscription.exceptions.ServiceException;

public class UserServiceException extends ServiceException {
    public UserServiceException(String message) {
        super(message);
    }
}
