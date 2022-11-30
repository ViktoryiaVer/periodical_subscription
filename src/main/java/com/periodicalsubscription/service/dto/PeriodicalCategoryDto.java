package com.periodicalsubscription.service.dto;

import lombok.Data;

/**
 * Class describing dto object for periodical category entity
 */
@Data
public class PeriodicalCategoryDto {
    private Long id;
    private PeriodicalDto periodicalDto;
    private CategoryDto categoryDto;

    public enum CategoryDto {
        ART_AND_ARCHITECTURE,
        SCIENCE,
        BUSINESS_AND_FINANCE,
        NEWS_AND_POLITICS,
        CULTURE_AND_LITERATURE,
        TRAVEL_AND_OUTDOOR
    }
}
