package com.periodicalsubscription.mapper;

import com.periodicalsubscription.service.dto.SubscriptionDto;
import com.periodicalsubscription.model.entity.Subscription;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {SubscriptionDetailMapper.class, UserMapper.class}, collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface SubscriptionMapper {
    @Mapping(source = "subscription.status", target = "statusDto")
    @Mapping(source = "subscription.user", target = "userDto")
    @Mapping(source = "subscription.subscriptionDetails", target = "subscriptionDetailDtos")
    SubscriptionDto toDto(Subscription subscription);

    @Mapping(source = "subscriptionDto.statusDto", target = "status")
    @Mapping(source = "subscriptionDto.userDto", target = "user")
    @Mapping(source = "subscriptionDto.subscriptionDetailDtos", target = "subscriptionDetails")
    Subscription toEntity(SubscriptionDto subscriptionDto);
}
