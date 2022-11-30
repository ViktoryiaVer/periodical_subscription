package com.periodicalsubscription.exceptions.periodical;

/**
 * class of exception thrown when problem while deleting periodical occurs
 */
public class PeriodicalDeleteException extends PeriodicalServiceException {
    public PeriodicalDeleteException(String message) {
        super(message);
    }
}
