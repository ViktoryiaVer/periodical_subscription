package com.periodicalsubscription.model.repository;

import com.periodicalsubscription.model.entity.Periodical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PeriodicalRepository extends JpaRepository<Periodical, Long>, JpaSpecificationExecutor<Periodical> {
    Optional<Periodical> findByTitle(String title);

    boolean existsByTitle(String title);
}
