package com.periodicalsubscription.exceptions.periodical;

import com.periodicalsubscription.exceptions.ServiceException;

public class PeriodicalServiceException extends ServiceException {
    public PeriodicalServiceException() {
    }

    public PeriodicalServiceException(String message) {
        super(message);
    }

    public PeriodicalServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
