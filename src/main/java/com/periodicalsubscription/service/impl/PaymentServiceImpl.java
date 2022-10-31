package com.periodicalsubscription.service.impl;

import com.periodicalsubscription.dto.SubscriptionDto;
import com.periodicalsubscription.exceptions.payment.PaymentNotFoundException;
import com.periodicalsubscription.exceptions.payment.PaymentServiceException;
import com.periodicalsubscription.mapper.PaymentMapper;
import com.periodicalsubscription.model.repository.PaymentRepository;
import com.periodicalsubscription.model.entity.Payment;
import com.periodicalsubscription.service.api.PaymentService;
import com.periodicalsubscription.dto.PaymentDto;
import com.periodicalsubscription.service.api.SubscriptionDetailService;
import com.periodicalsubscription.service.api.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper mapper;
    private final SubscriptionService subscriptionService;
    private final SubscriptionDetailService subscriptionDetailService;

    @Override
    public List<PaymentDto> findAll() {
        return paymentRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentDto findById(Long id) {
        Payment payment = paymentRepository.findById(id).orElseThrow(() -> {
            throw new PaymentNotFoundException("Payment with id  " + id + " could not be found.");
        });
        return mapper.toDto(payment);
    }

    @Override
    public PaymentDto save(PaymentDto dto) {
        PaymentDto savedPayment = mapper.toDto(paymentRepository.save(mapper.toEntity(dto)));
        if(savedPayment == null) {
            throw new PaymentServiceException("Error while saving payment.");
        }
        return savedPayment;
    }

    @Override
    public PaymentDto update(PaymentDto dto) {
        PaymentDto updatedPayment = mapper.toDto(paymentRepository.save(mapper.toEntity(dto)));
        if(updatedPayment == null) {
            throw new PaymentServiceException("Error while updating payment with id " + dto.getId() + ".");
        }
        return updatedPayment;
    }

    @Override
    public void deleteById(Long id) {
        paymentRepository.deleteById(id);
        if(paymentRepository.existsById(id)) {
            throw new PaymentServiceException("Error while deleting payment with id " + id + ".");
        }
    }


    @Transactional
    @Override
    public PaymentDto processPaymentRegistration(Long subscriptionId, String paymentTime, String paymentMethod) {
        PaymentDto paymentDto = createPaymentDto(subscriptionId, paymentTime, paymentMethod);
        PaymentDto savedPayment = save(paymentDto);

        SubscriptionDto subscriptionDto = subscriptionService.updateSubscriptionStatus(SubscriptionDto.StatusDto.PAYED, paymentDto.getSubscriptionDto().getId());
        subscriptionDto.getSubscriptionDetailDtos().forEach((detail -> subscriptionDetailService.updateSubscriptionPeriod(savedPayment.getPaymentTime().toLocalDate(), detail.getSubscriptionDurationInYears(), detail.getId())));
        return savedPayment;
    }

    @Override
    public PaymentDto processPaymentUpdate(Long paymentId, String paymentTime, String paymentMethodDto) {
        PaymentDto foundPayment = findById(paymentId);
        foundPayment.setPaymentTime(LocalDateTime.parse(paymentTime));
        foundPayment.setPaymentMethodDto(PaymentDto.PaymentMethodDto.valueOf(paymentMethodDto));
        return update(foundPayment);
    }

    private PaymentDto createPaymentDto(Long subscriptionId, String paymentTime, String paymentMethod) {
        PaymentDto paymentDto = new  PaymentDto();
        SubscriptionDto subscriptionDto = subscriptionService.findById(subscriptionId);
        paymentDto.setUserDto(subscriptionDto.getUserDto());
        paymentDto.setSubscriptionDto(subscriptionDto);
        paymentDto.setPaymentTime(LocalDateTime.parse(paymentTime));
        paymentDto.setPaymentMethodDto(PaymentDto.PaymentMethodDto.valueOf(paymentMethod));
        return paymentDto;
    }
}
