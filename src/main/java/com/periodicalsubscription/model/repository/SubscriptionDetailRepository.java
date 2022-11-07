package com.periodicalsubscription.model.repository;

import com.periodicalsubscription.constant.HqlConstant;
import com.periodicalsubscription.model.entity.Periodical;
import com.periodicalsubscription.model.entity.SubscriptionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface SubscriptionDetailRepository extends JpaRepository<SubscriptionDetail, Long> {
    @Modifying
    @Query(HqlConstant.HQL_UPDATE_SUBSCRIPTION_START_DATE)
    void updateSubscriptionStartDate(LocalDate date, Long id);

    @Modifying
    @Query(HqlConstant.HQL_UPDATE_SUBSCRIPTION_END_DATE)
    void updateSubscriptionEndDate(LocalDate date, Long id);

    boolean existsSubscriptionDetailByPeriodical(Periodical periodical);
}
