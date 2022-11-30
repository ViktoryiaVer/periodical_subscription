package com.periodicalsubscription.constant;

/**
 * class with regExp constants for bean validation
 */
public class RegExpConstant {
    public static final String NAME = "^[A-Za-z-А-Яа-я]+";
    public static final String USERNAME = "^(?=[a-zA-Z0-9._]{5,20}$)(?!.*[_.]{2})[^_.].*[^_.]$";
    public static final String PASSWORD = "^(?=.*[\\d])"
            + "(?=.*[a-z])(?=.*[A-Z])"
            + "(?=.*[@#$%^&+=!])"
            + "(?=\\S+$).{8,}$";
    public static final String PHONE = "\\+[0-9]{10,}";
}
