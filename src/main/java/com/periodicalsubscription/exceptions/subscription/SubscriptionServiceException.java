package com.periodicalsubscription.exceptions.subscription;

import com.periodicalsubscription.exceptions.ServiceException;

public class SubscriptionServiceException extends ServiceException {
    public SubscriptionServiceException() {
    }

    public SubscriptionServiceException(String message) {
        super(message);
    }

    public SubscriptionServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
