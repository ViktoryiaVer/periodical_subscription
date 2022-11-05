package com.periodicalsubscription.service.api;

import com.periodicalsubscription.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<UserDto> findAll(Pageable pageable);

    UserDto findById(Long id);

    UserDto save(UserDto dto);

    UserDto update (UserDto dto);

    void deleteById(Long id);

    UserDto login(String email, String password);
}
