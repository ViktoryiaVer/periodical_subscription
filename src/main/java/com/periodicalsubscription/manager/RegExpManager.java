package com.periodicalsubscription.manager;

public class RegExpManager {
    public static final String NAME = "^[A-Za-z-А-Яа-я]+";
    public static final String PASSWORD = "^(?=.*[\\d])"
            + "(?=.*[a-z])(?=.*[A-Z])"
            + "(?=.*[@#$%^&+=!])"
            + "(?=\\S+$).{8,20}$";
}
