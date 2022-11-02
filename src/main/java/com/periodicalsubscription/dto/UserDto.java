package com.periodicalsubscription.dto;

import com.periodicalsubscription.manager.ErrorMessageManager;
import com.periodicalsubscription.manager.RegExpManager;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Data
public class UserDto {
    private Long id;
    @NotBlank(message = ErrorMessageManager.FIRST_NAME_EMPTY)
    @Pattern(regexp = RegExpManager.NAME, message = ErrorMessageManager.FIRST_NAME_NOT_VALID)
    private String firstName;
    @NotBlank(message = ErrorMessageManager.LAST_NAME_EMPTY)
    @Pattern(regexp = RegExpManager.NAME, message = ErrorMessageManager.LAST_NAME_NOT_VALID)
    private String lastName;
    @NotBlank(message = ErrorMessageManager.EMAIL_EMPTY)
    @Email(message = ErrorMessageManager.EMAIL_NOT_VALID)
    private String email;
    @NotBlank(message = ErrorMessageManager.PASSWORD_EMPTY)
    @Pattern(regexp = RegExpManager.PASSWORD, message = ErrorMessageManager.PASSWORD_NOT_VALID)
    @Size(min = 8, max = 20, message = ErrorMessageManager.PASSWORD_LENGTH)
    @ToString.Exclude
    private String password;
    @NotBlank(message = ErrorMessageManager.PHONE_EMPTY)
    @Pattern(regexp = RegExpManager.PHONE, message = ErrorMessageManager.PHONE_NOT_VALID)
    @Size(min = 10, message = ErrorMessageManager.PHONE_LENGTH)
    private String phoneNumber;
    private String avatarPath;
    private RoleDto roleDto;

    public enum RoleDto {
        ADMIN,
        READER,
        GUEST
    }
}
