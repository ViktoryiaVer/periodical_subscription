package com.periodicalsubscription.service.impl;

import com.periodicalsubscription.util.TestObjectUtil;
import com.periodicalsubscription.exceptions.periodicalcategory.PeriodicalCategoryServiceException;
import com.periodicalsubscription.mapper.PeriodicalMapper;
import com.periodicalsubscription.model.entity.Periodical;
import com.periodicalsubscription.model.repository.PeriodicalCategoryRepository;
import com.periodicalsubscription.service.api.PeriodicalCategoryService;
import com.periodicalsubscription.service.dto.PeriodicalDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PeriodicalCategoryServiceImplTest {
    @Mock
    private PeriodicalCategoryRepository periodicalCategoryRepository;
    @Mock
    private PeriodicalMapper periodicalMapper;
    @Mock
    private MessageSource messageSource;
    private PeriodicalCategoryService periodicalCategoryService;

    @BeforeEach
    public void setup() {
        periodicalCategoryService = new PeriodicalCategoryServiceImpl(periodicalCategoryRepository, periodicalMapper, messageSource);
    }

    @Test
    void whenDeleteAllCategoriesByPeriodical_thenAllCategoriesAreDeleted() {
        Periodical periodical = TestObjectUtil.getPeriodicalWithoutId();
        PeriodicalDto periodicalDto = TestObjectUtil.getPeriodicalDtoWithoutId();
        when(periodicalMapper.toEntity(periodicalDto)).thenReturn(periodical);
        doNothing().when(periodicalCategoryRepository).deleteAllByPeriodical(periodical);
        when(periodicalCategoryRepository.existsByPeriodical(periodical)).thenReturn(false);

        periodicalCategoryService.deleteAllCategoriesForPeriodical(periodicalDto);
        verify(periodicalCategoryRepository, times(1)).deleteAllByPeriodical(any(Periodical.class));
    }

    @Test
    void whenFailureWhileDeletingAllCategoriesByPeriodical_thenThrowException() {
        Periodical periodical = TestObjectUtil.getPeriodicalWithoutId();
        PeriodicalDto periodicalDto = TestObjectUtil.getPeriodicalDtoWithoutId();
        when(periodicalMapper.toEntity(periodicalDto)).thenReturn(periodical);
        doNothing().when(periodicalCategoryRepository).deleteAllByPeriodical(periodical);
        when(periodicalCategoryRepository.existsByPeriodical(periodical)).thenReturn(true);

        assertThrows(PeriodicalCategoryServiceException.class, () -> periodicalCategoryService.deleteAllCategoriesForPeriodical(periodicalDto));
        verify(periodicalCategoryRepository, times(1)).deleteAllByPeriodical(any(Periodical.class));
    }

}
