package com.periodicalsubscription.model.repository;

import com.periodicalsubscription.model.entity.SubscriptionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface SubscriptionDetailRepository extends JpaRepository<SubscriptionDetail, Long> {
    @Modifying
    @Query("update SubscriptionDetail s set s.subscriptionStartDate = :date where s.id = :id")
    void updateSubscriptionStartDate(LocalDate date, Long id);

    @Modifying
    @Query("update SubscriptionDetail s set s.subscriptionEndDate = :date where s.id = :id")
    void updateSubscriptionEndDate(LocalDate date, Long id);
}
