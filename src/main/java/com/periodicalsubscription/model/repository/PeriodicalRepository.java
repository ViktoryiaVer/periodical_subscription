package com.periodicalsubscription.model.repository;

import com.periodicalsubscription.constant.HqlConstant;
import com.periodicalsubscription.model.entity.Periodical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PeriodicalRepository extends JpaRepository<Periodical, Long>, JpaSpecificationExecutor<Periodical> {
    Optional<Periodical> findByTitle(String title);

    boolean existsByTitle(String title);

    @Modifying
    @Query(HqlConstant.HQL_UPDATE_PERIODICAL_STATUS)
    void updatePeriodicalStatus(Periodical.Status status, Long id);

    boolean existsPeriodicalByStatusEqualsAndId(Periodical.Status status, Long id);
}
