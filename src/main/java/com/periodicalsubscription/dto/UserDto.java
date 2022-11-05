package com.periodicalsubscription.dto;

import com.periodicalsubscription.constant.ErrorMessageConstant;
import com.periodicalsubscription.constant.RegExpConstant;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Data
public class UserDto {
    private Long id;
    @NotBlank(message = ErrorMessageConstant.FIRST_NAME_EMPTY)
    @Pattern(regexp = RegExpConstant.NAME, message = ErrorMessageConstant.FIRST_NAME_NOT_VALID)
    private String firstName;
    @NotBlank(message = ErrorMessageConstant.LAST_NAME_EMPTY)
    @Pattern(regexp = RegExpConstant.NAME, message = ErrorMessageConstant.LAST_NAME_NOT_VALID)
    private String lastName;
    @NotBlank(message = ErrorMessageConstant.EMAIL_EMPTY)
    @Email(message = ErrorMessageConstant.EMAIL_NOT_VALID)
    private String email;
    @NotBlank(message = ErrorMessageConstant.PASSWORD_EMPTY)
    @Pattern(regexp = RegExpConstant.PASSWORD, message = ErrorMessageConstant.PASSWORD_NOT_VALID)
    @Size(min = 8, max = 20, message = ErrorMessageConstant.PASSWORD_LENGTH)
    @ToString.Exclude
    private String password;
    @NotBlank(message = ErrorMessageConstant.PHONE_EMPTY)
    @Pattern(regexp = RegExpConstant.PHONE, message = ErrorMessageConstant.PHONE_NOT_VALID)
    @Size(min = 10, message = ErrorMessageConstant.PHONE_LENGTH)
    private String phoneNumber;
    private String avatarPath;
    private RoleDto roleDto;

    public enum RoleDto {
        ADMIN,
        READER,
        GUEST
    }
}
