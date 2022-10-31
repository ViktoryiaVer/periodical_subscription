package com.periodicalsubscription.service.api;

import com.periodicalsubscription.dto.PaymentDto;

import java.util.List;

public interface PaymentService {
    List<PaymentDto> findAll();

    PaymentDto findById(Long id);

    PaymentDto save(PaymentDto dto);

    PaymentDto update (PaymentDto dto);

    void deleteById(Long id);

    PaymentDto processPaymentRegistration(Long subscriptionId, String paymentTime, String paymentMethod);

    PaymentDto processPaymentUpdate(Long paymentId, String paymentTime, String paymentMethodDto);
}
