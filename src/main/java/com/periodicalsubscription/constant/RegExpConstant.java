package com.periodicalsubscription.constant;

public class RegExpConstant {
    public static final String NAME = "^[A-Za-z-А-Яа-я]+";
    public static final String PASSWORD = "^(?=.*[\\d])"
            + "(?=.*[a-z])(?=.*[A-Z])"
            + "(?=.*[@#$%^&+=!])"
            + "(?=\\S+$).{8,20}$";
    public static final String PHONE = "\\+[0-9]{10,}";
}
