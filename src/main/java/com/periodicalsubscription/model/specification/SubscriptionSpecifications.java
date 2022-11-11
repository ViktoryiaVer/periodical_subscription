package com.periodicalsubscription.model.specification;

import com.periodicalsubscription.aspect.logging.annotation.LogInvocation;
import com.periodicalsubscription.model.entity.Periodical;
import com.periodicalsubscription.model.entity.Subscription;
import com.periodicalsubscription.model.entity.SubscriptionDetail;
import com.periodicalsubscription.model.entity.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;

public class SubscriptionSpecifications {
    @LogInvocation
    public static Specification<Subscription> hasIdLike(String keyword) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.concat(root.get("id"), " "), "%" + keyword + "%");
    }

    @LogInvocation
    public static Specification<Subscription> hasTotalCostLike(String keyword) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.concat(root.get("totalCost"), " "), "%" + keyword + "%");
    }

    @LogInvocation
    public static Specification<Subscription> hasUserIdLike(String keyword) {
        return (root, query, criteriaBuilder) -> {
            Join<User, Subscription> user = root.join("user");
            return criteriaBuilder.like(criteriaBuilder.concat(user.get("id"), " "), "%" + keyword + "%");
        };
    }

    @LogInvocation
    public static Specification<Subscription> hasUserEmailLike(String keyword) {
        return (root, query, criteriaBuilder) -> {
            Join<User, Subscription> user = root.join("user");
            return criteriaBuilder.like(criteriaBuilder.lower(user.get("email")), "%" + keyword.toLowerCase() + "%");
        };
    }

    @LogInvocation
    public static Specification<Subscription> hasPeriodicalIdLike(String keyword) {
        return (root, query, criteriaBuilder) -> {
            Join<SubscriptionDetail, Subscription> subscriptionDetail = root.join("subscriptionDetails");
            Join<Periodical, SubscriptionDetail> periodical = subscriptionDetail.join("periodical");
            return criteriaBuilder.like(criteriaBuilder.concat(periodical.get("id"), " "), "%" + keyword + "%");
        };
    }

    @LogInvocation
    public static Specification<Subscription> hasPeriodicalTitleLike(String keyword) {
        return (root, query, criteriaBuilder) -> {
            Join<SubscriptionDetail, Subscription> subscriptionDetail = root.join("subscriptionDetails");
            Join<Periodical, SubscriptionDetail> periodical = subscriptionDetail.join("periodical");
            return criteriaBuilder.like(criteriaBuilder.lower(periodical.get("title")), "%" + keyword.toLowerCase() + "%");
        };
    }
}
