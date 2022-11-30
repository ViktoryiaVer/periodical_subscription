package com.periodicalsubscription.model.repository;

import com.periodicalsubscription.constant.HqlConstant;
import com.periodicalsubscription.model.entity.Periodical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interface extending JpaRepository and JpaSpecificationExecutor interfaces for managing Periodical entities
 */
@Repository
public interface PeriodicalRepository extends JpaRepository<Periodical, Long>, JpaSpecificationExecutor<Periodical> {
    /**
     * finds periodical by title
     * @param title title to be searched by
     * @return Optional object parametrized with Periodical
     */
    Optional<Periodical> findByTitle(String title);

    /**
     * checks if periodical exists by title
     * @param title title to be searched by
     * @return true if periodical exist by title, false otherwise
     */
    boolean existsByTitle(String title);

    /**
     * updates periodical status
     * @param status status to be set
     * @param id id of the periodical to be updated
     */
    @Modifying
    @Query(HqlConstant.HQL_UPDATE_PERIODICAL_STATUS)
    void updatePeriodicalStatus(Periodical.Status status, Long id);

    /**
     * checks if periodical exists by status and id
     * @param status status to be checked
     * @param id id to be checked
     * @return true if exists, false otherwised
     */
    boolean existsPeriodicalByStatusEqualsAndId(Periodical.Status status, Long id);
}
