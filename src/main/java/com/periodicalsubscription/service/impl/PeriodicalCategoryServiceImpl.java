package com.periodicalsubscription.service.impl;

import com.periodicalsubscription.aspect.logging.annotation.LogInvocationService;
import com.periodicalsubscription.aspect.logging.annotation.ServiceEx;
import com.periodicalsubscription.service.dto.PeriodicalDto;
import com.periodicalsubscription.exceptions.periodicalcategory.PeriodicalCategoryServiceException;
import com.periodicalsubscription.mapper.PeriodicalMapper;
import com.periodicalsubscription.model.repository.PeriodicalCategoryRepository;
import com.periodicalsubscription.service.api.PeriodicalCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PeriodicalCategoryServiceImpl implements PeriodicalCategoryService {
    private final PeriodicalCategoryRepository periodicalCategoryRepository;
    private final PeriodicalMapper periodicalMapper;
    private final MessageSource messageSource;

    @Override
    @LogInvocationService
    @ServiceEx
    @Transactional
    public void deleteAllCategoriesForPeriodical(PeriodicalDto dto) {
        periodicalCategoryRepository.deleteAllByPeriodical(periodicalMapper.toEntity(dto));
        if (periodicalCategoryRepository.existsByPeriodical(periodicalMapper.toEntity(dto))) {
            throw new PeriodicalCategoryServiceException(messageSource.getMessage("msg.error.periodical.category.service.delete.by.periodical", null,
                    LocaleContextHolder.getLocale()));
        }
    }
}
