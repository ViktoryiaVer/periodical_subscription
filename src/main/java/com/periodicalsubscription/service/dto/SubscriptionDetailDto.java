package com.periodicalsubscription.service.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Class describing dto object for subscription detail entity
 */
@Data
public class SubscriptionDetailDto {
    private Long id;
    private SubscriptionDto subscriptionDto;
    private PeriodicalDto periodicalDto;
    private Integer subscriptionDurationInYears;
    private LocalDate subscriptionStartDate;
    private LocalDate subscriptionEndDate;
    private BigDecimal periodicalCurrentPrice;
}
