package com.periodicalsubscription.service.api;

import com.periodicalsubscription.dto.PaymentDto;

import java.util.List;

public interface PaymentService {
    List<PaymentDto> findAll();

    PaymentDto findById(Long id);

    PaymentDto save(PaymentDto dto);

    PaymentDto update (PaymentDto dto);

    void delete(PaymentDto dto);
}
