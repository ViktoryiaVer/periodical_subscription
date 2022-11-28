package com.periodicalsubscription.model.repository;

import com.periodicalsubscription.constant.HqlConstant;
import com.periodicalsubscription.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    @Query(HqlConstant.HQL_GET_USER_PASSWORD)
    String getPasswordByUserId(Long id);
}
