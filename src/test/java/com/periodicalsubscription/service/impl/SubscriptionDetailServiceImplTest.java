package com.periodicalsubscription.service.impl;

import com.periodicalsubscription.TestObjectUtil;
import com.periodicalsubscription.exceptions.subscriptiondetail.SubscriptionDetailNotFoundException;
import com.periodicalsubscription.exceptions.subscriptiondetail.SubscriptionDetailServiceException;
import com.periodicalsubscription.mapper.SubscriptionDetailMapper;
import com.periodicalsubscription.model.entity.Payment;
import com.periodicalsubscription.model.entity.SubscriptionDetail;
import com.periodicalsubscription.model.repository.SubscriptionDetailRepository;
import com.periodicalsubscription.service.api.SubscriptionDetailService;
import com.periodicalsubscription.service.dto.SubscriptionDetailDto;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubscriptionDetailServiceImplTest {
    @Mock
    private SubscriptionDetailRepository subscriptionDetailRepository;
    @Mock
    private SubscriptionDetailMapper mapper;
    @Mock
    private MessageSource messageSource;
    private SubscriptionDetailService subscriptionDetailService;
    private SubscriptionDetail subscriptionDetail;
    private SubscriptionDetailDto subscriptionDetailDto;

    @BeforeEach
    public void setup() {
        subscriptionDetailService = new SubscriptionDetailServiceImpl(subscriptionDetailRepository, mapper, messageSource);
        subscriptionDetail = TestObjectUtil.getSubscriptionDetailWithoutId();
        subscriptionDetailDto = TestObjectUtil.getSubscriptionDetailDtoWithoutId();
    }

    @Test
    void whenFindAllSubscriptionDetails_thenReturnSubscriptionDetails() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<SubscriptionDetail> pageDetail = new PageImpl<>(new ArrayList<>());
        Page<SubscriptionDetailDto> pageDetailDto = new PageImpl<>(new ArrayList<>());

        when(subscriptionDetailRepository.findAll(pageable)).thenReturn(pageDetail);
        when(subscriptionDetailRepository.findAll(pageable).map(mapper::toDto)).thenReturn(pageDetailDto);

        Page<SubscriptionDetailDto> foundPage = subscriptionDetailService.findAll(pageable);

        assertNotNull(foundPage);
        verify(subscriptionDetailRepository, times(1)).findAll(pageable);
    }

    @Test
    void whenFindExitingSubscriptionDetailById_thenReturnSubscriptionDetail() {
        Long detailId = 1L;
        when(subscriptionDetailRepository.findById(detailId)).thenReturn(Optional.of(subscriptionDetail));
        mockMapperToDto();

        SubscriptionDetailDto foundDetail = subscriptionDetailService.findById(detailId);

        assertEquals(subscriptionDetailDto, foundDetail);
        verify(subscriptionDetailRepository, times(1)).findById(detailId);
    }

    @Test
    void whenFindNonExitingSubscriptionDetailById_thenThrowException() {
        Long detailId = 1L;
        when(subscriptionDetailRepository.findById(detailId)).thenReturn(Optional.empty());

        assertThrows(SubscriptionDetailNotFoundException.class, () -> subscriptionDetailService.findById(detailId));
        verify(mapper, never()).toDto(any(SubscriptionDetail.class));
    }

    @Test
    void whenSaveNewSubscriptionDetail_thenReturnNewSubscriptionDetail() {
        mockMapperToEntity();
        when(subscriptionDetailRepository.save(subscriptionDetail)).thenReturn(subscriptionDetail);
        mockMapperToDto();

        SubscriptionDetailDto savedDetail = subscriptionDetailService.save(subscriptionDetailDto);

        assertEquals(subscriptionDetailDto, savedDetail);
        verify(subscriptionDetailRepository, times(1)).save(any(SubscriptionDetail.class));
    }

    @Test
    void whenFailureBySavingNewSubscriptionDetail_thenThrowException() {
        mockMapperToEntity();
        when(subscriptionDetailRepository.save(subscriptionDetail)).thenReturn(null);
        when(mapper.toDto(null)).thenReturn(null);

        assertThrows(SubscriptionDetailServiceException.class, () -> subscriptionDetailService.save(subscriptionDetailDto));
        verify(subscriptionDetailRepository, times(1)).save(any(SubscriptionDetail.class));
    }

    @Test
    void whenUpdateSubscriptionDetail_thenReturnUpdatedSubscriptionDetail() {
        mockMapperToEntity();
        when(subscriptionDetailRepository.save(subscriptionDetail)).thenReturn(subscriptionDetail);
        mockMapperToDto();

        subscriptionDetail.setPeriodicalCurrentPrice(BigDecimal.valueOf(200));
        subscriptionDetailDto.setPeriodicalCurrentPrice(BigDecimal.valueOf(200));

        SubscriptionDetailDto updatedSDetail = subscriptionDetailService.update(subscriptionDetailDto);
        assertEquals(subscriptionDetailDto, updatedSDetail);
        verify(subscriptionDetailRepository, times(1)).save(any(SubscriptionDetail.class));
    }

    @Test
    void whenFailureByUpdatingSubscriptionDetail_thenThrowException() {
        mockMapperToEntity();
        when(subscriptionDetailRepository.save(subscriptionDetail)).thenReturn(null);
        when(mapper.toDto(null)).thenReturn(null);

        assertThrows(SubscriptionDetailServiceException.class, () -> subscriptionDetailService.update(subscriptionDetailDto));
        verify(subscriptionDetailRepository, times(1)).save(any(SubscriptionDetail.class));
    }

    @Test
    void whenDeleteSubscriptionDetail_thenSubscriptionDetailIsDeleted() {
        Long detailId = 1L;
        doNothing().when(subscriptionDetailRepository).deleteById(detailId);

        subscriptionDetailService.deleteById(detailId);
        verify(subscriptionDetailRepository, times(1)).deleteById(detailId);
    }

    @Test
    void whenFailureWhileDeletingSubscriptionDetail_thenThrowException() {
        Long detailId = 1L;

        doNothing().when(subscriptionDetailRepository).deleteById(detailId);
        when(subscriptionDetailRepository.existsById(detailId)).thenReturn(true);

        assertThrows(SubscriptionDetailServiceException.class, () -> subscriptionDetailService.deleteById(detailId));
    }

    @Test
    void whenUpdateSubscriptionPeriod_thenSubscriptionDetailIsUpdated() {
        Payment payment = TestObjectUtil.getPaymentWithOutId();
        LocalDate startDate = payment.getPaymentTime().toLocalDate();
        int subscriptionDuration = 1;
        Long detailId = 1L;
        LocalDate endDate = startDate.plusYears(subscriptionDuration).minusDays(1);

        subscriptionDetailService.updateSubscriptionPeriod(startDate, subscriptionDuration, detailId);

        verify(subscriptionDetailRepository, times(1)).updateSubscriptionStartDate(startDate, detailId);
        verify(subscriptionDetailRepository, times(1)).updateSubscriptionEndDate(endDate, detailId);
    }

    @Test
    void whenCheckIfSubscriptionDetailExistsByPeriodicalId_thenReturnTrue() {
        Long detailId = 1L;
        when(subscriptionDetailRepository.existsSubscriptionDetailByPeriodicalId(detailId)).thenReturn(true);
        assertTrue(subscriptionDetailService.checkIfSubscriptionDetailExistsByPeriodicalId(detailId));
    }

    @Test
    void whenCheckIfSubscriptionDetailNotExistsByPeriodicalId_thenReturnFalse() {
        Long detailId = 1L;
        when(subscriptionDetailRepository.existsSubscriptionDetailByPeriodicalId(detailId)).thenReturn(false);
        assertFalse(subscriptionDetailService.checkIfSubscriptionDetailExistsByPeriodicalId(detailId));
    }

    private void mockMapperToEntity() {
        when(mapper.toEntity(subscriptionDetailDto)).thenReturn(subscriptionDetail);
    }

    private void mockMapperToDto() {
        when(mapper.toDto(subscriptionDetail)).thenReturn(subscriptionDetailDto);
    }
}
