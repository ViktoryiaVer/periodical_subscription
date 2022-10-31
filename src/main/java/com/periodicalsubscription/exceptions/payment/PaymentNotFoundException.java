package com.periodicalsubscription.exceptions.payment;

public class PaymentNotFoundException extends PaymentServiceException {
    public PaymentNotFoundException() {
    }

    public PaymentNotFoundException(String message) {
        super(message);
    }

    public PaymentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
