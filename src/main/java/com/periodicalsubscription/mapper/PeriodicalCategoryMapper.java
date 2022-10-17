package com.periodicalsubscription.mapper;

import com.periodicalsubscription.dto.PeriodicalCategoryDto;
import com.periodicalsubscription.model.entity.PeriodicalCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = PeriodicalMapper.class)
public interface PeriodicalCategoryMapper {

    @Mapping(source = "category", target = "categoryDto")
    PeriodicalCategoryDto toDto(PeriodicalCategory periodicalCategory);

    @Mapping(source = "categoryDto", target = "category")
    PeriodicalCategory toEntity(PeriodicalCategoryDto periodicalCategoryDto);
}
