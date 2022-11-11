package com.periodicalsubscription.constant;

public class HqlConstant {
    public static final String HQL_UPDATE_SUBSCRIPTION_START_DATE = "update SubscriptionDetail s set s.subscriptionStartDate = :date where s.id = :id";
    public static final String HQL_UPDATE_SUBSCRIPTION_END_DATE = "update SubscriptionDetail s set s.subscriptionEndDate = :date where s.id = :id";
    public static final String HQL_UPDATE_SUBSCRIPTION_STATUS = "update Subscription s set s.status = :status where s.id = :id";
}
