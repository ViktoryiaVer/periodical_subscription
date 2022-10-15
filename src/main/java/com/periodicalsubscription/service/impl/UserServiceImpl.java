package com.periodicalsubscription.service.impl;

import com.periodicalsubscription.model.repository.UserRepository;
import com.periodicalsubscription.model.entity.User;
import com.periodicalsubscription.service.api.UserService;
import com.periodicalsubscription.service.dto.UserDto;
import com.periodicalsubscription.service.mapper.ObjectMapper;
import com.periodicalsubscription.service.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserValidator validator;
    private final ObjectMapper mapper;
    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(RuntimeException::new);

        return mapper.toDto(user);
    }

    @Override
    public UserDto save(UserDto dto) {
        if(userRepository.findByEmail(dto.getEmail()) != null) {
            throw new RuntimeException("User with this email already exists");
        }
        validator.checkValidity(dto);
        return mapper.toDto(userRepository.save(mapper.toEntity(dto)));
    }

    @Override
    public UserDto update(UserDto dto) {
        //TODO some validation?
        return mapper.toDto(userRepository.save(mapper.toEntity(dto)));
    }

    @Override
    public void delete(UserDto dto) {
        //TODO some validation?
        userRepository.delete(mapper.toEntity(dto));
    }

}
