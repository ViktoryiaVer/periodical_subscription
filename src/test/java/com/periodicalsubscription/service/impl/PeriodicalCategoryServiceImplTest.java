package com.periodicalsubscription.service.impl;

import com.periodicalsubscription.util.TestObjectUtil;
import com.periodicalsubscription.exceptions.periodicalcategory.PeriodicalCategoryNotFoundException;
import com.periodicalsubscription.exceptions.periodicalcategory.PeriodicalCategoryServiceException;
import com.periodicalsubscription.mapper.PeriodicalCategoryMapper;
import com.periodicalsubscription.mapper.PeriodicalMapper;
import com.periodicalsubscription.model.entity.Periodical;
import com.periodicalsubscription.model.entity.PeriodicalCategory;
import com.periodicalsubscription.model.repository.PeriodicalCategoryRepository;
import com.periodicalsubscription.service.api.PeriodicalCategoryService;
import com.periodicalsubscription.service.dto.PeriodicalCategoryDto;
import com.periodicalsubscription.service.dto.PeriodicalDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PeriodicalCategoryServiceImplTest {
    @Mock
    private PeriodicalCategoryRepository periodicalCategoryRepository;
    @Mock
    private PeriodicalCategoryMapper mapper;
    @Mock
    private PeriodicalMapper periodicalMapper;
    @Mock
    private MessageSource messageSource;
    private PeriodicalCategoryService periodicalCategoryService;
    private PeriodicalCategory periodicalCategory;
    private PeriodicalCategoryDto periodicalCategoryDto;

    @BeforeEach
    public void setup() {
        periodicalCategoryService = new PeriodicalCategoryServiceImpl(periodicalCategoryRepository, mapper, periodicalMapper, messageSource);
        periodicalCategory = TestObjectUtil.getPeriodicalCategoryForTest();
        periodicalCategoryDto = TestObjectUtil.getPeriodicalCategoryDtoForTest();
    }

    @Test
    void whenFindAllPeriodicalCategories_thenReturnPeriodicalCategories() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<PeriodicalCategory> pagePeriodicalCategory = new PageImpl<>(new ArrayList<>());
        Page<PeriodicalCategoryDto> pagePeriodicalCategoryDto = new PageImpl<>(new ArrayList<>());

        when(periodicalCategoryRepository.findAll(pageable)).thenReturn(pagePeriodicalCategory);
        when(periodicalCategoryRepository.findAll(pageable).map(mapper::toDto)).thenReturn(pagePeriodicalCategoryDto);

        Page<PeriodicalCategoryDto> foundPage = periodicalCategoryService.findAll(pageable);

        assertNotNull(foundPage);
        verify(periodicalCategoryRepository, times(1)).findAll(pageable);
    }

    @Test
    void whenFindExitingPeriodicalCategoryById_thenReturnPeriodicalCategory() {
        Long periodicalCategoryId = 1L;
        when(periodicalCategoryRepository.findById(periodicalCategoryId)).thenReturn(Optional.of(periodicalCategory));
        mockMapperToDto();

        PeriodicalCategoryDto foundPeriodicalCategory = periodicalCategoryService.findById(periodicalCategoryId);

        assertEquals(periodicalCategoryDto, foundPeriodicalCategory);
        verify(periodicalCategoryRepository, times(1)).findById(periodicalCategoryId);
    }

    @Test
    void whenFindNonExitingPeriodicalCategoryById_thenThrowException() {
        Long periodicalCategoryId = 1L;
        when(periodicalCategoryRepository.findById(periodicalCategoryId)).thenReturn(Optional.empty());

        assertThrows(PeriodicalCategoryNotFoundException.class, () -> periodicalCategoryService.findById(periodicalCategoryId));
        verify(mapper, never()).toDto(any(PeriodicalCategory.class));
    }

    @Test
    void whenDeletePeriodicalCategory_thenPeriodicalCategoryIsDeleted() {
        Long periodicalCategoryId = 1L;
        doNothing().when(periodicalCategoryRepository).deleteById(periodicalCategoryId);

        periodicalCategoryService.deleteById(periodicalCategoryId);
        verify(periodicalCategoryRepository, times(1)).deleteById(periodicalCategoryId);
    }

    @Test
    void whenFailureWhileDeletingPeriodicalCategory_thenThrowException() {
        Long periodicalCategoryId = 1L;

        doNothing().when(periodicalCategoryRepository).deleteById(periodicalCategoryId);
        when(periodicalCategoryRepository.existsById(periodicalCategoryId)).thenReturn(true);

        assertThrows(PeriodicalCategoryServiceException.class, () -> periodicalCategoryService.deleteById(periodicalCategoryId));
        verify(periodicalCategoryRepository, times(1)).deleteById(periodicalCategoryId);
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

    private void mockMapperToDto() {
        when(mapper.toDto(periodicalCategory)).thenReturn(periodicalCategoryDto);
    }

}
