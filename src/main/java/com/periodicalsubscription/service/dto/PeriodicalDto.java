package com.periodicalsubscription.service.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class PeriodicalDto {
    private Long id;
    @NotBlank(message = "{msg.validation.periodical.title.empty}")
    private String title;
    @NotBlank(message = "{msg.validation.periodical.publisher.empty}")
    private String publisher;
    @NotBlank(message = "{msg.validation.periodical.description.empty}")
    private String description;
    @NotNull(message = "{msg.validation.periodical.publication.date.empty}")
    private LocalDate publicationDate;
    @NotNull(message = "{msg.validation.periodical.issues.amount.empty}")
    @Digits(integer = 3, fraction = 0, message = "{msg.validation.periodical.issues.amount.not.valid}")
    private Integer issuesAmountInYear;
    @NotNull(message = "{msg.validation.periodical.price.empty}")
    @DecimalMin(value = "0.0", inclusive = false, message = "{msg.validation.periodical.price.min}")
    @DecimalMax(value = "10000.00", inclusive = false, message = "{msg.validation.periodical.price.max}")
    @Digits(integer = 4, fraction = 2, message = "{msg.validation.periodical.price.not.valid}")
    private BigDecimal price;
    @NotBlank(message = "{msg.validation.periodical.language.empty}")
    private String language;
    private String imagePath;
    private TypeDto typeDto;
    private StatusDto statusDto;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<PeriodicalCategoryDto> categoryDtos = new ArrayList<>();

    public void addCategoryDto(PeriodicalCategoryDto categoryDto) {
        categoryDtos.add(categoryDto);
        categoryDto.setPeriodicalDto(this);
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
