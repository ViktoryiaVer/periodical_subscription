package com.periodicalsubscription.dto;

import com.periodicalsubscription.manager.ErrorMessageManager;
import com.periodicalsubscription.manager.RegExpManager;
import lombok.Data;

import javax.validation.constraints.*;

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
    private String password;
    @NotBlank(message = ErrorMessageManager.PHONE_EMPTY)
    private String phoneNumber;
    private String avatarPath;
    @NotNull(message = ErrorMessageManager.ROLE_NOT_SPECIFIED)
    private RoleDto roleDto;

    public enum RoleDto {
        ADMIN,
        READER,
        GUEST
    }
    //TODO some validation if avatar is specified
}
