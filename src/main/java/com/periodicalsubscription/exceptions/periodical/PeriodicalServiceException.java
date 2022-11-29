package com.periodicalsubscription.exceptions.periodical;

import com.periodicalsubscription.exceptions.ServiceException;

public class PeriodicalServiceException extends ServiceException {
    public PeriodicalServiceException(String message) {
        super(message);
    }
}
