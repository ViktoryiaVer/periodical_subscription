package com.periodicalsubscription.model.repository;

import com.periodicalsubscription.constant.HqlConstant;
import com.periodicalsubscription.model.entity.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long>, JpaSpecificationExecutor<Subscription> {
    @Modifying
    @Query(HqlConstant.HQL_UPDATE_SUBSCRIPTION_STATUS)
    void updateSubscriptionStatus(Subscription.Status status, Long id);

    boolean existsSubscriptionByUserId(Long id);

    Page<Subscription> findAllByUserId(Long id, Pageable pageable);
}
