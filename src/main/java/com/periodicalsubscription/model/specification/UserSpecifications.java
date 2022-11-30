package com.periodicalsubscription.model.specification;

import com.periodicalsubscription.aspect.logging.annotation.LogInvocation;
import com.periodicalsubscription.model.entity.User;
import org.springframework.data.jpa.domain.Specification;

/**
 * class with implementations of Specification interface for searching and filtering of users
 */
public class UserSpecifications {
    @LogInvocation
    public static Specification<User> hasIdLike(String keyword) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.concat(root.get("id"), " "), "%" + keyword + "%");
    }

    @LogInvocation
    public static Specification<User> hasFirstNameLike(String keyword) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), "%" + keyword.toLowerCase() + "%");
    }

    @LogInvocation
    public static Specification<User> hasLastNameLike(String keyword) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%" + keyword.toLowerCase() + "%");
    }

    @LogInvocation
    public static Specification<User> hasEmailLike(String keyword) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + keyword.toLowerCase() + "%");
    }

    @LogInvocation
    public static Specification<User> hasPhoneNumberLike(String keyword) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("phoneNumber")), "%" + keyword.toLowerCase() + "%");
    }
}
