package com.periodical_subscription.model.dao;

import com.periodical_subscription.model.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionDao extends JpaRepository<Subscription, Long> {
}
