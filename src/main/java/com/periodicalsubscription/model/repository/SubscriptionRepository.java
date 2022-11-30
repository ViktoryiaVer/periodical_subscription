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

/**
 * Interface extending JpaRepository and JpaSpecificationExecutor interfaces for managing Subscription entities
 */
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long>, JpaSpecificationExecutor<Subscription> {

    /**
     * updates subscription status
     * @param status status to be set
     * @param id id of the subscription to be updated
     */
    @Modifying
    @Query(HqlConstant.HQL_UPDATE_SUBSCRIPTION_STATUS)
    void updateSubscriptionStatus(Subscription.Status status, Long id);

    /**
     * checks if subscriptions exist by user
     * @param id id of the user
     * @return true if the user exists, false otherwise
     */
    boolean existsSubscriptionByUserId(Long id);

    /**
     * finds all subscriptions by user
     * @param id id of the user
     * @param pageable Pageable object for paging
     * @return subscription page
     */
    Page<Subscription> findAllByUserId(Long id, Pageable pageable);
}
