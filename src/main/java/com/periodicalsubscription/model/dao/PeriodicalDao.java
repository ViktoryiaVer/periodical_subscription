package com.periodicalsubscription.model.dao;

import com.periodicalsubscription.model.entity.Periodical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeriodicalDao extends JpaRepository<Periodical, Long>  {
}
