package com.periodicalsubscription.service.api;

import com.periodicalsubscription.service.dto.PeriodicalDto;

/**
 * interface with methods for periodical category business logic
 */
public interface PeriodicalCategoryService {

    /**
     * deletes all periodical categories for a periodical
     * @param dto PeriodicalDto object for periodical categories deletion
     */
    void deleteAllCategoriesForPeriodical(PeriodicalDto dto);
}
