package com.periodicalsubscription.converter;

import com.periodicalsubscription.service.dto.PeriodicalCategoryDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class StringToPeriodicalCategoryDtoConverter implements Converter<String, PeriodicalCategoryDto> {

    @Override
    public PeriodicalCategoryDto convert(@NonNull String source) {
        PeriodicalCategoryDto categoryDto = new PeriodicalCategoryDto();
        categoryDto.setCategoryDto(PeriodicalCategoryDto.CategoryDto.valueOf(source));
        return categoryDto;
    }
}
