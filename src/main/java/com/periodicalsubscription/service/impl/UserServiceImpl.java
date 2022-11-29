package com.periodicalsubscription.service.impl;

import com.periodicalsubscription.aspect.logging.annotation.LogInvocationService;
import com.periodicalsubscription.aspect.logging.annotation.ServiceEx;
import com.periodicalsubscription.exceptions.user.UserAlreadyExistsException;
import com.periodicalsubscription.exceptions.user.UserDeleteException;
import com.periodicalsubscription.exceptions.user.UserNotFoundException;
import com.periodicalsubscription.exceptions.user.UserServiceException;
import com.periodicalsubscription.mapper.UserMapper;
import com.periodicalsubscription.mapper.UserWithoutPasswordMapper;
import com.periodicalsubscription.model.repository.UserRepository;
import com.periodicalsubscription.model.entity.User;
import com.periodicalsubscription.model.specification.UserSpecifications;
import com.periodicalsubscription.service.api.SubscriptionService;
import com.periodicalsubscription.service.api.UserService;
import com.periodicalsubscription.service.dto.UserDto;
import com.periodicalsubscription.service.dto.UserWithoutPasswordDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final UserWithoutPasswordMapper mapperWithoutPassword;
    private final SubscriptionService subscriptionService;
    private final MessageSource messageSource;
    private final PasswordEncoder passwordEncoder;

    @Override
    @LogInvocationService
    public Page<UserWithoutPasswordDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(mapperWithoutPassword::toDto);
    }

    @Override
    @LogInvocationService
    @ServiceEx
    public UserWithoutPasswordDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            throw new UserNotFoundException(messageSource.getMessage("msg.error.user.find.by.id", null,
                    LocaleContextHolder.getLocale()));
        });
        return mapperWithoutPassword.toDto(user);
    }

    @Override
    @LogInvocationService
    @ServiceEx
    public UserDto findByUsername(String username) {
        if ("anonymousUser".equals(username)) {
            return null;
        }

        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            throw new UserNotFoundException(messageSource.getMessage("msg.error.login", null,
                    LocaleContextHolder.getLocale()));
        });
        return mapper.toDto(user);
    }

    @Override
    @LogInvocationService
    @ServiceEx
    @Transactional
    public UserWithoutPasswordDto save(UserDto dto) {
        if (userRepository.existsByUsername(dto.getUsername()) || userRepository.existsByEmail(dto.getEmail())) {
            throw new UserAlreadyExistsException(messageSource.getMessage("msg.error.user.exists", null,
                    LocaleContextHolder.getLocale()));
        }
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        return mapperWithoutPassword.toDto(userRepository.save(mapper.toEntity(dto)));
    }

    @Override
    @LogInvocationService
    @ServiceEx
    @Transactional
    public UserWithoutPasswordDto update(UserWithoutPasswordDto dto) {
        User existingUser = userRepository.findByEmail(dto.getEmail()).orElse(null);

        if (existingUser != null && !existingUser.getId().equals(dto.getId())) {
            throw new UserAlreadyExistsException(messageSource.getMessage("msg.error.user.exists", null,
                    LocaleContextHolder.getLocale()));
        }

        User userToUpdate = mapperWithoutPassword.toEntity(dto);
        userToUpdate.setPassword(userRepository.getPasswordByUserId(dto.getId()));
        return mapperWithoutPassword.toDto(userRepository.save(userToUpdate));
    }

    @Override
    @LogInvocationService
    @ServiceEx
    @Transactional
    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(messageSource.getMessage("msg.error.user.find.by.id", null,
                    LocaleContextHolder.getLocale()));
        }

        if (subscriptionService.checkIfSubscriptionExistsByUSer(id)) {
            throw new UserDeleteException(messageSource.getMessage("msg.error.user.delete.subscription", null,
                    LocaleContextHolder.getLocale()));
        }
        userRepository.deleteById(id);

        if (userRepository.findById(id).isPresent()) {
            throw new UserServiceException(messageSource.getMessage("msg.error.user.service.delete", null,
                    LocaleContextHolder.getLocale()));
        }
    }

    @Override
    @LogInvocationService
    @Transactional
    public UserWithoutPasswordDto processUserCreation(UserDto userDto) {
        userDto.setRoleDto(UserDto.RoleDto.ROLE_READER);
        return save(userDto);
    }

    @Override
    @LogInvocationService
    public Page<UserWithoutPasswordDto> searchForUserByKeyword(String keyword, Pageable pageable) {
        Specification<User> specification = UserSpecifications.hasIdLike(keyword)
                .or(UserSpecifications.hasFirstNameLike(keyword))
                .or(UserSpecifications.hasLastNameLike(keyword))
                .or(UserSpecifications.hasEmailLike(keyword))
                .or(UserSpecifications.hasPhoneNumberLike(keyword));
        return userRepository.findAll(specification, pageable).map(mapperWithoutPassword::toDto);
    }

    @Override
    @LogInvocationService
    public Long getCorrectUserId(Long userId) {
        UserDto user = findByUsername(SecurityContextHolder.getContext().getAuthentication()
                .getName());
        if ("ROLE_READER".equals(user.getRoleDto().toString())) {
            userId = user.getId();
        }
        return userId;
    }

    @Override
    @LogInvocationService
    public UserWithoutPasswordDto processFindingUserConsideringUserRole(Long id) {
        return findById(getCorrectUserId(id));
    }
}
