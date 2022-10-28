package com.periodicalsubscription.service.impl;

import com.periodicalsubscription.dto.PeriodicalDto;
import com.periodicalsubscription.dto.SubscriptionDetailDto;
import com.periodicalsubscription.dto.UserDto;
import com.periodicalsubscription.mapper.SubscriptionMapper;
import com.periodicalsubscription.mapper.UserMapper;
import com.periodicalsubscription.model.repository.SubscriptionRepository;
import com.periodicalsubscription.model.entity.Subscription;
import com.periodicalsubscription.service.api.PeriodicalService;
import com.periodicalsubscription.service.api.SubscriptionService;
import com.periodicalsubscription.dto.SubscriptionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final PeriodicalService periodicalService;
    private final SubscriptionMapper mapper;
    private final UserMapper userMapper;

    @Override
    public List<SubscriptionDto> findAll() {
        return subscriptionRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SubscriptionDto findById(Long id) {
        Subscription subscription = subscriptionRepository.findById(id).orElseThrow(RuntimeException::new);

        return mapper.toDto(subscription);
    }

    @Override
    public SubscriptionDto save(SubscriptionDto dto) {
        //TODO some validation
        return mapper.toDto(subscriptionRepository.save(mapper.toEntity(dto)));
    }

    @Override
    public SubscriptionDto update(SubscriptionDto dto) {
        //TODO some validation?
        return mapper.toDto(subscriptionRepository.save(mapper.toEntity(dto)));
    }

    @Override
    public void delete(SubscriptionDto dto) {
        //TODO some validation?
        subscriptionRepository.delete(mapper.toEntity(dto));
    }

    @Override
    public SubscriptionDto createSubscriptionFromCart(UserDto userDto, Map<Long, Integer> cart) {
        SubscriptionDto subscription = processSubscriptionInCart(userDto, cart);
        return save(subscription);
    }

    @Override
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

    private BigDecimal calculateTotalCost(List<SubscriptionDetailDto> details) {
        BigDecimal totalCost = BigDecimal.ZERO;
        for (SubscriptionDetailDto detail : details) {
            BigDecimal periodicalPrice = detail.getPeriodicalCurrentPrice();
            totalCost = totalCost.add(periodicalPrice.multiply(BigDecimal.valueOf(detail.getSubscriptionDurationInYears())));
        }
        return totalCost;
    }

    @Transactional
    @Override
    public SubscriptionDto updateSubscriptionStatus(SubscriptionDto.StatusDto status, Long id) {
        subscriptionRepository.updateSubscriptionStatus(Subscription.Status.valueOf(status.toString()), id);
        return findById(id);
    }

    @Override
    public boolean checkIfSubscriptionExistsByUSer(UserDto userDto) {
        return subscriptionRepository.existsSubscriptionByUser(userMapper.toEntity(userDto));
    }

    @Override
    public List<SubscriptionDto> findAllSubscriptionsByUser(UserDto userDto) {
        return subscriptionRepository.findAllByUser(userMapper.toEntity(userDto)).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
