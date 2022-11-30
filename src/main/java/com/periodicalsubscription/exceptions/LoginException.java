package com.periodicalsubscription.exceptions;

/**
 * class of exception thrown when login problem occurs
 */
public class LoginException extends RuntimeException {
    public LoginException(String message) {
        super(message);
    }
}
