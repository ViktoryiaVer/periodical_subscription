package com.periodicalsubscription.service.impl;

import com.periodicalsubscription.aspect.logging.annotation.LogInvocationService;
import com.periodicalsubscription.aspect.logging.annotation.ServiceEx;
import com.periodicalsubscription.dto.PeriodicalDto;
import com.periodicalsubscription.dto.SubscriptionDetailDto;
import com.periodicalsubscription.dto.UserDto;
import com.periodicalsubscription.exceptions.subscription.SubscriptionServiceException;
import com.periodicalsubscription.mapper.SubscriptionMapper;
import com.periodicalsubscription.mapper.UserMapper;
import com.periodicalsubscription.model.repository.SubscriptionRepository;
import com.periodicalsubscription.model.entity.Subscription;
import com.periodicalsubscription.service.api.PeriodicalService;
import com.periodicalsubscription.service.api.SubscriptionService;
import com.periodicalsubscription.dto.SubscriptionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final PeriodicalService periodicalService;
    private final SubscriptionMapper mapper;
    private final UserMapper userMapper;
    private final MessageSource messageSource;

    @Override
    @LogInvocationService
    public Page<SubscriptionDto> findAll(Pageable pageable) {
        return subscriptionRepository.findAll(pageable).map(mapper::toDto);
    }

    @Override
    @LogInvocationService
    @ServiceEx
    public SubscriptionDto findById(Long id) {
        Subscription subscription = subscriptionRepository.findById(id).orElseThrow(() -> {
            throw new SubscriptionServiceException(messageSource.getMessage("msg.error.subscription.find.by.id", null,
                    LocaleContextHolder.getLocale()));
        });
        return mapper.toDto(subscription);
    }

    @Override
    @LogInvocationService
    @ServiceEx
    public SubscriptionDto save(SubscriptionDto dto) {
        SubscriptionDto savedSubscription = mapper.toDto(subscriptionRepository.save(mapper.toEntity(dto)));
        if (savedSubscription == null) {
            throw new SubscriptionServiceException(messageSource.getMessage("msg.error.subscription.service.save", null,
                    LocaleContextHolder.getLocale()));
        }
        return savedSubscription;

    }

    @Override
    @LogInvocationService
    @ServiceEx
    public SubscriptionDto update(SubscriptionDto dto) {
        SubscriptionDto updatedSubscription = mapper.toDto(subscriptionRepository.save(mapper.toEntity(dto)));
        if (updatedSubscription == null) {
            throw new SubscriptionServiceException(messageSource.getMessage("msg.error.subscription.service.update", null,
                    LocaleContextHolder.getLocale()));
        }
        return updatedSubscription;
    }

    @Override
    @LogInvocationService
    @ServiceEx
    public void deleteById(Long id) {
        subscriptionRepository.deleteById(id);
        if (subscriptionRepository.existsById(id)) {
            throw new SubscriptionServiceException(messageSource.getMessage("msg.error.subscription.service.delete", null,
                    LocaleContextHolder.getLocale()));
        }
    }

    @Override
    @LogInvocationService
    public SubscriptionDto createSubscriptionFromCart(UserDto userDto, Map<Long, Integer> cart) {
        SubscriptionDto subscription = processSubscriptionInCart(userDto, cart);
        return save(subscription);
    }

    @Override
    @LogInvocationService
    public SubscriptionDto processSubscriptionInCart(UserDto userDto, Map<Long, Integer> cart) {
        SubscriptionDto subscription = new SubscriptionDto();
        subscription.setUserDto(userDto);
        subscription.setStatusDto(SubscriptionDto.StatusDto.PENDING);
        List<SubscriptionDetailDto> subscriptionDetails = new ArrayList<>();

        cart.forEach((periodicalId, durationInYears) -> {
            SubscriptionDetailDto detail = new SubscriptionDetailDto();
            PeriodicalDto periodical = periodicalService.findById(periodicalId);
            detail.setPeriodicalDto(periodical);
            detail.setSubscriptionDurationInYears(durationInYears);
            detail.setPeriodicalCurrentPrice(periodical.getPrice());
            subscriptionDetails.add(detail);
        });
        subscription.setSubscriptionDetailDtos(subscriptionDetails);
        BigDecimal totalCost = calculateTotalCost(subscriptionDetails);
        subscription.setTotalCost(totalCost);
        return subscription;
    }

    @LogInvocationService
    private BigDecimal calculateTotalCost(List<SubscriptionDetailDto> details) {
        BigDecimal totalCost = BigDecimal.ZERO;
        for (SubscriptionDetailDto detail : details) {
            BigDecimal periodicalPrice = detail.getPeriodicalCurrentPrice();
            totalCost = totalCost.add(periodicalPrice.multiply(BigDecimal.valueOf(detail.getSubscriptionDurationInYears())));
        }
        return totalCost;
    }

    @Override
    @LogInvocationService
    @Transactional
    public SubscriptionDto updateSubscriptionStatus(SubscriptionDto.StatusDto status, Long id) {
        subscriptionRepository.updateSubscriptionStatus(Subscription.Status.valueOf(status.toString()), id);
        return findById(id);
    }

    @Override
    @LogInvocationService
    public boolean checkIfSubscriptionExistsByUSer(UserDto userDto) {
        return subscriptionRepository.existsSubscriptionByUser(userMapper.toEntity(userDto));
    }

    @Override
    @LogInvocationService
    public Page<SubscriptionDto> findAllSubscriptionsByUserId(Long id, Pageable pageable) {
        return subscriptionRepository.findAllByUserId(id, pageable).map(mapper::toDto);
    }
}
