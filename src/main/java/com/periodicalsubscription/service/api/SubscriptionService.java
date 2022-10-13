package com.periodicalsubscription.service.api;

import com.periodicalsubscription.model.entity.Subscription;

import java.util.List;

public interface SubscriptionService {
    List<Subscription> findAll();

    Subscription findById(Long id);

    Subscription save(Subscription subscription);

    Subscription update (Subscription subscription);

    void delete(Subscription subscription);
}
