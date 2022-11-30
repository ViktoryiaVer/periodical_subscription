package com.periodicalsubscription.service.dto.filter;

import lombok.Data;

/**
 * Class describing dto object for filtering periodicals
 */
@Data
public class PeriodicalFilterDto {
    private String category;
    private String type;
}


