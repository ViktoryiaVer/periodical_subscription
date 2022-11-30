package com.periodicalsubscription.service.dto.filter;

import lombok.Data;

/**
 * Class describing dto object for filtering payments
 */
@Data
public class PaymentFilterDto {
    private String paymentMethod;
    private String paymentDate;
}
