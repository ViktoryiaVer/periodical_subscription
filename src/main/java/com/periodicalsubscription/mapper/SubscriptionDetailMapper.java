package com.periodicalsubscription.mapper;

import com.periodicalsubscription.service.dto.SubscriptionDetailDto;
import com.periodicalsubscription.model.entity.SubscriptionDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Interface for mapping between SubscriptionDetail and SubscriptionDetailDto
 */
@Mapper(componentModel = "spring", uses = {PeriodicalMapper.class})
public interface SubscriptionDetailMapper {

    /**
     * converts SubscriptionDetail to SubscriptionDetailDto
     * @param subscriptionDetail SubscriptionDetail object to be converted
     * @return SubscriptionDetailDto object
     */
    @Mapping(source = "subscription", target = "subscriptionDto")
    @Mapping(source = "periodical", target = "periodicalDto")
    SubscriptionDetailDto toDto(SubscriptionDetail subscriptionDetail);

    /**
     * converts SubscriptionDetailDto to SubscriptionDetail
     * @param subscriptionDetailDto SubscriptionDetailDto object to be converted
     * @return SubscriptionDetail object
     */
    @Mapping(source = "subscriptionDto", target = "subscription")
    @Mapping(source = "periodicalDto", target = "periodical")
    SubscriptionDetail toEntity(SubscriptionDetailDto subscriptionDetailDto);
}
