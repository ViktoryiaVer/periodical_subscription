package com.periodicalsubscription.service.dto;

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
    @NotBlank(message = "{msg.validation.user.first.name.empty}")
    @Pattern(regexp = RegExpConstant.NAME, message = "{msg.validation.user.first.name.not.valid}")
    private String firstName;
    @NotBlank(message = "{msg.validation.user.last.name.empty}")
    @Pattern(regexp = RegExpConstant.NAME, message = "{msg.validation.user.last.name.not.valid}")
    private String lastName;
    @NotBlank(message = "{msg.validation.user.email.empty}")
    @Email(message = "{msg.validation.user.email.not.valid}")
    private String email;
    @NotBlank(message = "{msg.validation.user.password.empty}")
    @Pattern(regexp = RegExpConstant.PASSWORD, message = "{msg.validation.user.password.not.valid}")
    @Size(min = 8, max = 20, message = "{msg.validation.user.password.length}")
    @ToString.Exclude
    private String password;
    @NotBlank(message = "{msg.validation.user.phone.empty}")
    @Pattern(regexp = RegExpConstant.PHONE, message = "{msg.validation.user.phone.not.valid}")
    @Size(min = 10, message = "{msg.validation.user.phone.length}")
    private String phoneNumber;
    private String avatarPath;
    private RoleDto roleDto;

    public enum RoleDto {
        ADMIN,
        READER,
        GUEST
    }
}
