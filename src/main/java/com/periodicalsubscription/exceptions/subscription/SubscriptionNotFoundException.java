package com.periodicalsubscription.exceptions.subscription;

public class SubscriptionNotFoundException extends SubscriptionServiceException {
    public SubscriptionNotFoundException(String message) {
        super(message);
    }
}
