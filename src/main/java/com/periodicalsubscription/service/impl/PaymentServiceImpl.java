package com.periodicalsubscription.service.impl;

import com.periodicalsubscription.dto.SubscriptionDto;
import com.periodicalsubscription.mapper.PaymentMapper;
import com.periodicalsubscription.model.repository.PaymentRepository;
import com.periodicalsubscription.model.entity.Payment;
import com.periodicalsubscription.service.api.PaymentService;
import com.periodicalsubscription.dto.PaymentDto;
import com.periodicalsubscription.service.api.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper mapper;
    private final SubscriptionService subscriptionService;

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

    @Override
    public PaymentDto processPaymentRegistration(Long subscriptionId, String paymentTime, String paymentMethod) {
        PaymentDto paymentDto = new  PaymentDto();
        SubscriptionDto subscriptionDto = subscriptionService.findById(subscriptionId);
        paymentDto.setUserDto(subscriptionDto.getUserDto());
        paymentDto.setSubscriptionDto(subscriptionDto);
        paymentDto.setPaymentTime(LocalDateTime.parse(paymentTime));
        paymentDto.setPaymentMethodDto(PaymentDto.PaymentMethodDto.valueOf(paymentMethod));
        return paymentDto;
    }
}
