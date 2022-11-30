package com.periodicalsubscription.service.impl;

import com.periodicalsubscription.util.TestObjectUtil;
import com.periodicalsubscription.exceptions.payment.PaymentNotFoundException;
import com.periodicalsubscription.exceptions.payment.PaymentServiceException;
import com.periodicalsubscription.mapper.PaymentMapper;
import com.periodicalsubscription.model.entity.Payment;
import com.periodicalsubscription.model.repository.PaymentRepository;
import com.periodicalsubscription.service.api.PaymentService;
import com.periodicalsubscription.service.api.SubscriptionDetailService;
import com.periodicalsubscription.service.api.SubscriptionService;
import com.periodicalsubscription.service.dto.PaymentDto;
import com.periodicalsubscription.service.dto.SubscriptionDto;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private PaymentMapper mapper;
    @Mock
    private SubscriptionService subscriptionService;
    @Mock
    private SubscriptionDetailService subscriptionDetailService;
    @Mock
    private MessageSource messageSource;
    private PaymentService paymentService;
    private Payment payment;
    private PaymentDto paymentDto;

    @BeforeEach
    public void setup() {
        paymentService = new PaymentServiceImpl(paymentRepository, mapper, subscriptionService, subscriptionDetailService, messageSource);
        payment = TestObjectUtil.getPaymentWithOutId();
        paymentDto = TestObjectUtil.getPaymentDtoWithoutId();
    }

    @Test
    void whenFindAllPayments_thenReturnPayments() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Payment> pagePayment = new PageImpl<>(new ArrayList<>());
        Page<PaymentDto> pagePaymentDto = new PageImpl<>(new ArrayList<>());

        when(paymentRepository.findAll(pageable)).thenReturn(pagePayment);
        when(paymentRepository.findAll(pageable).map(mapper::toDto)).thenReturn(pagePaymentDto);

        Page<PaymentDto> foundPage = paymentService.findAll(pageable);

        assertNotNull(foundPage);
        verify(paymentRepository, times(1)).findAll(pageable);
    }

    @Test
    void whenFindExitingPaymentById_thenReturnPayment() {
        Long paymentId = 1L;
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(payment));
        mockMapperToDto();

        PaymentDto foundPayment = paymentService.findById(paymentId);

        assertEquals(paymentDto, foundPayment);
        verify(paymentRepository, times(1)).findById(paymentId);
    }

    @Test
    void whenFindNonExitingPaymentById_thenThrowException() {
        Long paymentId = 1L;
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.empty());

        assertThrows(PaymentNotFoundException.class, () -> paymentService.findById(paymentId));
        verify(mapper, never()).toDto(any(Payment.class));
    }

    @Test
    void whenSaveNewPayment_thenReturnNewPayment() {
        mockMapperToEntity();
        when(paymentRepository.save(payment)).thenReturn(payment);
        mockMapperToDto();

        PaymentDto savedPeriodical = paymentService.save(paymentDto);

        assertEquals(paymentDto, savedPeriodical);
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void whenFailureBySavingNewPayment_thenThrowException() {
        mockMapperToEntity();
        when(paymentRepository.save(payment)).thenReturn(null);
        when(mapper.toDto(null)).thenReturn(null);

        assertThrows(PaymentServiceException.class, () -> paymentService.save(paymentDto));
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void whenUpdatePayment_thenReturnUpdatedPayment() {
        mockMapperToEntity();
        when(paymentRepository.save(payment)).thenReturn(payment);
        mockMapperToDto();

        payment.setPaymentMethod(Payment.PaymentMethod.CHECK);
        payment.setPaymentTime(LocalDateTime.now().minusDays(1));
        paymentDto.setPaymentMethodDto(PaymentDto.PaymentMethodDto.CHECK);
        paymentDto.setPaymentTime(LocalDateTime.now().minusDays(1));


        PaymentDto updatedPayment = paymentService.update(paymentDto);
        assertEquals(paymentDto, updatedPayment);
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void whenFailureByUpdatingPayment_thenThrowException() {
        mockMapperToEntity();
        when(paymentRepository.save(payment)).thenReturn(null);
        when(mapper.toDto(null)).thenReturn(null);

        assertThrows(PaymentServiceException.class, () -> paymentService.update(paymentDto));
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void whenProcessPaymentRegistration_thenReturnSavedPaymentAndTriggerAllOtherServices() {
        Long subscriptionId = 1L;
        String paymentTime = paymentDto.getPaymentTime().toString();
        String paymentMethod = String.valueOf(paymentDto.getPaymentMethodDto().toString());

        when(subscriptionService.findById(subscriptionId)).thenReturn(paymentDto.getSubscriptionDto());
        mockMapperToEntity();
        when(paymentRepository.save(payment)).thenReturn(payment);
        mockMapperToDto();
        when(subscriptionService.updateSubscriptionStatus(SubscriptionDto.StatusDto.PAYED, subscriptionId)).thenReturn(paymentDto.getSubscriptionDto());

        doNothing().when(subscriptionDetailService).updateSubscriptionPeriod(paymentDto.getPaymentTime().toLocalDate(),
                paymentDto.getSubscriptionDto().getSubscriptionDetailDtos().get(0).getSubscriptionDurationInYears(), subscriptionId);

        PaymentDto savedPayment = paymentService.processPaymentRegistration(subscriptionId, paymentTime, paymentMethod);

        assertEquals(paymentDto, savedPayment);
        verify(subscriptionService, times(1)).findById(subscriptionId);
        verify(paymentRepository, times(1)).save(any(Payment.class));
        verify(subscriptionService, times(1)).updateSubscriptionStatus(SubscriptionDto.StatusDto.PAYED, subscriptionId);
        verify(subscriptionDetailService, times(1)).updateSubscriptionPeriod(any(LocalDate.class), anyInt(), anyLong());
    }

    @Test
    void whenProcessPaymentUpdateFromRequestData_thenReturnUpdatedPayment() {
        Long paymentId = 1L;
        payment.setId(paymentId);
        paymentDto.setId(paymentId);
        String paymentTime = payment.getPaymentTime().minusDays(1).toString();
        String paymentMethod = String.valueOf(PaymentDto.PaymentMethodDto.ONLINE_PAYMENT_SERVICE);

        mockMapperToEntity();
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(payment));
        mockMapperToDto();
        when(paymentRepository.save(payment)).thenReturn(payment);

        payment.setPaymentTime(LocalDateTime.parse(paymentTime));
        payment.setPaymentMethod(Payment.PaymentMethod.valueOf(paymentMethod));
        paymentDto.setPaymentTime(LocalDateTime.parse(paymentTime));
        paymentDto.setPaymentMethodDto(PaymentDto.PaymentMethodDto.valueOf(paymentMethod));

        PaymentDto updatedPayment = paymentService.processPaymentUpdate(paymentId, paymentTime, paymentMethod);

        assertEquals(paymentDto, updatedPayment);
        verify(paymentRepository, times(1)).findById(paymentId);
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    private void mockMapperToEntity() {
        when(mapper.toEntity(paymentDto)).thenReturn(payment);
    }

    private void mockMapperToDto() {
        when(mapper.toDto(payment)).thenReturn(paymentDto);
    }
}
