package com.periodicalsubscription.exceptions.periodical;

public class PeriodicalAlreadyExistsException extends PeriodicalServiceException {

    public PeriodicalAlreadyExistsException() {
    }

    public PeriodicalAlreadyExistsException(String message) {
        super(message);
    }

    public PeriodicalAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
