package com.periodicalsubscription.exceptions.periodicalcategory;

import com.periodicalsubscription.exceptions.ServiceException;

public class PeriodicalCategoryServiceException extends ServiceException {
    public PeriodicalCategoryServiceException() {
    }

    public PeriodicalCategoryServiceException(String message) {
        super(message);
    }

    public PeriodicalCategoryServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
