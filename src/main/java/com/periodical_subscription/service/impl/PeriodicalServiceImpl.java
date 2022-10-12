package com.periodical_subscription.service.impl;

import com.periodical_subscription.model.dao.PeriodicalDao;
import com.periodical_subscription.model.entity.Periodical;
import com.periodical_subscription.service.api.PeriodicalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PeriodicalServiceImpl implements PeriodicalService {
    private final PeriodicalDao periodicalDao;

    @Override
    public List<Periodical> findAll() {
        return periodicalDao.findAll();
    }

    @Override
    public Periodical findById(Long id) {
        Optional<Periodical> periodical = periodicalDao.findById(id);

        return periodical.orElseThrow(RuntimeException::new);
    }

    @Override
    public Periodical save(Periodical periodical) {
        //TODO some validation
        return periodicalDao.save(periodical);
    }

    @Override
    public Periodical update(Periodical periodical) {
        //TODO some validation
        return periodicalDao.save(periodical);
    }

    @Override
    public void delete(Periodical periodical) {
        //TODO some validation?
        periodicalDao.delete(periodical);
    }
}
