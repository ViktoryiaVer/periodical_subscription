package com.periodicalsubscription.service.impl;

import com.periodicalsubscription.TestObjectUtil;
import com.periodicalsubscription.exceptions.LoginException;
import com.periodicalsubscription.exceptions.user.UserAlreadyExistsException;
import com.periodicalsubscription.exceptions.user.UserDeleteException;
import com.periodicalsubscription.exceptions.user.UserNotFoundException;
import com.periodicalsubscription.exceptions.user.UserServiceException;
import com.periodicalsubscription.mapper.UserMapper;
import com.periodicalsubscription.model.entity.User;
import com.periodicalsubscription.model.repository.UserRepository;
import com.periodicalsubscription.service.api.SubscriptionService;
import com.periodicalsubscription.service.api.UserService;
import com.periodicalsubscription.service.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper mapper;
    @Mock
    private SubscriptionService subscriptionService;
    @Mock
    private MessageSource messageSource;

    private UserService userService;
    private User user;
    private UserDto userDto;

    @BeforeEach
    public void setup() {
        userService = new UserServiceImpl(userRepository, mapper, subscriptionService, messageSource);
        user = TestObjectUtil.getUserWithoutId();
        userDto = TestObjectUtil.getUserDtoWithoutId();
    }

    @Test
    void whenFindAllUsers_thenReturnUsers() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<User> pageUser = new PageImpl<>(new ArrayList<>());
        Page<UserDto> pageUserDto = new PageImpl<>(new ArrayList<>());

        when(userRepository.findAll(pageable)).thenReturn(pageUser);
        when(userRepository.findAll(pageable).map(mapper::toDto)).thenReturn(pageUserDto);

        Page<UserDto> foundPage = userService.findAll(pageable);

        assertNotNull(foundPage);
        verify(userRepository, times(1)).findAll(pageable);

    }

    @Test
    void whenFindExitingUserById_thenReturnUser() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        mockMapperToDto();

        UserDto foundUser = userService.findById(userId);

        assertEquals(userDto, foundUser);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void whenFindNonExitingUserById_thenThrowException() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findById(userId));
        verify(mapper, never()).toDto(any(User.class));
    }

    @Test
    void whenSaveNewUser_thenReturnNewUser() {
        mockMapperToEntity();
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);
        mockMapperToDto();

        UserDto savedUser = userService.save(userDto);

        assertEquals(userDto, savedUser);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void whenSaveUserWithExistingEmail_thenThrowException() {
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> userService.save(userDto));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void whenUpdateUser_thenReturnUpdatedUser() {
        user = TestObjectUtil.getUserWithId();
        userDto = TestObjectUtil.getUserDtoWithId();

        mockMapperToEntity();
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.ofNullable(user));
        when(userRepository.save(user)).thenReturn(user);
        mockMapperToDto();

        user.setFirstName("Updated Test");
        user.setLastName("Updated Test");
        userDto.setFirstName("Updated Test");
        userDto.setLastName("Updated Test");

        UserDto updatedUser = userService.update(userDto);
        assertEquals(userDto, updatedUser);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void whenUpdateUserWithSameEmailButAnotherId_thenThrowException() {
        user = TestObjectUtil.getUserWithId();
        userDto = TestObjectUtil.getUserDtoWithId();
        User existingUser = TestObjectUtil.getUserWithId();
        existingUser.setId(2L);

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(existingUser));

        assertThrows(UserAlreadyExistsException.class, () -> userService.update(userDto));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void whenDeleteUserWithoutSubscriptions_thenUserIsDeleted() {
        Long userId = 1L;
        when(subscriptionService.checkIfSubscriptionExistsByUSer(userId)).thenReturn(false);
        doNothing().when(userRepository).deleteById(userId);

        userService.deleteById(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void whenDeleteUserWithSubscriptions_thenThrowException() {
        Long userId = 1L;

        when(subscriptionService.checkIfSubscriptionExistsByUSer(userId)).thenReturn(true);

        assertThrows(UserDeleteException.class, () -> userService.deleteById(userId));
        verify(userRepository, never()).deleteById(userId);
    }

    @Test
    void whenFailureWhileDeletingUser_thenThroeException() {
        Long userId = 1L;

        when(subscriptionService.checkIfSubscriptionExistsByUSer(userId)).thenReturn(false);
        doNothing().when(userRepository).deleteById(userId);
        when(userRepository.existsById(userId)).thenReturn(true);

        assertThrows(UserServiceException.class, () -> userService.deleteById(userId));
    }

    @Test
    void whenLoginWithCorrectData_thenReturnUser() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        mockMapperToDto();

        UserDto foundUser = userService.login(userDto.getEmail(), userDto.getPassword());

        assertEquals(userDto, foundUser);
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void whenLoginWithInCorrectData_thenThrowException() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        assertThrows(LoginException.class, () -> userService.login(userDto.getEmail(), userDto.getPassword()));
    }

    private void mockMapperToEntity() {
        when(mapper.toEntity(userDto)).thenReturn(user);
    }

    private void mockMapperToDto() {
        when(mapper.toDto(user)).thenReturn(userDto);
    }
}
