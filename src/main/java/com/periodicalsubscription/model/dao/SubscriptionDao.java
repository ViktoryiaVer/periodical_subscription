package com.periodicalsubscription.model.dao;

import com.periodicalsubscription.model.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionDao extends JpaRepository<Subscription, Long> {
}
