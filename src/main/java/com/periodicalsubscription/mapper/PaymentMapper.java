package com.periodicalsubscription.mapper;

import com.periodicalsubscription.dto.PaymentDto;
import com.periodicalsubscription.model.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class, SubscriptionMapper.class})
public interface PaymentMapper {
    @Mapping(source = "user", target = "userDto")
    @Mapping(source = "subscription", target = "subscriptionDto")
    @Mapping(source = "paymentMethod", target = "paymentMethodDto")
    PaymentDto toDto(Payment payment);

    @Mapping(source = "userDto", target = "user")
    @Mapping(source = "subscriptionDto", target = "subscription")
    @Mapping(source = "paymentMethodDto", target = "paymentMethod")
    Payment toEntity(PaymentDto paymentDto);
}
