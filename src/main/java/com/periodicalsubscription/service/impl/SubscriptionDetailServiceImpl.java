package com.periodicalsubscription.service.impl;

import com.periodicalsubscription.mapper.SubscriptionDetailMapper;
import com.periodicalsubscription.model.repository.SubscriptionDetailRepository;
import com.periodicalsubscription.model.entity.SubscriptionDetail;
import com.periodicalsubscription.service.api.SubscriptionDetailService;
import com.periodicalsubscription.dto.SubscriptionDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionDetailServiceImpl implements SubscriptionDetailService {
    private final SubscriptionDetailRepository subscriptionDetailRepository;
    private final SubscriptionDetailMapper mapper;

    @Override
    public List<SubscriptionDetailDto> findAll() {
        return subscriptionDetailRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SubscriptionDetailDto findById(Long id) {
        SubscriptionDetail detail = subscriptionDetailRepository.findById(id).orElseThrow(RuntimeException::new);

        return mapper.toDto(detail);
    }

    @Override
    public SubscriptionDetailDto save(SubscriptionDetailDto dto) {
        //TODO some validation
        return mapper.toDto(subscriptionDetailRepository.save(mapper.toEntity(dto)));
    }

    @Override
    public SubscriptionDetailDto update(SubscriptionDetailDto dto) {
        //TODO some validation
        return mapper.toDto(subscriptionDetailRepository.save(mapper.toEntity(dto)));
    }

    @Override
    public void delete(SubscriptionDetailDto dto) {
        //TODO some validation?
        subscriptionDetailRepository.delete(mapper.toEntity(dto));
    }
    @Transactional
    @Override
    public SubscriptionDetailDto updateSubscriptionPeriod(LocalDate startDate, Integer subscriptionDuration, Long id) {
        LocalDate endDate = startDate.plusYears(subscriptionDuration).minusDays(1);

        subscriptionDetailRepository.updateSubscriptionStartDate(startDate, id);
        subscriptionDetailRepository.updateSubscriptionEndDate(endDate, id);
        return findById(id);
    }
}
