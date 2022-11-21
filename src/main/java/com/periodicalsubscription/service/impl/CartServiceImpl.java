package com.periodicalsubscription.service.impl;

import com.periodicalsubscription.service.api.CartService;
import com.periodicalsubscription.service.api.PeriodicalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final PeriodicalService periodicalService;
    @Override
    public Map<Long, Integer> add(Long periodicalId, Integer subscriptionDurationInYears, Map<Long, Integer> cart) {
        if (cart == null) {
            cart = new HashMap<>();
        }

        periodicalService.checkIfPeriodicalIsUnavailable(periodicalId);
        cart.put(periodicalId, subscriptionDurationInYears);
        return cart;
    }
}
