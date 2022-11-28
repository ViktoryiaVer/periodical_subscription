package com.periodicalsubscription.service.api;

import com.periodicalsubscription.service.dto.UserDto;
import com.periodicalsubscription.service.dto.UserWithoutPasswordDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<UserWithoutPasswordDto> findAll(Pageable pageable);

    UserWithoutPasswordDto findById(Long id);

    UserDto findByUsername(String username);

    UserWithoutPasswordDto save(UserDto dto);

    UserWithoutPasswordDto update(UserWithoutPasswordDto dto);

    void deleteById(Long id);

    Page<UserWithoutPasswordDto> searchForUserByKeyword(String keyword, Pageable pageable);

    Long getCorrectUserId(Long userId);

    UserWithoutPasswordDto processFindingUserConsideringUserRole(Long id);

    UserWithoutPasswordDto processUserCreation(UserDto userDto);
}
