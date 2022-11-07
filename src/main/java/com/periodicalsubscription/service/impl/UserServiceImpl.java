package com.periodicalsubscription.service.impl;

import com.periodicalsubscription.aspect.logging.annotation.LogInvocationService;
import com.periodicalsubscription.aspect.logging.annotation.LoginEx;
import com.periodicalsubscription.aspect.logging.annotation.ServiceEx;
import com.periodicalsubscription.exceptions.LoginException;
import com.periodicalsubscription.exceptions.user.UserAlreadyExistsException;
import com.periodicalsubscription.exceptions.user.UserDeleteException;
import com.periodicalsubscription.exceptions.user.UserNotFoundException;
import com.periodicalsubscription.exceptions.user.UserServiceException;
import com.periodicalsubscription.mapper.UserMapper;
import com.periodicalsubscription.model.repository.UserRepository;
import com.periodicalsubscription.model.entity.User;
import com.periodicalsubscription.service.api.SubscriptionService;
import com.periodicalsubscription.service.api.UserService;
import com.periodicalsubscription.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final SubscriptionService subscriptionService;
    private final MessageSource messageSource;

    @Override
    @LogInvocationService
    public Page<UserDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(mapper::toDto);
    }

    @Override
    @LogInvocationService
    @ServiceEx
    public UserDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            throw new UserNotFoundException(messageSource.getMessage("msg.error.user.find.by.id", null,
                    LocaleContextHolder.getLocale()));
        });
        return mapper.toDto(user);
    }

    @Override
    @LogInvocationService
    @ServiceEx
    public UserDto save(@Valid UserDto dto) {
        if (userRepository.findByEmail(dto.getEmail()) != null) {
            throw new UserAlreadyExistsException(messageSource.getMessage("msg.error.user.email.exists", null,
                    LocaleContextHolder.getLocale()));
        }
        return mapper.toDto(userRepository.save(mapper.toEntity(dto)));
    }

    @Override
    @LogInvocationService
    @ServiceEx
    public UserDto update(UserDto dto) {
        User existingUser = userRepository.findByEmail(dto.getEmail());

        if (existingUser != null && !existingUser.getId().equals(dto.getId())) {
            throw new UserAlreadyExistsException(messageSource.getMessage("msg.error.user.email.exists", null,
                    LocaleContextHolder.getLocale()));
        }
        return mapper.toDto(userRepository.save(mapper.toEntity(dto)));
    }

    @Override
    @LogInvocationService
    @ServiceEx
    public void deleteById(Long id) {
        UserDto userDto = findById(id);

        if (subscriptionService.checkIfSubscriptionExistsByUSer(userDto)) {
            throw new UserDeleteException(messageSource.getMessage("msg.error.user.delete.subscription", null,
                    LocaleContextHolder.getLocale()));
        }
        userRepository.deleteById(id);

        if (userRepository.existsById(id)) {
            throw new UserServiceException(messageSource.getMessage("msg.error.user.service.delete", null,
                    LocaleContextHolder.getLocale()));
        }
    }

    @Override
    @LogInvocationService
    @LoginEx
    public UserDto login(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user == null || !password.equals(user.getPassword())) {
            throw new LoginException(messageSource.getMessage("msg.error.login", null,
                    LocaleContextHolder.getLocale()));
        }
        return mapper.toDto(user);
    }
}
