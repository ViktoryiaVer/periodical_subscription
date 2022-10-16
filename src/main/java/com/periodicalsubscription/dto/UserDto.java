package com.periodicalsubscription.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String avatarPath;
    private RoleDto roleDto;

    public enum RoleDto {
        ADMIN,
        READER,
        GUEST
    }

}
