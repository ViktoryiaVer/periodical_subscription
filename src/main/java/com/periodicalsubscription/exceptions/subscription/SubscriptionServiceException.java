package com.periodicalsubscription.exceptions.subscription;

import com.periodicalsubscription.exceptions.ServiceException;

/**
 * class of exception thrown when a subscription service problem occurs
 */
public class SubscriptionServiceException extends ServiceException {
    public SubscriptionServiceException(String message) {
        super(message);
    }
}
