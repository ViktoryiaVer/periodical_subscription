package com.periodicalsubscription.service.api;

import com.periodicalsubscription.dto.SubscriptionDetailDto;

import java.time.LocalDate;
import java.util.List;

public interface SubscriptionDetailService {
    List<SubscriptionDetailDto> findAll();

    SubscriptionDetailDto findById(Long id);

    SubscriptionDetailDto save(SubscriptionDetailDto dto);

    SubscriptionDetailDto update (SubscriptionDetailDto dto);

    void delete(SubscriptionDetailDto dto);

    SubscriptionDetailDto updateSubscriptionPeriod(LocalDate startDate, Integer subscriptionDuration, Long id);
}
