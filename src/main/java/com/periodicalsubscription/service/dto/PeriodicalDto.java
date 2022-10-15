package com.periodicalsubscription.service.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class PeriodicalDto {
    private Long id;
    private String title;
    private String publisher;
    private String description;
    private LocalDate publicationDate;
    private Integer issuesAmountInYear;
    private BigDecimal price;
    private String language;
    private String imagePath;
    private TypeDto typeDto;
    private StatusDto statusDto;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<PeriodicalCategoryDto> categoryDtos = new ArrayList<>();

    public void addCategoryDto(PeriodicalCategoryDto categoryDto) {
        categoryDtos.add(categoryDto);
        categoryDto.setPeriodical(this);
    }

    public enum TypeDto {
        MAGAZINE,
        NEWSPAPER,
        JOURNAL
    }

    public enum StatusDto {
        AVAILABLE,
        UNAVAILABLE
    }

}
