package com.periodicalsubscription.exceptions.payment;

import com.periodicalsubscription.exceptions.ServiceException;

public class PaymentServiceException extends ServiceException {
    public PaymentServiceException(String message) {
        super(message);
    }

}
