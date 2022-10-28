package com.periodicalsubscription.model.repository;

import com.periodicalsubscription.model.entity.Subscription;
import com.periodicalsubscription.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    @Modifying
    @Query("update Subscription s set s.status = :status where s.id = :id")
    void updateSubscriptionStatus(Subscription.Status status, Long id);

    boolean existsSubscriptionByUser(User user);

    List<Subscription> findAllByUser(User user);
}
