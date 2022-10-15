package com.periodicalsubscription.model.repository;

import com.periodicalsubscription.model.entity.SubscriptionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionDetailRepository extends JpaRepository<SubscriptionDetail, Long> {
}