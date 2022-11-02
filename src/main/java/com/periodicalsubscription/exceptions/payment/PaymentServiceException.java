package com.periodicalsubscription.exceptions.payment;

import com.periodicalsubscription.exceptions.ServiceException;

public class PaymentServiceException extends ServiceException {
    public PaymentServiceException() {
    }

    public PaymentServiceException(String message) {
        super(message);
    }

    public PaymentServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
