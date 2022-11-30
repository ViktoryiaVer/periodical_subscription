package com.periodicalsubscription.exceptions.periodical;

/**
 * class of exception thrown when periodical unavailable problem occurs
 */
public class PeriodicalUnavailableException extends PeriodicalServiceException {
    public PeriodicalUnavailableException(String message) {
        super(message);
    }
}
