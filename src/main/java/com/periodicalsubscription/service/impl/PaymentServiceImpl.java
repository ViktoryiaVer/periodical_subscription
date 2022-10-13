package com.periodicalsubscription.service.impl;

import com.periodicalsubscription.model.dao.PaymentDao;
import com.periodicalsubscription.model.entity.Payment;
import com.periodicalsubscription.service.api.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentDao paymentDao;

    @Override
    public List<Payment> findAll() {
        return paymentDao.findAll();
    }

    @Override
    public Payment findById(Long id) {
        Optional<Payment> payment = paymentDao.findById(id);

        return payment.orElseThrow(RuntimeException::new);
    }

    @Override
    public Payment save(Payment payment) {
        //TODO some validation
        return paymentDao.save(payment);
    }

    @Override
    public Payment update(Payment payment) {
        //TODO some validation
        return paymentDao.save(payment);
    }

    @Override
    public void delete(Payment payment) {
        //TODO some validation?
        paymentDao.delete(payment);
    }
}
