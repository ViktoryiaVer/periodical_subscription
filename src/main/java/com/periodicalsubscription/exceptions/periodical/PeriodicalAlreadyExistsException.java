package com.periodicalsubscription.exceptions.periodical;

/**
 * class of exception thrown when periodical already exists
 */
public class PeriodicalAlreadyExistsException extends PeriodicalServiceException {
    public PeriodicalAlreadyExistsException(String message) {
        super(message);
    }
}
