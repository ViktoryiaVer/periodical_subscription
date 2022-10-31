package com.periodicalsubscription.service.api;

import com.periodicalsubscription.dto.PeriodicalCategoryDto;
import com.periodicalsubscription.dto.PeriodicalDto;

import java.util.List;

public interface PeriodicalCategoryService {
    List<PeriodicalCategoryDto> findAll();

    PeriodicalCategoryDto findById(Long id);

    PeriodicalCategoryDto save(PeriodicalCategoryDto dto);

    PeriodicalCategoryDto update(PeriodicalCategoryDto dto);

    void deleteById(Long id);

    void deleteAllCategoriesForPeriodical(PeriodicalDto dto);
}
