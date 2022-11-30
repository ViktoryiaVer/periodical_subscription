package com.periodicalsubscription.mapper;

import com.periodicalsubscription.service.dto.SubscriptionDto;
import com.periodicalsubscription.model.entity.Subscription;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Interface for mapping between Subscription and SubscriptionDto
 */
@Mapper(componentModel = "spring", uses = {SubscriptionDetailMapper.class, UserMapper.class}, collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface SubscriptionMapper {

    /**
     * converts Subscription to SubscriptionDto
     * @param subscription Subscription object to be converted
     * @return SubscriptionDto object
     */
    @Mapping(source = "subscription.status", target = "statusDto")
    @Mapping(source = "subscription.user", target = "userDto")
    @Mapping(source = "subscription.subscriptionDetails", target = "subscriptionDetailDtos")
    SubscriptionDto toDto(Subscription subscription);

    /**
     * converts SubscriptionDto to Subscription
     * @param subscriptionDto SubscriptionDto object to be converted
     * @return Subscription object
     */
    @Mapping(source = "subscriptionDto.statusDto", target = "status")
    @Mapping(source = "subscriptionDto.userDto", target = "user")
    @Mapping(source = "subscriptionDto.subscriptionDetailDtos", target = "subscriptionDetails")
    Subscription toEntity(SubscriptionDto subscriptionDto);
}
