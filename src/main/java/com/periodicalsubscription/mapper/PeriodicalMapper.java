package com.periodicalsubscription.mapper;

import com.periodicalsubscription.service.dto.PeriodicalDto;
import com.periodicalsubscription.model.entity.Periodical;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Interface for mapping between Periodical and PeriodicalDto
 */
@Mapper(componentModel = "spring", uses = PeriodicalCategoryMapper.class, collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface PeriodicalMapper {
    /**
     * converts Periodical to PeriodicalDto
     * @param periodical Periodical object to be converted
     * @return PeriodicalDto object
     */
    @Mapping(source = "periodical.type", target = "typeDto")
    @Mapping(source = "periodical.status", target = "statusDto")
    @Mapping(source = "periodical.categories", target = "categoryDtos")
    PeriodicalDto toDto(Periodical periodical);

    /**
     * converts PeriodicalDto to Periodical
     * @param periodicalDto PeriodicalDto object to be converted
     * @return Periodical object
     */
    @Mapping(source = "periodicalDto.typeDto", target = "type")
    @Mapping(source = "periodicalDto.statusDto", target = "status")
    @Mapping(source = "periodicalDto.categoryDtos", target = "categories")
    Periodical toEntity(PeriodicalDto periodicalDto);

}
