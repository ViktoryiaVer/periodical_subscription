package com.periodicalsubscription.service.api;

import com.periodicalsubscription.dto.PeriodicalCategoryDto;

import java.util.List;

public interface PeriodicalCategoryService {
    List<PeriodicalCategoryDto> findAll();

    PeriodicalCategoryDto findById(Long id);

    PeriodicalCategoryDto save(PeriodicalCategoryDto dto);

    PeriodicalCategoryDto update(PeriodicalCategoryDto dto);

    void delete(PeriodicalCategoryDto dto);
}
