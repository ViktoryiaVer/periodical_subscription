package com.periodicalsubscription.exceptions.periodical;

public class PeriodicalDeleteException extends PeriodicalServiceException {
    public PeriodicalDeleteException() {
    }

    public PeriodicalDeleteException(String message) {
        super(message);
    }

    public PeriodicalDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}
