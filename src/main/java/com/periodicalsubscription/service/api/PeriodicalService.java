package com.periodicalsubscription.service.api;

import com.periodicalsubscription.dto.PeriodicalDto;

import java.util.List;

public interface PeriodicalService {
    List<PeriodicalDto> findAll();

    PeriodicalDto findById(Long id);

    PeriodicalDto save(PeriodicalDto dto);

    PeriodicalDto update (PeriodicalDto dto);

    void delete(Long id);

    void deleteAllCategoriesForPeriodical(PeriodicalDto dto);
}
