package com.periodicalsubscription.service.api;

import java.time.LocalDate;

/**
 * interface with methods for subscription detail business logic
 */
public interface SubscriptionDetailService {

    /**
     * updates subscription period of the subscription detail
     * @param startDate start date as LocalDate to be set
     * @param subscriptionDuration end date as LocalDate to be set
     * @param id id of the subscription detail
     */
    void updateSubscriptionPeriod(LocalDate startDate, Integer subscriptionDuration, Long id);

    /**
     * checks if subscription detail exists by periodical
     * @param id id of the periodical
     * @return true if exists, false otherwise
     */
    boolean checkIfSubscriptionDetailExistsByPeriodicalId(Long id);
}
