package com.periodicalsubscription.exceptions.subscription;

public class SubscriptionNotFoundException extends SubscriptionServiceException {
    public SubscriptionNotFoundException() {
    }

    public SubscriptionNotFoundException(String message) {
        super(message);
    }

    public SubscriptionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
