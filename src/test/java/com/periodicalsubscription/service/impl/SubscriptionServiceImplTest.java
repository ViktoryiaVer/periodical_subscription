package com.periodicalsubscription.service.impl;

import com.periodicalsubscription.exceptions.user.UserNotFoundException;
import com.periodicalsubscription.model.repository.UserRepository;
import com.periodicalsubscription.util.TestObjectUtil;
import com.periodicalsubscription.exceptions.subscription.SubscriptionCompletedStatusException;
import com.periodicalsubscription.exceptions.subscription.SubscriptionNotFoundException;
import com.periodicalsubscription.exceptions.subscription.SubscriptionServiceException;
import com.periodicalsubscription.mapper.SubscriptionMapper;
import com.periodicalsubscription.model.entity.Subscription;
import com.periodicalsubscription.model.repository.SubscriptionRepository;
import com.periodicalsubscription.service.api.PeriodicalService;
import com.periodicalsubscription.service.api.SubscriptionService;
import com.periodicalsubscription.service.dto.PeriodicalDto;
import com.periodicalsubscription.service.dto.SubscriptionDto;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
class SubscriptionServiceImplTest {
    @Mock
    private SubscriptionRepository subscriptionRepository;
    @Mock
    private PeriodicalService periodicalService;
    @Mock
    private SubscriptionMapper mapper;
    @Mock
    private MessageSource messageSource;
    @Mock
    private UserRepository userRepository;
    private SubscriptionService subscriptionService;
    private Subscription subscription;
    private SubscriptionDto subscriptionDto;

    @BeforeEach
    public void setup() {
        subscriptionService = new SubscriptionServiceImpl(subscriptionRepository, periodicalService, mapper, messageSource, userRepository);
        subscription = TestObjectUtil.getSubscriptionWithoutId();
        subscriptionDto = TestObjectUtil.getSubscriptionDtoWithoutId();
    }

    @Test
    void whenFindAllSubscriptions_thenReturnSubscriptions() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Subscription> pageSubscription = new PageImpl<>(new ArrayList<>());
        Page<SubscriptionDto> pageSubscriptionDto = new PageImpl<>(new ArrayList<>());

        when(subscriptionRepository.findAll(pageable)).thenReturn(pageSubscription);
        when(subscriptionRepository.findAll(pageable).map(mapper::toDto)).thenReturn(pageSubscriptionDto);

        Page<SubscriptionDto> foundPage = subscriptionService.findAll(pageable);

