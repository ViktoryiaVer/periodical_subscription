package com.periodical_subscription.service.impl;

import com.periodical_subscription.model.dao.SubscriptionDao;
import com.periodical_subscription.model.dao.UserDao;
import com.periodical_subscription.model.entity.Subscription;
import com.periodical_subscription.service.api.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionDao subscriptionDao;

    @Override
    public List<Subscription> findAll() {
        return subscriptionDao.findAll();
    }

    @Override
    public Subscription findById(Long id) {
        Optional<Subscription> subscription = subscriptionDao.findById(id);

        return subscription.orElseThrow(RuntimeException::new);
    }

    @Override
    public Subscription save(Subscription subscription) {
        //TODO some validation
        return subscriptionDao.save(subscription);
    }

    @Override
    public Subscription update(Subscription subscription) {
        //TODO some validation?
        return subscriptionDao.save(subscription);
    }

    @Override
    public void delete(Subscription subscription) {
        //TODO some validation?
        subscriptionDao.delete(subscription);
    }
}
