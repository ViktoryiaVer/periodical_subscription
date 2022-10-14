package com.periodicalsubscription.model.dao;

import com.periodicalsubscription.model.entity.PeriodicalCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeriodicalCategoryRepository extends JpaRepository<PeriodicalCategory, Long> {
}
