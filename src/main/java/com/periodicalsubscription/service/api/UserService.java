package com.periodicalsubscription.service.api;

import com.periodicalsubscription.service.dto.UserDto;
import com.periodicalsubscription.service.dto.UserWithoutPasswordDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * interface with methods for suer business logic
 */
public interface UserService {
    /**
     * finds all users
     * @param pageable Pageable object for result pagination
     * @return page of UserWithoutPasswordDto object
     */
    Page<UserWithoutPasswordDto> findAll(Pageable pageable);

    /**
     * find a user by id
     * @param id id of the user
     * @return found user
     */
    UserWithoutPasswordDto findById(Long id);

    /**
     * find a user by username
     * @param username username of the user
     * @return found user
     */
    UserDto findByUsername(String username);

    /**
     * saves the user
     * @param dto UserDto object to be saved
     * @return saved UserWithoutPasswordDto object
     */
    UserWithoutPasswordDto save(UserDto dto);

    /**
     * updates the user
     * @param dto UserWithoutPasswordDto object to be updated
     * @return updated UserWithoutPasswordDto object
     */
    UserWithoutPasswordDto update(UserWithoutPasswordDto dto);

    /**
     * deletes user by id
     * @param id id of the user
     */
    void deleteById(Long id);

    /**
     * searches for a user by keyword
     * @param keyword word to be searched by
     * @param pageable Pageable object for result pagination
     * @return page of UserWithoutPasswordDto object
     */
    Page<UserWithoutPasswordDto> searchForUserByKeyword(String keyword, Pageable pageable);

    /**
     * gets correct user id considering user role
     * @param userId id of the user to be checked
     * @return correct user id
     */
    Long getCorrectUserId(Long userId);

    /**
     * process finding a user considering user role
     * @param id id of the user to be checked
     * @return found UserWithoutPasswordDto object
     */
    UserWithoutPasswordDto processFindingUserConsideringUserRole(Long id);

    /**
     * processes user creation
     * @param userDto UserDto object to be created
     * @return created UserWithoutPasswordDto object
     */
    UserWithoutPasswordDto processUserCreation(UserDto userDto);
}
