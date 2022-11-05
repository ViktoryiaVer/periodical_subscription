package com.periodicalsubscription.model.repository;

import com.periodicalsubscription.model.entity.Subscription;
import com.periodicalsubscription.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    @Modifying
    @Query("update Subscription s set s.status = :status where s.id = :id")
    void updateSubscriptionStatus(Subscription.Status status, Long id);

    boolean existsSubscriptionByUser(User user);

    Page<Subscription> findAllByUserId(Long id, Pageable pageable);
}
