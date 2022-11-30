package com.periodicalsubscription.exceptions.periodical;

import com.periodicalsubscription.exceptions.ServiceException;

/**
 * class of exception thrown when a periodical service problem occurs
 */
public class PeriodicalServiceException extends ServiceException {
    public PeriodicalServiceException(String message) {
        super(message);
    }
}
