package com.periodicalsubscription.service.impl;

import com.periodicalsubscription.dto.PeriodicalDto;
import com.periodicalsubscription.exceptions.periodical.PeriodicalServiceException;
import com.periodicalsubscription.exceptions.periodicalcategory.PeriodicalCategoryServiceException;
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
        PeriodicalCategory category = periodicalCategoryRepository.findById(id).orElseThrow(() -> {
            throw new PeriodicalCategoryServiceException("Periodical category with id " + id + " could not be found.");
        });
        return mapper.toDto(category);
    }

    @Override
    public PeriodicalCategoryDto save(PeriodicalCategoryDto dto) {
        PeriodicalCategoryDto savedPeriodicalCategory = mapper.toDto(periodicalCategoryRepository.save(mapper.toEntity(dto)));
        if(savedPeriodicalCategory == null) {
            throw new PeriodicalCategoryServiceException("Error while saving periodical category.");
        }
        return savedPeriodicalCategory;
    }

    @Override
    public PeriodicalCategoryDto update(PeriodicalCategoryDto dto) {
        PeriodicalCategoryDto updatedPeriodicalCategory = mapper.toDto(periodicalCategoryRepository.save(mapper.toEntity(dto)));
        if(updatedPeriodicalCategory == null) {
            throw new PeriodicalCategoryServiceException("Error while updating periodical category with id " + dto.getId() + ".");
        }
        return updatedPeriodicalCategory;
    }

    @Override
    public void deleteById(Long id) {
        periodicalCategoryRepository.deleteById(id);
        if(periodicalCategoryRepository.existsById(id)) {
            throw new PeriodicalServiceException("Error while deleting periodical category with id " + id + ".");
        }
    }

    @Transactional
    @Override
    public void deleteAllCategoriesForPeriodical(PeriodicalDto dto) {
        periodicalCategoryRepository.deleteAllByPeriodical(periodicalMapper.toEntity(dto));
        if(periodicalCategoryRepository.existsByPeriodical(periodicalMapper.toEntity(dto))) {
            throw new PeriodicalServiceException("Error while deleting periodical categories by periodical " + dto.getTitle() + ".");
        }
    }
}
