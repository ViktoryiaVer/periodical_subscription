package com.periodicalsubscription.service.api;

import com.periodicalsubscription.dto.UserDto;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
@Validated
public interface UserService {
    List<UserDto> findAll();

    UserDto findById(Long id);

    UserDto save(@Valid UserDto dto);

    UserDto update (UserDto dto);

    void delete(UserDto dto);
}
