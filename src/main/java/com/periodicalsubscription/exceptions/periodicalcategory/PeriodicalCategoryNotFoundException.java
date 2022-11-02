package com.periodicalsubscription.exceptions.periodicalcategory;

public class PeriodicalCategoryNotFoundException extends PeriodicalCategoryServiceException {
    public PeriodicalCategoryNotFoundException() {
    }

    public PeriodicalCategoryNotFoundException(String message) {
        super(message);
    }

    public PeriodicalCategoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
