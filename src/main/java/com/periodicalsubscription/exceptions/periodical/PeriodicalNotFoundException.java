package com.periodicalsubscription.exceptions.periodical;

public class PeriodicalNotFoundException extends PeriodicalServiceException {
    public PeriodicalNotFoundException() {
    }

    public PeriodicalNotFoundException(String message) {
        super(message);
    }

    public PeriodicalNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
