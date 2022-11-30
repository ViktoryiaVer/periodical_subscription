package com.periodicalsubscription.exceptions.payment;

import com.periodicalsubscription.exceptions.ServiceException;

/**
 * class of exception thrown when a payment service problem occurs
 */
public class PaymentServiceException extends ServiceException {
    public PaymentServiceException(String message) {
        super(message);
    }

}
