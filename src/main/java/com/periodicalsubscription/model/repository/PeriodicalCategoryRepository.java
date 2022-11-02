package com.periodicalsubscription.model.repository;

import com.periodicalsubscription.model.entity.Periodical;
import com.periodicalsubscription.model.entity.PeriodicalCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeriodicalCategoryRepository extends JpaRepository<PeriodicalCategory, Long> {
    void deleteAllByPeriodical(Periodical periodical);

    boolean existsByPeriodical(Periodical periodical);
}
