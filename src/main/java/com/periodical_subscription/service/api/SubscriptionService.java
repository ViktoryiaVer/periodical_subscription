package com.periodical_subscription.service.api;

import com.periodical_subscription.model.entity.Subscription;

import java.util.List;

public interface SubscriptionService {
    List<Subscription> findAll();

    Subscription findById(Long id);

    Subscription save(Subscription subscription);

    Subscription update (Subscription subscription);

    void delete(Subscription subscription);
}
