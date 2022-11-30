package com.periodicalsubscription.model.repository;

import com.periodicalsubscription.constant.HqlConstant;
import com.periodicalsubscription.model.entity.SubscriptionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

/**
 * Interface extending JpaRepository interface for managing SubscriptionDetail entities
 */
@Repository
public interface SubscriptionDetailRepository extends JpaRepository<SubscriptionDetail, Long> {

    /**
     * updates subscription start date
     * @param date date to be set
     * @param id id of subscription detail to be updated
     */
    @Modifying
    @Query(HqlConstant.HQL_UPDATE_SUBSCRIPTION_START_DATE)
    void updateSubscriptionStartDate(LocalDate date, Long id);

    /**
     * updates subscription end date
     * @param date date to be set
     * @param id id of subscription detail to be updated
     */
    @Modifying
    @Query(HqlConstant.HQL_UPDATE_SUBSCRIPTION_END_DATE)
    void updateSubscriptionEndDate(LocalDate date, Long id);

    /**
     * checks if subscription detail exist by periodical
     * @param id id of periodical to be checked
     * @return true if subscription detail exists, false otherwise
     */
    boolean existsSubscriptionDetailByPeriodicalId(Long id);
}
