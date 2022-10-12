package com.periodical_subscription.service.api;

import com.periodical_subscription.model.entity.SubscriptionDetail;

import java.util.List;

public interface SubscriptionDetailService {
    List<SubscriptionDetail> findAll();

    SubscriptionDetail findById(Long id);

    SubscriptionDetail save(SubscriptionDetail subscriptionDetail);

    SubscriptionDetail update (SubscriptionDetail subscriptionDetail);

    void delete(SubscriptionDetail subscriptionDetail);
}
