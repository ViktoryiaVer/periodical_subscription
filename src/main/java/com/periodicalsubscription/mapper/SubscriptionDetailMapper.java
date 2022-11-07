package com.periodicalsubscription.mapper;

import com.periodicalsubscription.service.dto.SubscriptionDetailDto;
import com.periodicalsubscription.model.entity.SubscriptionDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PeriodicalMapper.class})
public interface SubscriptionDetailMapper {
    @Mapping(source = "subscription", target = "subscriptionDto")
    @Mapping(source = "periodical", target = "periodicalDto")
    SubscriptionDetailDto toDto(SubscriptionDetail subscriptionDetail);

    @Mapping(source = "subscriptionDto", target = "subscription")
    @Mapping(source = "periodicalDto", target = "periodical")
    SubscriptionDetail toEntity(SubscriptionDetailDto subscriptionDetailDto);
}
