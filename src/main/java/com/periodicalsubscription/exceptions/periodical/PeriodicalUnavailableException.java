package com.periodicalsubscription.exceptions.periodical;

public class PeriodicalUnavailableException extends PeriodicalServiceException {
    public PeriodicalUnavailableException() {
    }

    public PeriodicalUnavailableException(String message) {
        super(message);
    }

    public PeriodicalUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
