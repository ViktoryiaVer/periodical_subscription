package com.periodicalsubscription.exceptions.payment;

public class PaymentNotFoundException extends PaymentServiceException {
    public PaymentNotFoundException(String message) {
        super(message);
    }
}
