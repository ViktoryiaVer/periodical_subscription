package com.periodicalsubscription.util.constant;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TestObjectConstant {
    public static final String USER_FIRSTNAME = "Test";
    public static final String USER_LASTNAME = "Test";
    public static final String USER_EMAIL = "Test@mail.ru";
    public static final String USER_PASSWORD = "TestTest1234!";
    public static final String USER_PHONE_NUMBER = "+375333333333";
    public static final String USER_ROLE = "READER";
    public static final Long USER_ID = 1L;

    public static final String PERIODICAL_TITLE = "Test periodical";
    public static final String PERIODICAL_PUBLISHER = "Test publisher";
    public static final String PERIODICAL_DESCRIPTION = "Test periodical description";
    public static final Integer PERIODICAL_ISSUES = 51;
    public static final Double PERIODICAL_PRICE = 123.12;
    public static final String PERIODICAL_LANGUAGE = "English";
    public static final String PERIODICAL_IMAGE = "test_periodical.jpg";
    public static final String PERIODICAL_TYPE = "MAGAZINE";
    public static final String PERIODICAL_STATUS = "AVAILABLE";
    public static final String PERIODICAL_CATEGORY = "SCIENCE";
    public static final Long PERIODICAL_ID = 1L;
    public static final Long PERIODICAL_ID_SECOND = 2L;

    public static final Long SUBSCRIPTION_ID = 1L;
    public static final LocalDate SUBSCRIPTION_DETAIL_START_DATE = LocalDate.of(2020, 1, 1);
    public static final LocalDate SUBSCRIPTION_DETAIL_END_DATE = LocalDate.of(2020, 12, 31);
    public static final Integer SUBSCRIPTION_DETAIL_DURATION = 1;
    public static final Long SUBSCRIPTION_DETAIL_ID = 1L;

    public static final LocalDateTime PAYMENT_DATETIME = LocalDateTime.of(2020, 1, 15, 15, 0);
    public static final String PAYMENT_METHOD = "CREDIT_OR_DEBIT_CARD";
    public static final Long PAYMENT_ID = 1L;
}
