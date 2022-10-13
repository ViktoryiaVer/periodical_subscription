package com.periodicalsubscription.service.api;

import com.periodicalsubscription.model.entity.SubscriptionDetail;

import java.util.List;

public interface SubscriptionDetailService {
    List<SubscriptionDetail> findAll();

    SubscriptionDetail findById(Long id);

    SubscriptionDetail save(SubscriptionDetail subscriptionDetail);

    SubscriptionDetail update (SubscriptionDetail subscriptionDetail);

    void delete(SubscriptionDetail subscriptionDetail);
}
