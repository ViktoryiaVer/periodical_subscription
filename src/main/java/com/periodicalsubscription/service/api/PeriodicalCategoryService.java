package com.periodicalsubscription.service.api;

import com.periodicalsubscription.model.entity.PeriodicalCategory;

import java.util.List;

public interface PeriodicalCategoryService {
    List<PeriodicalCategory> findAll();

    PeriodicalCategory findById(Long id);

    PeriodicalCategory save(PeriodicalCategory category);

    PeriodicalCategory update(PeriodicalCategory category);

    void delete(PeriodicalCategory category);
}
