package com.periodicalsubscription.exceptions.user;

import com.periodicalsubscription.exceptions.ServiceException;

/**
 * class of exception thrown when a user service problem occurs
 */
public class UserServiceException extends ServiceException {
    public UserServiceException(String message) {
        super(message);
    }
}
