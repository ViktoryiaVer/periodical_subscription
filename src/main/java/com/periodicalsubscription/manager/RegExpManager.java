package com.periodicalsubscription.manager;

public class RegExpManager {
    public static final String NAME = "^[A-Za-z-А-Яа-я]+";
    public static final String PASSWORD = "^(?=.*[\\d])"
            + "(?=.*[a-z])(?=.*[A-Z])"
            + "(?=.*[@#$%^&+=!])"
            + "(?=\\S+$)";
    public static final String PHONE = "\\+[0-9]{10,}";
}
