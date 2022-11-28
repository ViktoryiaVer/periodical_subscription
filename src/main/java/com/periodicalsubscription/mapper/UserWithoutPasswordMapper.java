package com.periodicalsubscription.mapper;

import com.periodicalsubscription.model.entity.User;
import com.periodicalsubscription.service.dto.UserWithoutPasswordDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserWithoutPasswordMapper {
    @Mapping(source = "role", target = "roleDto")
    UserWithoutPasswordDto toDto(User user);

    @Mapping(source = "roleDto", target = "role")
    User toEntity(UserWithoutPasswordDto userDto);

}
