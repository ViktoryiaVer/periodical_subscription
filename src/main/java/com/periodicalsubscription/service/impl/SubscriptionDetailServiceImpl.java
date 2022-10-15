package com.periodicalsubscription.service.impl;

import com.periodicalsubscription.model.repository.SubscriptionDetailRepository;
import com.periodicalsubscription.model.entity.SubscriptionDetail;
import com.periodicalsubscription.service.api.SubscriptionDetailService;
import com.periodicalsubscription.service.dto.SubscriptionDetailDto;
import com.periodicalsubscription.service.mapper.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionDetailServiceImpl implements SubscriptionDetailService {
    private final SubscriptionDetailRepository subscriptionDetailRepository;
    private final ObjectMapper mapper;

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
}
