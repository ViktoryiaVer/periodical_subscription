package com.periodicalsubscription.exceptions.subscription;

public class SubscriptionCompletedStatusException extends SubscriptionServiceException {
    public SubscriptionCompletedStatusException() {
    }

    public SubscriptionCompletedStatusException(String message) {
        super(message);
    }

    public SubscriptionCompletedStatusException(String message, Throwable cause) {
        super(message, cause);
    }
}
