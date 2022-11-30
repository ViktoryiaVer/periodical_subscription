package com.periodicalsubscription.mapper;

import com.periodicalsubscription.service.dto.UserDto;
import com.periodicalsubscription.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Interface for mapping between User and UserDto
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * converts User to UserDto
     * @param user User object to be converted
     * @return UserDto object
     */
    @Mapping(source = "role", target = "roleDto")
    UserDto toDto(User user);

    /**
     * converts UserDto to User
     * @param userDto UserDto object to be converted
     * @return User object
     */
    @Mapping(source = "roleDto", target = "role")
    User toEntity(UserDto userDto);

}
