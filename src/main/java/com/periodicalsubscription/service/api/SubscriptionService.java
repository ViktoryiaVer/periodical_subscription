package com.periodicalsubscription.service.api;

import com.periodicalsubscription.dto.SubscriptionDto;
import com.periodicalsubscription.dto.UserDto;

import java.util.List;
import java.util.Map;

public interface SubscriptionService {
    List<SubscriptionDto> findAll();

    SubscriptionDto findById(Long id);

    SubscriptionDto save(SubscriptionDto dto);

    SubscriptionDto update (SubscriptionDto dto);

    void delete(SubscriptionDto dto);

    SubscriptionDto processSubscriptionInCart(UserDto userDto, Map<Long, Integer> cart);
}
