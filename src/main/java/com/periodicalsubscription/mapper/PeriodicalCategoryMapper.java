package com.periodicalsubscription.mapper;

import com.periodicalsubscription.service.dto.PeriodicalCategoryDto;
import com.periodicalsubscription.model.entity.PeriodicalCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Interface for mapping between PeriodicalCategory and PeriodicalCategoryDto
 */
@Mapper(componentModel = "spring", uses = PeriodicalMapper.class)
public interface PeriodicalCategoryMapper {

    /**
     * converts PeriodicalCategory to PeriodicalCategoryDto
     * @param periodicalCategory PeriodicalCategory object to be converted
     * @return PeriodicalCategoryDto object
     */
    @Mapping(source = "category", target = "categoryDto")
    PeriodicalCategoryDto toDto(PeriodicalCategory periodicalCategory);

    /**
     * converts PeriodicalCategoryDto to PeriodicalCategory
     * @param periodicalCategoryDto PeriodicalCategoryDto object to be converted
     * @return PeriodicalCategory object
     */
    @Mapping(source = "categoryDto", target = "category")
    PeriodicalCategory toEntity(PeriodicalCategoryDto periodicalCategoryDto);
}
