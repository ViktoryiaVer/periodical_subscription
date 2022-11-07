package com.periodicalsubscription.service.api;

import com.periodicalsubscription.service.dto.PeriodicalCategoryDto;
import com.periodicalsubscription.service.dto.PeriodicalDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PeriodicalCategoryService {
    Page<PeriodicalCategoryDto> findAll(Pageable pageable);

    PeriodicalCategoryDto findById(Long id);

    void deleteById(Long id);

    void deleteAllCategoriesForPeriodical(PeriodicalDto dto);
}
