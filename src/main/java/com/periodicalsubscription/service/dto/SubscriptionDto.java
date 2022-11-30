package com.periodicalsubscription.service.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Class describing dto object for subscription entity
 */
@Data
public class SubscriptionDto {
    private Long id;
    private UserDto userDto;
    private BigDecimal totalCost;
    private StatusDto statusDto;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<SubscriptionDetailDto> subscriptionDetailDtos = new ArrayList<>();

    public void addSubscriptionDetailDto(SubscriptionDetailDto detailDto) {
        subscriptionDetailDtos.add(detailDto);
        detailDto.setSubscriptionDto(this);

    }

    public enum StatusDto {
        PENDING,
        AWAITING_PAYMENT,
        PAYED,
        CANCELED,
        COMPLETED
    }
}
