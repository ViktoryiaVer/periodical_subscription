package com.periodicalsubscription.service.impl;

import com.periodicalsubscription.model.repository.PaymentRepository;
import com.periodicalsubscription.model.entity.Payment;
import com.periodicalsubscription.service.api.PaymentService;
import com.periodicalsubscription.dto.PaymentDto;
import com.periodicalsubscription.mapper.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final ObjectMapper mapper;

    @Override
    public List<PaymentDto> findAll() {
        return paymentRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentDto findById(Long id) {
        Payment payment = paymentRepository.findById(id).orElseThrow(RuntimeException::new);

        return mapper.toDto(payment);
    }

    @Override
    public PaymentDto save(PaymentDto dto) {
        //TODO some validation
        return mapper.toDto(paymentRepository.save(mapper.toEntity(dto)));
    }

    @Override
    public PaymentDto update(PaymentDto dto) {
        //TODO some validation
        return mapper.toDto(paymentRepository.save(mapper.toEntity(dto)));
    }

    @Override
    public void delete(PaymentDto dto) {
        //TODO some validation?
        paymentRepository.delete(mapper.toEntity(dto));
    }
}