        assertNotNull(foundPage);
        verify(subscriptionRepository, times(1)).findAll(pageable);
    }

    @Test
    void whenFindExitingSubscriptionById_thenReturnSubscription() {
        Long subscriptionId = 1L;
        when(subscriptionRepository.findById(subscriptionId)).thenReturn(Optional.of(subscription));
        mockMapperToDto();

        SubscriptionDto foundSubscription = subscriptionService.findById(subscriptionId);

        assertEquals(subscriptionDto, foundSubscription);
        verify(subscriptionRepository, times(1)).findById(subscriptionId);
    }

    @Test
    void whenFindNonExitingSubscriptionById_thenThrowException() {
        Long subscriptionId = 1L;
        when(subscriptionRepository.findById(subscriptionId)).thenReturn(Optional.empty());

        assertThrows(SubscriptionNotFoundException.class, () -> subscriptionService.findById(subscriptionId));
        verify(mapper, never()).toDto(any(Subscription.class));
    }

    @Test
    void whenSaveNewSubscription_thenReturnNewSubscription() {
        mockMapperToEntity();
        when(subscriptionRepository.save(subscription)).thenReturn(subscription);
        mockMapperToDto();

        SubscriptionDto savedSubscription = subscriptionService.save(subscriptionDto);

        assertEquals(subscriptionDto, savedSubscription);
        verify(subscriptionRepository, times(1)).save(any(Subscription.class));
    }

    @Test
    void whenFailureBySavingNewSubscription_thenThrowException() {
        mockMapperToEntity();
        when(subscriptionRepository.save(subscription)).thenReturn(null);
        when(mapper.toDto(null)).thenReturn(null);

        assertThrows(SubscriptionServiceException.class, () -> subscriptionService.save(subscriptionDto));
        verify(subscriptionRepository, times(1)).save(any(Subscription.class));
    }

    @Test
    void whenUpdateSubscription_thenReturnUpdatedSubscription() {
        mockMapperToEntity();
        when(subscriptionRepository.save(subscription)).thenReturn(subscription);
        mockMapperToDto();

        subscription.setStatus(Subscription.Status.PAYED);
        subscriptionDto.setStatusDto(SubscriptionDto.StatusDto.PAYED);

        SubscriptionDto updatedSubscription = subscriptionService.update(subscriptionDto);
        assertEquals(subscriptionDto, updatedSubscription);
        verify(subscriptionRepository, times(1)).save(any(Subscription.class));
    }

    @Test
    void whenFailureByUpdatingSubscription_thenThrowException() {
        mockMapperToEntity();
        when(subscriptionRepository.save(subscription)).thenReturn(null);
        when(mapper.toDto(null)).thenReturn(null);

        assertThrows(SubscriptionServiceException.class, () -> subscriptionService.update(subscriptionDto));
        verify(subscriptionRepository, times(1)).save(any(Subscription.class));
    }

    @Test
    void whenDeleteSubscription_thenSubscriptionIsDeleted() {
        Long subscriptionId = 1L;
        doNothing().when(subscriptionRepository).deleteById(subscriptionId);

        subscriptionService.deleteById(subscriptionId);
        verify(subscriptionRepository, times(1)).deleteById(subscriptionId);
    }

    @Test
    void whenFailureWhileDeletingSubscription_thenThrowException() {
        Long subscriptionId = 1L;

        doNothing().when(subscriptionRepository).deleteById(subscriptionId);
        when(subscriptionRepository.existsById(subscriptionId)).thenReturn(true);

        assertThrows(SubscriptionServiceException.class, () -> subscriptionService.deleteById(subscriptionId));
    }

    @Test
    void whenCreateSubscriptionFromCart_thenSubscriptionIsSaved() {
        UserDto userDto = TestObjectUtil.getUserDtoWithoutId();
        Long periodicalId = subscription.getSubscriptionDetails().get(0).getPeriodical().getId();
        Map<Long, Integer> cart = new HashMap<>();
        cart.put(periodicalId, subscription.getSubscriptionDetails().get(0).getSubscriptionDurationInYears());

        PeriodicalDto periodicalDto = subscriptionDto.getSubscriptionDetailDtos().get(0).getPeriodicalDto();
        when(periodicalService.findById(periodicalId)).thenReturn(periodicalDto);
        mockMapperToEntity();
        when(subscriptionRepository.save(subscription)).thenReturn(subscription);
        mockMapperToDto();

        SubscriptionDto createdSubscription = subscriptionService.createSubscriptionFromCart(userDto, cart);

        assertEquals(subscriptionDto, createdSubscription);
        verify(periodicalService, times(1)).findById(periodicalId);
        verify(subscriptionRepository, times(1)).save(any(Subscription.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void whenUpdateSubscriptionStatus_thenReturnUpdatedSubscription() {
        subscription = TestObjectUtil.getSubscriptionWithId();
        subscriptionDto = TestObjectUtil.getSubscriptionDtoWithId();

        doNothing().when(subscriptionRepository).updateSubscriptionStatus(subscription.getStatus(), subscription.getId());
        when(subscriptionRepository.findById(subscription.getId())).thenReturn(Optional.of(subscription));
        mockMapperToDto();

        SubscriptionDto updatedSubscription = subscriptionService.updateSubscriptionStatus(subscriptionDto.getStatusDto(), subscriptionDto.getId());
        assertEquals(subscriptionDto, updatedSubscription);
        verify(subscriptionRepository, times(1)).updateSubscriptionStatus(subscription.getStatus(), subscription.getId());
        verify(subscriptionRepository, times(1)).findById(subscription.getId());
    }

    @Test
    @WithMockUser(roles = "READER")
    void givenUserReader_whenUpdateSubscriptionStatusNotCanceled_thenThrowException() {
        assertThrows(SubscriptionServiceException.class, () -> subscriptionService.updateSubscriptionStatus(subscriptionDto.getStatusDto(), subscriptionDto.getId()));
        verify(subscriptionRepository, never()).updateSubscriptionStatus(subscription.getStatus(), subscription.getId());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void givenEndDateBeforeToday_whenUpdateSubscriptionStatusToCompleted_thenReturnUpdatedSubscription() {
        subscription = TestObjectUtil.getPayedSubscription();
        subscriptionDto = TestObjectUtil.getPayedSubscriptionDto();

        mockMapperToDto();
        when(subscriptionRepository.findById(subscription.getId())).thenReturn(Optional.of(subscription));
        doNothing().when(subscriptionRepository).updateSubscriptionStatus(Subscription.Status.COMPLETED, subscription.getId());

        subscription.setStatus(Subscription.Status.COMPLETED);
        subscriptionDto.setStatusDto(SubscriptionDto.StatusDto.COMPLETED);

        SubscriptionDto updatedSubscription = subscriptionService.updateSubscriptionStatus(subscriptionDto.getStatusDto(), subscriptionDto.getId());
        assertEquals(subscriptionDto, updatedSubscription);
        verify(subscriptionRepository, times(1)).updateSubscriptionStatus(subscription.getStatus(), subscription.getId());
        verify(subscriptionRepository, times(2)).findById(subscription.getId());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void givenEndDateAfterToday_whenUpdateSubscriptionStatusToCompleted_thenThrowException() {
        subscription = TestObjectUtil.getPayedSubscription();
        LocalDate subscriptionEndDate = subscription.getSubscriptionDetails().get(0).getSubscriptionEndDate();
        subscription.getSubscriptionDetails().get(0).setSubscriptionEndDate(subscriptionEndDate.plusYears(2).minusDays(1));
        subscriptionDto = TestObjectUtil.getPayedSubscriptionDto();
        LocalDate subscriptionDtoEndDate = subscriptionDto.getSubscriptionDetailDtos().get(0).getSubscriptionEndDate();
        subscriptionDto.getSubscriptionDetailDtos().get(0).setSubscriptionEndDate(subscriptionDtoEndDate.plusYears(2).minusDays(1));

        mockMapperToDto();
        when(subscriptionRepository.findById(subscription.getId())).thenReturn(Optional.of(subscription));

        assertThrows(SubscriptionCompletedStatusException.class, () -> subscriptionService.updateSubscriptionStatus(SubscriptionDto.StatusDto.COMPLETED, subscriptionDto.getId()));
        verify(subscriptionRepository, times(1)).findById(subscription.getId());
        verify(subscriptionRepository, times(0)).updateSubscriptionStatus(subscription.getStatus(), subscription.getId());
    }

    @Test
    void whenCheckIfSubscriptionExistsByUserId_thenReturnTrue() {
        Long userId = 1L;
        when(subscriptionRepository.existsSubscriptionByUserId(userId)).thenReturn(true);
        assertTrue(subscriptionService.checkIfSubscriptionExistsByUSer(userId));
    }

    @Test
    void whenCheckIfSubscriptionNotExistsByUserId_thenReturnFalse() {
        Long userId = 1L;
        when(subscriptionRepository.existsSubscriptionByUserId(userId)).thenReturn(false);
        assertFalse(subscriptionService.checkIfSubscriptionExistsByUSer(userId));
    }

    @Test
    void whenFindAllSubscriptionsByUserId_thenReturnSubscriptions() {
        Long userId = 1L;
        Pageable pageable = PageRequest.of(0, 5);
        Page<Subscription> pageSubscription = new PageImpl<>(new ArrayList<>());
        Page<SubscriptionDto> pageSubscriptionDto = new PageImpl<>(new ArrayList<>());

        when(userRepository.existsById(userId)).thenReturn(true);
        when(subscriptionRepository.findAllByUserId(userId, pageable)).thenReturn(pageSubscription);
        when(subscriptionRepository.findAllByUserId(userId, pageable).map(mapper::toDto)).thenReturn(pageSubscriptionDto);

        Page<SubscriptionDto> foundPage = subscriptionService.findAllSubscriptionsByUserId(userId, pageable);

        assertNotNull(foundPage);
        verify(subscriptionRepository, times(1)).findAllByUserId(userId, pageable);
    }

    @Test
    void whenFindAllSubscriptionsByNonExistingUser_thenThrowException() {
        Long userId = 1L;
        Pageable pageable = PageRequest.of(0, 5);

        when(userRepository.existsById(userId)).thenReturn(false);
        assertThrows(UserNotFoundException.class, () -> subscriptionService.findAllSubscriptionsByUserId(userId, pageable));
    }


    private void mockMapperToEntity() {
        when(mapper.toEntity(subscriptionDto)).thenReturn(subscription);
    }

    private void mockMapperToDto() {
        when(mapper.toDto(subscription)).thenReturn(subscriptionDto);
    }
}
