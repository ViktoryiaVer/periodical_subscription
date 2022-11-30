package com.periodicalsubscription.exceptions.payment;

/**
 * class of exception thrown when not able to find a payment
 */
public class PaymentNotFoundException extends PaymentServiceException {
    public PaymentNotFoundException(String message) {
        super(message);
    }
}
