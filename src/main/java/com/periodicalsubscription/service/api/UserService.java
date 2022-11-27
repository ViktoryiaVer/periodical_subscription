package com.periodicalsubscription.service.api;

import com.periodicalsubscription.service.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<UserDto> findAll(Pageable pageable);

    UserDto findById(Long id);

    UserDto findByUsername(String username);

    UserDto save(UserDto dto);

    UserDto update(UserDto dto);

    void deleteById(Long id);

    Page<UserDto> searchForUserByKeyword(String keyword, Pageable pageable);

    Long getCorrectUserId(Long userId);

    UserDto processFindingUserConsideringUserRole(Long id);

    UserDto processUserCreation(UserDto userDto);
}
