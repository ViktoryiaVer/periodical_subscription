package com.periodicalsubscription.service.api;

import java.util.Map;

/**
 * interface with methods for cart business logic
 */
public interface CartService {
    /**
     * adds periodical to cart
     * @param periodicalId id of the periodical to be added to cart
     * @param subscriptionDurationInYears subscription duration for a periodical
     * @param cart Map object of cart
     * @return cart with a periodical added
     */
    Map<Long, Integer> add(Long periodicalId, Integer subscriptionDurationInYears, Map<Long, Integer> cart);
}
