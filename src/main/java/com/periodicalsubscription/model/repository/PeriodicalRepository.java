package com.periodicalsubscription.model.repository;

import com.periodicalsubscription.model.entity.Periodical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeriodicalRepository extends JpaRepository<Periodical, Long>  {
    @Query("select distinct p from Periodical p join fetch p.categories")
    List<Periodical> findAllDistinctFetchCategories();
}
