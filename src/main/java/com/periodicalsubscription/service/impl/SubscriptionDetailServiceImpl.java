package com.periodicalsubscription.service.impl;

import com.periodicalsubscription.aspect.logging.annotation.LogInvocationService;
import com.periodicalsubscription.model.repository.SubscriptionDetailRepository;
import com.periodicalsubscription.service.api.SubscriptionDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SubscriptionDetailServiceImpl implements SubscriptionDetailService {
    private final SubscriptionDetailRepository subscriptionDetailRepository;

    @Override
    @LogInvocationService
    @Transactional
    public void updateSubscriptionPeriod(LocalDate startDate, Integer subscriptionDuration, Long id) {
        LocalDate endDate = startDate.plusYears(subscriptionDuration).minusDays(1);

        subscriptionDetailRepository.updateSubscriptionStartDate(startDate, id);
        subscriptionDetailRepository.updateSubscriptionEndDate(endDate, id);
    }

    @Override
    @LogInvocationService
    public boolean checkIfSubscriptionDetailExistsByPeriodicalId(Long id) {
        return subscriptionDetailRepository.existsSubscriptionDetailByPeriodicalId(id);
    }
}
