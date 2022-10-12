package com.periodical_subscription.model.dao;

import com.periodical_subscription.model.entity.SubscriptionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionDetailDao extends JpaRepository<SubscriptionDetail, Long> {
}
