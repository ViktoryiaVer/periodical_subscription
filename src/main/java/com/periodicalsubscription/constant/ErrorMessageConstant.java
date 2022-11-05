package com.periodicalsubscription.constant;

public class ErrorMessageConstant {
    //validation for user
    public static final String FIRST_NAME_EMPTY = "User's first name is not specified or empty";
    public static final String FIRST_NAME_NOT_VALID = "User's first name is not valid";
    public static final String LAST_NAME_EMPTY = "User's last name is not specified or empty";
    public static final String LAST_NAME_NOT_VALID = "User's last name is not valid";
    public static final String EMAIL_EMPTY = "User's email is not specified or empty";
    public static final String EMAIL_NOT_VALID = "User's email is not valid";
    public static final String PASSWORD_EMPTY = "User's password is not specified or empty";
    public static final String PASSWORD_NOT_VALID = "User's password is not valid";
    public static final String PASSWORD_LENGTH = "User's password is less than 8 symbols or more than 20 symbols";
    public static final String PHONE_EMPTY = "User's phone number is not specified or empty";
    public static final String PHONE_NOT_VALID = "User's phone number is not valid";
    public static final String PHONE_LENGTH = "User's phone number is too short";

    public static final String LOGIN_DATA_NOT_SPECIFIED = "Email or password are not specified";
    public static final String LOGIN_REQUIRED_SUBSCRIPTION = "You need to login for completing subscription";

    //validation for periodical
    public static final String PERIODICAL_TITLE_EMPTY = "Periodical's title is not specified or empty";
    public static final String PERIODICAL_PUBLISHER_EMPTY = "Periodical's publisher is not specified or empty";
    public static final String PERIODICAL_DESCRIPTION_EMPTY = "Periodical's description is not specified or empty";
    public static final String PERIODICAL_PUBLICATION_DATE_EMPTY = "Periodical's publication date is not specified";
    public static final String PERIODICAL_ISSUES_AMOUNT_EMPTY = "Periodical's issues amount is not specified";
    public static final String PERIODICAL_ISSUES_AMOUNT_NOT_VALID = "Periodical's issues amount is not valid";
    public static final String PERIODICAL_PRICE_EMPTY = "Periodical's price is not specified";
    public static final String PERIODICAL_PRICE_MIN = "Periodical's price is 0 or negative";
    public static final String PERIODICAL_PRICE_MAX = "Periodical's price is larger than 9999.99";
    public static final String PERIODICAL_PRICE_INVALID = "Periodical's price is invalid";
    public static final String PERIODICAL_LANGUAGE_EMPTY = "Periodical's language is not specified or empty";


    //not found messages
    public static final String USERS_NOT_FOUND = "No users could be found";
    public static final String PERIODICALS_NOT_FOUND = "No periodicals could be found";
    public static final String SUBSCRIPTIONS_NOT_FOUND = "No subscriptions could be found";
    public static final String SUBSCRIPTIONS_USER_NOT_FOUND = "You haven't ordered any subscriptions yet";
    public static final String PAYMENTS_NOT_FOUND = "No payments could be found";


}
