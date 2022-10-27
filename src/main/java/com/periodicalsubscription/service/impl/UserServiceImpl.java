package com.periodicalsubscription.service.impl;

import com.periodicalsubscription.mapper.UserMapper;
import com.periodicalsubscription.model.repository.UserRepository;
import com.periodicalsubscription.model.entity.User;
import com.periodicalsubscription.service.api.UserService;
import com.periodicalsubscription.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper mapper;
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
    public UserDto save(@Valid UserDto dto) {
        if(userRepository.findByEmail(dto.getEmail()) != null) {
            throw new RuntimeException("User with this email already exists");
        }
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
