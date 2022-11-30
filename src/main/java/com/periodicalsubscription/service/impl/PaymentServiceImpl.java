package com.periodicalsubscription.service.impl;

import com.periodicalsubscription.aspect.logging.annotation.LogInvocationService;
import com.periodicalsubscription.aspect.logging.annotation.ServiceEx;
import com.periodicalsubscription.model.specification.PaymentSpecifications;
import com.periodicalsubscription.service.dto.SubscriptionDto;
import com.periodicalsubscription.exceptions.payment.PaymentNotFoundException;
import com.periodicalsubscription.exceptions.payment.PaymentServiceException;
import com.periodicalsubscription.mapper.PaymentMapper;
import com.periodicalsubscription.model.repository.PaymentRepository;
import com.periodicalsubscription.model.entity.Payment;
import com.periodicalsubscription.service.api.PaymentService;
import com.periodicalsubscription.service.dto.PaymentDto;
import com.periodicalsubscription.service.api.SubscriptionDetailService;
import com.periodicalsubscription.service.api.SubscriptionService;
import com.periodicalsubscription.service.dto.filter.PaymentFilterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper mapper;
    private final SubscriptionService subscriptionService;
    private final SubscriptionDetailService subscriptionDetailService;
    private final MessageSource messageSource;

    @Override
    @LogInvocationService
    public Page<PaymentDto> findAll(Pageable pageable) {
        return paymentRepository.findAll(pageable).map(mapper::toDto);
    }

    @Override
    @LogInvocationService
    @ServiceEx
    public PaymentDto findById(Long id) {
        Payment payment = paymentRepository.findById(id).orElseThrow(() -> {
            throw new PaymentNotFoundException(messageSource.getMessage("msg.error.payment.find.by.id", null,
                    LocaleContextHolder.getLocale()));
        });
        return mapper.toDto(payment);
    }

    @Override
    @LogInvocationService
    @ServiceEx
    @Transactional
    public PaymentDto save(PaymentDto dto) {
        PaymentDto savedPayment = mapper.toDto(paymentRepository.save(mapper.toEntity(dto)));
        if (savedPayment == null) {
            throw new PaymentServiceException(messageSource.getMessage("msg.error.payment.service.save", null,
                    LocaleContextHolder.getLocale()));
        }
        return savedPayment;
    }

    @Override
    @LogInvocationService
    @ServiceEx
    @Transactional
    public PaymentDto update(PaymentDto dto) {
        PaymentDto updatedPayment = mapper.toDto(paymentRepository.save(mapper.toEntity(dto)));
        if (updatedPayment == null) {
            throw new PaymentServiceException(messageSource.getMessage("msg.error.payment.service.update", null,
                    LocaleContextHolder.getLocale()));
        }
        return updatedPayment;
    }

    @Override
    @LogInvocationService
    @ServiceEx
    @Transactional
    public void deleteById(Long id) {
        paymentRepository.deleteById(id);
        if (paymentRepository.existsById(id)) {
            throw new PaymentServiceException(messageSource.getMessage("msg.error.payment.service.delete", null,
                    LocaleContextHolder.getLocale()));
        }
    }

    @Override
    @LogInvocationService
    @Transactional
    public PaymentDto processPaymentRegistration(Long subscriptionId, String paymentTime, String paymentMethod) {
        PaymentDto paymentDto = createPaymentDto(subscriptionId, paymentTime, paymentMethod);
        PaymentDto savedPayment = save(paymentDto);

        SubscriptionDto subscriptionDto = subscriptionService.updateSubscriptionStatus(SubscriptionDto.StatusDto.PAYED, paymentDto.getSubscriptionDto().getId());
        subscriptionDto.getSubscriptionDetailDtos().forEach((detail -> subscriptionDetailService.updateSubscriptionPeriod(savedPayment.getPaymentTime().toLocalDate(), detail.getSubscriptionDurationInYears(), detail.getId())));
        return savedPayment;
    }

    @Override
    @LogInvocationService
    @Transactional
    public PaymentDto processPaymentUpdate(Long paymentId, String paymentTime, String paymentMethodDto) {
        PaymentDto foundPayment = findById(paymentId);
        foundPayment.setPaymentTime(LocalDateTime.parse(paymentTime));
        foundPayment.setPaymentMethodDto(PaymentDto.PaymentMethodDto.valueOf(paymentMethodDto));
        return update(foundPayment);
    }

    @Override
    @LogInvocationService
    public Page<PaymentDto> filterPayment(PaymentFilterDto filterDto, Pageable pageable) {
        Specification<Payment> specification = Specification
                .where(filterDto.getPaymentMethod() == null  || filterDto.getPaymentMethod().isEmpty() ? null : PaymentSpecifications.hasPaymentMethod(filterDto.getPaymentMethod()))
                .and(filterDto.getPaymentDate() == null || filterDto.getPaymentDate().isEmpty()  ? null : PaymentSpecifications.hasPaymentDate(filterDto.getPaymentDate()));
        return paymentRepository.findAll(specification, pageable).map(mapper::toDto);
    }

    @Override
    @LogInvocationService
    public Page<PaymentDto> searchForPaymentByKeyword(String keyword, Pageable pageable) {
        Specification<Payment> specification = PaymentSpecifications.hasIdLike(keyword)
                .or(PaymentSpecifications.hasSubscriptionIdLike(keyword))
                .or(PaymentSpecifications.hasUserIdLike(keyword))
                .or(PaymentSpecifications.hasUserEmailLike(keyword));
        return paymentRepository.findAll(specification, pageable).map(mapper::toDto);
    }

    @LogInvocationService
    private PaymentDto createPaymentDto(Long subscriptionId, String paymentTime, String paymentMethod) {
        PaymentDto paymentDto = new PaymentDto();
        SubscriptionDto subscriptionDto = subscriptionService.findById(subscriptionId);
        paymentDto.setUserDto(subscriptionDto.getUserDto());
        paymentDto.setSubscriptionDto(subscriptionDto);
        paymentDto.setPaymentTime(LocalDateTime.parse(paymentTime));
        paymentDto.setPaymentMethodDto(PaymentDto.PaymentMethodDto.valueOf(paymentMethod));
        return paymentDto;
    }
}
