package com.periodicalsubscription.exceptions.subscription;

/**
 * class of exception thrown when not able to find a subscription
 */
public class SubscriptionNotFoundException extends SubscriptionServiceException {
    public SubscriptionNotFoundException(String message) {
        super(message);
    }
}
