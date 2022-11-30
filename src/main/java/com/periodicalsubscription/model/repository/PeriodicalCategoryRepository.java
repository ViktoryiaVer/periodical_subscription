package com.periodicalsubscription.model.repository;

import com.periodicalsubscription.model.entity.Periodical;
import com.periodicalsubscription.model.entity.PeriodicalCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface extending JpaRepository interface for managing PeriodicalCategory entities
 */
@Repository
public interface PeriodicalCategoryRepository extends JpaRepository<PeriodicalCategory, Long> {
    /**
     * deletes all periodical categories by periodical
     * @param periodical Periodical object
     */
    void deleteAllByPeriodical(Periodical periodical);

    /**
     * checks if periodical category exists by periodical
     * @param periodical periodical to be checked
     * @return true if exists, false otherwise
     */
    boolean existsByPeriodical(Periodical periodical);
}
