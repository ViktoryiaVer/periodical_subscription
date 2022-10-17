package com.periodicalsubscription.service.impl;

import com.periodicalsubscription.mapper.SubscriptionMapper;
import com.periodicalsubscription.model.repository.SubscriptionRepository;
import com.periodicalsubscription.model.entity.Subscription;
import com.periodicalsubscription.service.api.SubscriptionService;
import com.periodicalsubscription.dto.SubscriptionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper mapper;

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
}
