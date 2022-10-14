package com.periodicalsubscription.service.impl;

import com.periodicalsubscription.model.repository.SubscriptionRepository;
import com.periodicalsubscription.model.entity.Subscription;
import com.periodicalsubscription.service.api.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    @Override
    public List<Subscription> findAll() {
        return subscriptionRepository.findAll();
    }

    @Override
    public Subscription findById(Long id) {
        Optional<Subscription> subscription = subscriptionRepository.findById(id);

        return subscription.orElseThrow(RuntimeException::new);
    }

    @Override
    public Subscription save(Subscription subscription) {
        //TODO some validation
        return subscriptionRepository.save(subscription);
    }

    @Override
    public Subscription update(Subscription subscription) {
        //TODO some validation?
        return subscriptionRepository.save(subscription);
    }

    @Override
    public void delete(Subscription subscription) {
        //TODO some validation?
        subscriptionRepository.delete(subscription);
    }
}
