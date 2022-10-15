package com.periodicalsubscription.service.api;

import com.periodicalsubscription.service.dto.SubscriptionDetailDto;

import java.util.List;

public interface SubscriptionDetailService {
    List<SubscriptionDetailDto> findAll();

    SubscriptionDetailDto findById(Long id);

    SubscriptionDetailDto save(SubscriptionDetailDto dto);

    SubscriptionDetailDto update (SubscriptionDetailDto dto);

    void delete(SubscriptionDetailDto dto);
}
