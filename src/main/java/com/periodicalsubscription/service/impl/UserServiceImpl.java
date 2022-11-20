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
import com.periodicalsubscription.model.specification.UserSpecifications;
import com.periodicalsubscription.service.api.SubscriptionService;
import com.periodicalsubscription.service.api.UserService;
import com.periodicalsubscription.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public UserDto save(@Valid UserDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new UserAlreadyExistsException(messageSource.getMessage("msg.error.user.email.exists", null,
                    LocaleContextHolder.getLocale()));
        }
        return mapper.toDto(userRepository.save(mapper.toEntity(dto)));
    }

    @Override
    @LogInvocationService
    @ServiceEx
    @Transactional
    public UserDto update(UserDto dto) {
        User existingUser = userRepository.findByEmail(dto.getEmail()).orElse(null);

        if (existingUser != null && !existingUser.getId().equals(dto.getId())) {
            throw new UserAlreadyExistsException(messageSource.getMessage("msg.error.user.email.exists", null,
                    LocaleContextHolder.getLocale()));
        }
        return mapper.toDto(userRepository.save(mapper.toEntity(dto)));
    }

    @Override
    @LogInvocationService
    @ServiceEx
    @Transactional
    public void deleteById(Long id) {
        if (subscriptionService.checkIfSubscriptionExistsByUSer(id)) {
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
        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null || !password.equals(user.getPassword())) {
            throw new LoginException(messageSource.getMessage("msg.error.login", null,
                    LocaleContextHolder.getLocale()));
        }
        return mapper.toDto(user);
    }

    @Override
    @LogInvocationService
    public Page<UserDto> searchForUserByKeyword(String keyword, Pageable pageable) {
        Specification<User> specification = UserSpecifications.hasIdLike(keyword)
                .or(UserSpecifications.hasFirstNameLike(keyword))
                .or(UserSpecifications.hasLastNameLike(keyword))
                .or(UserSpecifications.hasEmailLike(keyword))
                .or(UserSpecifications.hasPhoneNumberLike(keyword));
        return userRepository.findAll(specification, pageable).map(mapper::toDto);
    }
}
