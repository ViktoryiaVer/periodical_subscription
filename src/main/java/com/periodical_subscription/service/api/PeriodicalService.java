package com.periodical_subscription.service.api;

import com.periodical_subscription.model.entity.Periodical;

import java.util.List;

public interface PeriodicalService {
    List<Periodical> findAll();

    Periodical findById(Long id);

    Periodical save(Periodical periodical);

    Periodical update (Periodical periodical);

    void delete(Periodical periodical);
}
