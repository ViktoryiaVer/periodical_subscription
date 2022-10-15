package com.periodicalsubscription.service.api;

import com.periodicalsubscription.service.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> findAll();

    UserDto findById(Long id);

    UserDto save(UserDto dto);

    UserDto update (UserDto dto);

    void delete(UserDto dto);


}
