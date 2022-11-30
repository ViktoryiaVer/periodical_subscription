package com.periodicalsubscription.mapper;

import com.periodicalsubscription.service.dto.PaymentDto;
import com.periodicalsubscription.model.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Interface for mapping between Payment and PaymentDto
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, SubscriptionMapper.class})
public interface PaymentMapper {

    /**
     * converts Payment to PaymentDto
     * @param payment Payment object to be converted
     * @return PaymentDto object
     */
    @Mapping(source = "user", target = "userDto")
    @Mapping(source = "subscription", target = "subscriptionDto")
    @Mapping(source = "paymentMethod", target = "paymentMethodDto")
    PaymentDto toDto(Payment payment);

    /**
     * converts PaymentDto to Payment
     * @param paymentDto PaymentDto object to be converted
     * @return Payment object
     */
    @Mapping(source = "userDto", target = "user")
    @Mapping(source = "subscriptionDto", target = "subscription")
    @Mapping(source = "paymentMethodDto", target = "paymentMethod")
    Payment toEntity(PaymentDto paymentDto);
}
