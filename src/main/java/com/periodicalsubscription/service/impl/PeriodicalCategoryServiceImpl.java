package com.periodicalsubscription.service.impl;

import com.periodicalsubscription.aspect.logging.annotation.LogInvocationService;
import com.periodicalsubscription.aspect.logging.annotation.ServiceEx;
import com.periodicalsubscription.service.dto.PeriodicalDto;
import com.periodicalsubscription.exceptions.periodical.PeriodicalServiceException;
import com.periodicalsubscription.exceptions.periodicalcategory.PeriodicalCategoryServiceException;
import com.periodicalsubscription.mapper.PeriodicalCategoryMapper;
import com.periodicalsubscription.mapper.PeriodicalMapper;
import com.periodicalsubscription.model.repository.PeriodicalCategoryRepository;
import com.periodicalsubscription.model.entity.PeriodicalCategory;
import com.periodicalsubscription.service.api.PeriodicalCategoryService;
import com.periodicalsubscription.service.dto.PeriodicalCategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PeriodicalCategoryServiceImpl implements PeriodicalCategoryService {
    private final PeriodicalCategoryRepository periodicalCategoryRepository;
    private final PeriodicalCategoryMapper mapper;
    private final PeriodicalMapper periodicalMapper;
    private final MessageSource messageSource;

    @Override
    @LogInvocationService
    public Page<PeriodicalCategoryDto> findAll(Pageable pageable) {
        return periodicalCategoryRepository.findAll(pageable).map(mapper::toDto);
    }

    @Override
    @LogInvocationService
    @ServiceEx
    public PeriodicalCategoryDto findById(Long id) {
        PeriodicalCategory category = periodicalCategoryRepository.findById(id).orElseThrow(() -> {
            throw new PeriodicalCategoryServiceException(messageSource.getMessage("msg.error.periodical.category.find.by.id", null,
                    LocaleContextHolder.getLocale()));
        });
        return mapper.toDto(category);
    }

    @Override
    @LogInvocationService
    @ServiceEx
    @Transactional
    public void deleteById(Long id) {
        periodicalCategoryRepository.deleteById(id);
        if (periodicalCategoryRepository.existsById(id)) {
            throw new PeriodicalServiceException(messageSource.getMessage("msg.error.periodical.category.service.delete", null,
                    LocaleContextHolder.getLocale()));
        }
    }

    @Override
    @LogInvocationService
    @ServiceEx
    @Transactional
    public void deleteAllCategoriesForPeriodical(PeriodicalDto dto) {
        periodicalCategoryRepository.deleteAllByPeriodical(periodicalMapper.toEntity(dto));
        if (periodicalCategoryRepository.existsByPeriodical(periodicalMapper.toEntity(dto))) {
            throw new PeriodicalServiceException(messageSource.getMessage("msg.error.periodical.category.service.delete.by.periodical", null,
                    LocaleContextHolder.getLocale()));
        }
    }
}
