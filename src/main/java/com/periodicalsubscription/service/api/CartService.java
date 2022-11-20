package com.periodicalsubscription.service.api;

import java.util.Map;

public interface CartService {
    Map<Long, Integer> add(Long periodicalId, Integer subscriptionDurationInYears, Map<Long, Integer> cart);
}
