package com.periodicalsubscription.mapper;

import com.periodicalsubscription.dto.UserDto;
import com.periodicalsubscription.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "role", target = "roleDto")
    UserDto toDto(User user);

    @Mapping(source = "roleDto", target = "role")
    User toEntity(UserDto userDto);

}
