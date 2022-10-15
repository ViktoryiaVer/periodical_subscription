package com.periodicalsubscription.service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentDto {
    private Long id;
    private UserDto userDto;
    private SubscriptionDto subscriptionDto;
    private LocalDateTime paymentTime;
    private PaymentMethodDto paymentMethodDto;

    public enum PaymentMethodDto {
        CASH,
        CHECK,
        CREDIT_OR_DEBIT_CARD,
        ONLINE_PAYMENT_SERVICE
    }
}
