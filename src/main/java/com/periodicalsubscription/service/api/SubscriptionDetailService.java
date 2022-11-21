package com.periodicalsubscription.service.api;

import com.periodicalsubscription.service.dto.SubscriptionDetailDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface SubscriptionDetailService {
    Page<SubscriptionDetailDto> findAll(Pageable pageable);

    SubscriptionDetailDto findById(Long id);

    SubscriptionDetailDto save(SubscriptionDetailDto dto);

    SubscriptionDetailDto update (SubscriptionDetailDto dto);

    void deleteById(Long id);

    void updateSubscriptionPeriod(LocalDate startDate, Integer subscriptionDuration, Long id);

    boolean checkIfSubscriptionDetailExistsByPeriodicalId(Long id);
}
