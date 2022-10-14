package com.periodicalsubscription.service.impl;

import com.periodicalsubscription.model.dao.PeriodicalCategoryRepository;
import com.periodicalsubscription.model.entity.PeriodicalCategory;
import com.periodicalsubscription.service.api.PeriodicalCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PeriodicalCategoryServiceImpl implements PeriodicalCategoryService {
    private final PeriodicalCategoryRepository periodicalCategoryRepository;

    @Override
    public List<PeriodicalCategory> findAll() {
        return periodicalCategoryRepository.findAll();
    }

    @Override
    public PeriodicalCategory findById(Long id) {
        Optional<PeriodicalCategory> category = periodicalCategoryRepository.findById(id);

        return category.orElseThrow(RuntimeException::new);
    }

    @Override
    public PeriodicalCategory save(PeriodicalCategory category) {
        //TODO some validation
        return periodicalCategoryRepository.save(category);
    }

    @Override
    public PeriodicalCategory update(PeriodicalCategory category) {
        //TODO some validation
        return periodicalCategoryRepository.save(category);
    }

    @Override
    public void delete(PeriodicalCategory category) {
        //TODO some validation
        periodicalCategoryRepository.delete(category);
    }
}
