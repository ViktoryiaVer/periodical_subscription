package com.periodical_subscription.service.impl;

import com.periodical_subscription.model.dao.SubscriptionDetailDao;
import com.periodical_subscription.model.entity.Subscription;
import com.periodical_subscription.model.entity.SubscriptionDetail;
import com.periodical_subscription.service.api.SubscriptionDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscriptionDetailServiceImpl implements SubscriptionDetailService {
    private final SubscriptionDetailDao subscriptionDetailDao;
    @Override
    public List<SubscriptionDetail> findAll() {
        return subscriptionDetailDao.findAll();
    }

    @Override
    public SubscriptionDetail findById(Long id) {
        Optional<SubscriptionDetail> subscriptionDetail = subscriptionDetailDao.findById(id);

        return subscriptionDetail.orElseThrow(RuntimeException::new);
    }

    @Override
    public SubscriptionDetail save(SubscriptionDetail subscriptionDetail) {
        //TODO some validation
        return subscriptionDetailDao.save(subscriptionDetail);
    }

    @Override
    public SubscriptionDetail update(SubscriptionDetail subscriptionDetail) {
        //TODO some validation
        return subscriptionDetailDao.save(subscriptionDetail);
    }

    @Override
    public void delete(SubscriptionDetail subscriptionDetail) {
        //TODO some validation?
        subscriptionDetailDao.delete(subscriptionDetail);
    }
}
