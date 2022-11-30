package com.periodicalsubscription.mapper;

import com.periodicalsubscription.model.entity.User;
import com.periodicalsubscription.service.dto.UserWithoutPasswordDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Interface for mapping between User and UserWithoutPasswordDto
 */
@Mapper(componentModel = "spring")
public interface UserWithoutPasswordMapper {

    /**
     * converts User to UserWithoutPasswordDto
     * @param user User object to be converted
     * @return UserWithoutPasswordDto object
     */
    @Mapping(source = "role", target = "roleDto")
    UserWithoutPasswordDto toDto(User user);

    /**
     * converts UserWithoutPasswordDto to User
     * @param userDto UserWithoutPasswordDto object to be converted
     * @return User object
     */
    @Mapping(source = "roleDto", target = "role")
    User toEntity(UserWithoutPasswordDto userDto);

}
