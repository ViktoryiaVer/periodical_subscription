package com.periodicalsubscription.exceptions.subscription;

/**
 * class of exception thrown when a problem while setting subscriptions status completed occurs
 */
public class SubscriptionCompletedStatusException extends SubscriptionServiceException {
    public SubscriptionCompletedStatusException(String message) {
        super(message);
    }
}
