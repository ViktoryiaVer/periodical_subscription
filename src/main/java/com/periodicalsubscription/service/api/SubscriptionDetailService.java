package com.periodicalsubscription.service.api;

import com.periodicalsubscription.dto.PeriodicalDto;
import com.periodicalsubscription.dto.SubscriptionDetailDto;

import java.time.LocalDate;
import java.util.List;

public interface SubscriptionDetailService {
    List<SubscriptionDetailDto> findAll();

    SubscriptionDetailDto findById(Long id);

    SubscriptionDetailDto save(SubscriptionDetailDto dto);

    SubscriptionDetailDto update (SubscriptionDetailDto dto);

    void deleteById(Long id);

    void updateSubscriptionPeriod(LocalDate startDate, Integer subscriptionDuration, Long id);

    boolean checkIfSubscriptionExistsByPeriodical(PeriodicalDto periodicalDto);
}
