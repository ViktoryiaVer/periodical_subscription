package com.periodicalsubscription.model.repository;

import com.periodicalsubscription.model.entity.Periodical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface PeriodicalRepository extends JpaRepository<Periodical, Long>, JpaSpecificationExecutor<Periodical> {
    Periodical findByTitle(String title);
}
