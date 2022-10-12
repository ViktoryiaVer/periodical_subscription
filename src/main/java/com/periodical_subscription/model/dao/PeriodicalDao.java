package com.periodical_subscription.model.dao;

import com.periodical_subscription.model.entity.Periodical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeriodicalDao extends JpaRepository<Periodical, Long>  {
}
