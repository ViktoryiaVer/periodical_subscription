package com.periodicalsubscription.service.api;

import com.periodicalsubscription.model.entity.Payment;

import java.util.List;

public interface PaymentService {
    List<Payment> findAll();

    Payment findById(Long id);

    Payment save(Payment payment);

    Payment update (Payment payment);

    void delete(Payment payment);
}
