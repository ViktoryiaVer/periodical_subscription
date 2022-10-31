package com.periodicalsubscription.dto;

import com.periodicalsubscription.manager.ErrorMessageManager;
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
    @NotBlank(message = ErrorMessageManager.PERIODICAL_TITLE_EMPTY)
    private String title;
    @NotBlank(message = ErrorMessageManager.PERIODICAL_PUBLISHER_EMPTY)
    private String publisher;
    @NotBlank(message = ErrorMessageManager.PERIODICAL_DESCRIPTION_EMPTY)
    private String description;
    @NotNull(message = ErrorMessageManager.PERIODICAL_PUBLICATION_DATE_EMPTY)
    private LocalDate publicationDate;
    @NotNull(message = ErrorMessageManager.PERIODICAL_ISSUES_AMOUNT_EMPTY)
    @Digits(integer = 3, fraction = 0, message = ErrorMessageManager.PERIODICAL_ISSUES_AMOUNT_NOT_VALID)
    private Integer issuesAmountInYear;
    @NotNull(message = ErrorMessageManager.PERIODICAL_PRICE_EMPTY)
    @DecimalMin(value = "0.0", inclusive = false, message = ErrorMessageManager.PERIODICAL_PRICE_MIN)
    @DecimalMax(value = "10000.00", inclusive = false, message = ErrorMessageManager.PERIODICAL_PRICE_MAX)
    @Digits(integer = 4, fraction = 2, message = ErrorMessageManager.PERIODICAL_PRICE_INVALID)
    private BigDecimal price;
    @NotBlank(message = ErrorMessageManager.PERIODICAL_LANGUAGE_EMPTY)
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
