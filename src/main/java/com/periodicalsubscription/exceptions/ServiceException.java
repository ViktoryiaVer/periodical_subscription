package com.periodicalsubscription.exceptions;

/**
 * class of exception thrown when service problem occurs
 */
public class ServiceException extends RuntimeException {
    public ServiceException(String message) {
        super(message);
    }
}
