package com.periodicalsubscription.service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SubscriptionDetailDto {
    private Long id;
    private SubscriptionDto subscriptionDto;
    private PeriodicalDto periodicalDto;
    private Integer subscriptionDurationInYears;
    private BigDecimal periodicalCurrentPrice;
}
