package com.periodicalsubscription.exceptions.periodicalcategory;

import com.periodicalsubscription.exceptions.ServiceException;

/**
 * class of exception thrown when a periodical category service problem occurs
 */
public class PeriodicalCategoryServiceException extends ServiceException {
    public PeriodicalCategoryServiceException(String message) {
        super(message);
    }
}
