package com.periodicalsubscription.service.api;

import com.periodicalsubscription.dto.UserDto;
import java.util.List;
public interface UserService {
    List<UserDto> findAll();

    UserDto findById(Long id);

    UserDto save(UserDto dto);

    UserDto update (UserDto dto);

    void deleteById(Long id);

    UserDto login(String email, String password);
}
