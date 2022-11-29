package com.periodicalsubscription.exceptions.subscription;

import com.periodicalsubscription.exceptions.ServiceException;

public class SubscriptionServiceException extends ServiceException {
    public SubscriptionServiceException(String message) {
        super(message);
    }
}
