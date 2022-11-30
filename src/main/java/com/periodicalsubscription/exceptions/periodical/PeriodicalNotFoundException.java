package com.periodicalsubscription.exceptions.periodical;

/**
 * class of exception thrown when not able to find a periodical
 */
public class PeriodicalNotFoundException extends PeriodicalServiceException {
    public PeriodicalNotFoundException(String message) {
        super(message);
    }
}
