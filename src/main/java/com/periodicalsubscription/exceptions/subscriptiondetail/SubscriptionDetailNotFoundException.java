package com.periodicalsubscription.exceptions.subscriptiondetail;

import com.periodicalsubscription.exceptions.subscription.SubscriptionServiceException;

public class SubscriptionDetailNotFoundException extends SubscriptionServiceException {
    public SubscriptionDetailNotFoundException() {
    }

    public SubscriptionDetailNotFoundException(String message) {
        super(message);
    }

    public SubscriptionDetailNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
