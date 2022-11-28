package com.periodicalsubscription.constant;

public class HqlConstant {
    public static final String HQL_UPDATE_SUBSCRIPTION_START_DATE = "update SubscriptionDetail s set s.subscriptionStartDate = :date where s.id = :id";
    public static final String HQL_UPDATE_SUBSCRIPTION_END_DATE = "update SubscriptionDetail s set s.subscriptionEndDate = :date where s.id = :id";
    public static final String HQL_UPDATE_SUBSCRIPTION_STATUS = "update Subscription s set s.status = :status where s.id = :id";
    public static final String HQL_UPDATE_PERIODICAL_STATUS = "update Periodical p set p.status = :status where p.id = :id";
    public static final String HQL_GET_USER_PASSWORD = "select u.password FROM User u where u.id = :id";
}
