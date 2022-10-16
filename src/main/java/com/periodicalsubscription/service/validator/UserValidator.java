package com.periodicalsubscription.service.validator;

import com.periodicalsubscription.exceptions.SignUpException;
import com.periodicalsubscription.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {
    public void checkValidity(UserDto dto) {
        checkFirstName(dto.getFirstName());
        checkLastName(dto.getLastName());
        checkEmail(dto.getEmail());
        checkPassword(dto.getPassword());
        checkPhoneNumber(dto.getPhoneNumber());
    }

    private void checkFirstName(String firstName) {
        if(isNullOrBlank(firstName)) {
            throw new SignUpException("User's first name is not specified or empty");
        }
        if(!isNameValid(firstName)) {
            throw new SignUpException("User's first name is not valid");
        }
    }

    private void checkLastName(String lastName) {
        if(isNullOrBlank(lastName)) {
            throw new SignUpException("User's last name is not specified or empty");
        }
        if(!isNameValid(lastName)) {
            throw new SignUpException("User's last name is not valid");
        }
    }

    private void checkEmail(String email) {
        if(isNullOrBlank(email)) {
            throw new SignUpException("User's email is not specified or empty");
        }

        if(!isEmailValid(email)) {
            throw new SignUpException("User's email is not valid");
        }
    }

    private void checkPassword(String password) {
        if(isNullOrBlank(password)) {
            throw new SignUpException("User's password is not specified or empty");
        }

        if(!isPasswordValid(password)) {
            throw new SignUpException("User's password is not valid");
        }
    }

    private void checkPhoneNumber(String phoneNumber) {
        if(isNullOrBlank(phoneNumber)) {
            throw new SignUpException("User's phone number is not specified or empty");
        }
    }

    //TODO check of avatar path if specified?

    private boolean isNullOrBlank(String str) {
        return str == null || str.isBlank();
    }

    private boolean isNameValid(String name) {
        return name.matches("^[A-Za-z-А-Яа-я]+");
    }

    private boolean isEmailValid(String email) {
        return email.matches("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[^.а-яА-Я@]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
    }

    private boolean isPasswordValid(String password) {
        return password.matches("^(?=.*[\\d])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=!])"
                + "(?=\\S+$).{8,20}$");
    }
}
