package com.periodicalsubscription.service.dto.filter;

import lombok.Data;

@Data
public class PaymentFilterDto {
    private String paymentMethod;
    private String paymentDate;
}
