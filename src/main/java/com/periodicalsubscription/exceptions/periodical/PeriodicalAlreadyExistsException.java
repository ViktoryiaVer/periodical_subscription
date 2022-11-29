package com.periodicalsubscription.exceptions.periodical;

public class PeriodicalAlreadyExistsException extends PeriodicalServiceException {
    public PeriodicalAlreadyExistsException(String message) {
        super(message);
    }
}
