package com.periodicalsubscription.model.repository;

import com.periodicalsubscription.constant.HqlConstant;
import com.periodicalsubscription.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interface extending JpaRepository and JpaSpecificationExecutor interfaces for managing User entities
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    /**
     * finds user by username
     * @param username username to be searched by
     * @return Optional object parametrized with User
     */
    Optional<User> findByUsername(String username);

    /**
     * finds user by email
     * @param email email to be searched by
     * @return Optional object parametrized with User
     */
    Optional<User> findByEmail(String email);

    /**
     * checks if user exists by email
     * @param email email to be checked
     * @return true if user exists, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * checks if user exists by username
     * @param username username to be checked
     * @return true if user exists, false otherwise
     */
    boolean existsByUsername(String username);

    /**
     * gets password of user
     * @param id user id
     * @return password of a user
     */
    @Query(HqlConstant.HQL_GET_USER_PASSWORD)
    String getPasswordByUserId(Long id);
}
