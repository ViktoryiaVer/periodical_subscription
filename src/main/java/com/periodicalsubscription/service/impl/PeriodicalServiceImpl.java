package com.periodicalsubscription.service.impl;

import com.periodicalsubscription.model.dao.PeriodicalRepository;
import com.periodicalsubscription.model.entity.Periodical;
import com.periodicalsubscription.service.api.PeriodicalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PeriodicalServiceImpl implements PeriodicalService {
    private final PeriodicalRepository periodicalRepository;

    @Override
    public List<Periodical> findAll() {
        return periodicalRepository.findAll();
    }

    @Override
    public Periodical findById(Long id) {
        Optional<Periodical> periodical = periodicalRepository.findById(id);

        return periodical.orElseThrow(RuntimeException::new);
    }

    @Override
    public Periodical save(Periodical periodical) {
        //TODO some validation
        return periodicalRepository.save(periodical);
    }

    @Override
    public Periodical update(Periodical periodical) {
        //TODO some validation
        return periodicalRepository.save(periodical);
    }

    @Override
    public void delete(Periodical periodical) {
        //TODO some validation?
        periodicalRepository.delete(periodical);
    }
}
