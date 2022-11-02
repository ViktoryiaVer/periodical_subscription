package com.periodicalsubscription.exceptions.subscriptiondetail;

import com.periodicalsubscription.exceptions.ServiceException;

public class SubscriptionDetailServiceException extends ServiceException {
    public SubscriptionDetailServiceException() {
    }

    public SubscriptionDetailServiceException(String message) {
        super(message);
    }

    public SubscriptionDetailServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
