package com.periodicalsubscription.service.api;

import com.periodicalsubscription.service.dto.SubscriptionDto;

import java.util.List;

public interface SubscriptionService {
    List<SubscriptionDto> findAll();

    SubscriptionDto findById(Long id);

    SubscriptionDto save(SubscriptionDto dto);

    SubscriptionDto update (SubscriptionDto dto);

    void delete(SubscriptionDto dto);
}
