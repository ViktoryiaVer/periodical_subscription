package com.periodicalsubscription.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

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
