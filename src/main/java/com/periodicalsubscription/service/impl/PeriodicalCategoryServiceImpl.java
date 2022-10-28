package com.periodicalsubscription.service.impl;

import com.periodicalsubscription.dto.PeriodicalDto;
import com.periodicalsubscription.mapper.PeriodicalCategoryMapper;
import com.periodicalsubscription.mapper.PeriodicalMapper;
import com.periodicalsubscription.model.repository.PeriodicalCategoryRepository;
import com.periodicalsubscription.model.entity.PeriodicalCategory;
import com.periodicalsubscription.service.api.PeriodicalCategoryService;
import com.periodicalsubscription.dto.PeriodicalCategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PeriodicalCategoryServiceImpl implements PeriodicalCategoryService {
    private final PeriodicalCategoryRepository periodicalCategoryRepository;
    private final PeriodicalCategoryMapper mapper;
    private final PeriodicalMapper periodicalMapper;

    @Override
    public List<PeriodicalCategoryDto> findAll() {
        return periodicalCategoryRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PeriodicalCategoryDto findById(Long id) {
        PeriodicalCategory category = periodicalCategoryRepository.findById(id).orElseThrow(RuntimeException::new);

        return mapper.toDto(category);
    }

    @Override
    public PeriodicalCategoryDto save(PeriodicalCategoryDto dto) {
        //TODO some validation
        return mapper.toDto(periodicalCategoryRepository.save(mapper.toEntity(dto)));
    }

    @Override
    public PeriodicalCategoryDto update(PeriodicalCategoryDto dto) {
        //TODO some validation
        return mapper.toDto(periodicalCategoryRepository.save(mapper.toEntity(dto)));
    }

    @Override
    public void delete(PeriodicalCategoryDto dto) {
        //TODO some validation
        periodicalCategoryRepository.delete(mapper.toEntity(dto));
    }

    @Transactional
    @Override
    public void deleteAllCategoriesForPeriodical(PeriodicalDto dto) {
        periodicalCategoryRepository.deleteAllByPeriodical(periodicalMapper.toEntity(dto));
    }
}
