package com.periodicalsubscription.service.impl;

import com.periodicalsubscription.mapper.PeriodicalMapper;
import com.periodicalsubscription.model.repository.PeriodicalCategoryRepository;
import com.periodicalsubscription.model.repository.PeriodicalRepository;
import com.periodicalsubscription.model.entity.Periodical;
import com.periodicalsubscription.service.api.PeriodicalService;
import com.periodicalsubscription.dto.PeriodicalDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PeriodicalServiceImpl implements PeriodicalService {
    private final PeriodicalRepository periodicalRepository;
    private final PeriodicalCategoryRepository categoryRepository;
    private final PeriodicalMapper mapper;

    @Override
    public List<PeriodicalDto> findAll() {
        return periodicalRepository.findAllDistinctFetchCategories().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PeriodicalDto findById(Long id) {
        Periodical periodical = periodicalRepository.findById(id).orElseThrow(RuntimeException::new);

        return mapper.toDto(periodical);
    }

    @Override
    public PeriodicalDto save(PeriodicalDto dto) {
        //TODO some validation
        return mapper.toDto(periodicalRepository.save(mapper.toEntity(dto)));
    }

    @Override
    public PeriodicalDto update(PeriodicalDto dto) {
        //TODO some validation
        return mapper.toDto(periodicalRepository.save(mapper.toEntity(dto)));
    }

    @Override
    public void delete(Long id) {
        //TODO some validation?
        periodicalRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteAllCategoriesForPeriodical(PeriodicalDto dto) {
        categoryRepository.deleteAllByPeriodical(mapper.toEntity(dto));
    }
}
