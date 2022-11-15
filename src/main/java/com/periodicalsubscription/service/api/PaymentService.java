package com.periodicalsubscription.service.api;

import com.periodicalsubscription.service.dto.PaymentDto;
import com.periodicalsubscription.service.dto.filter.PaymentFilterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentService {
    Page<PaymentDto> findAll(Pageable pageable);

    PaymentDto findById(Long id);

    PaymentDto save(PaymentDto dto);

    PaymentDto update(PaymentDto dto);

    void deleteById(Long id);

    PaymentDto processPaymentRegistration(Long subscriptionId, String paymentTime, String paymentMethod);

    PaymentDto processPaymentUpdate(Long paymentId, String paymentTime, String paymentMethodDto);

    Page<PaymentDto> filterPayment(PaymentFilterDto filterDto, Pageable pageable);

    Page<PaymentDto> searchForPaymentByKeyword(String keyword, Pageable pageable);
}
