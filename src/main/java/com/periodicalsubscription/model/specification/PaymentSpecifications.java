package com.periodicalsubscription.model.specification;

import com.periodicalsubscription.aspect.logging.annotation.LogInvocation;
import com.periodicalsubscription.model.entity.Payment;
import com.periodicalsubscription.model.entity.Subscription;
import com.periodicalsubscription.model.entity.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import java.time.LocalDate;

public class PaymentSpecifications {
    @LogInvocation
    public static Specification<Payment> hasPaymentMethod(String paymentMethod) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("paymentMethod"), Payment.PaymentMethod.valueOf(paymentMethod));
    }

    @LogInvocation
    public static Specification<Payment> hasPaymentDate(String date) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("paymentTime"), LocalDate.parse(date).atStartOfDay(), LocalDate.parse(date).plusDays(1).atStartOfDay().minusMinutes(1));
    }

    @LogInvocation
    public static Specification<Payment> hasIdLike(String keyword) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.concat(root.get("id"), " "), "%" + keyword + "%");
    }

    @LogInvocation
    public static Specification<Payment> hasSubscriptionIdLike(String keyword) {
        return (root, query, criteriaBuilder) -> {
            Join<Subscription, Payment> subscription = root.join("subscription");
            return criteriaBuilder.like(criteriaBuilder.concat(subscription.get("id"), " "), "%" + keyword + "%");
        };
    }

    @LogInvocation
    public static Specification<Payment> hasUserIdLike(String keyword) {
        return (root, query, criteriaBuilder) -> {
            Join<User, Payment> user = root.join("user");
            return criteriaBuilder.like(criteriaBuilder.concat(user.get("id"), " "), "%" + keyword + "%");
        };
    }

    @LogInvocation
    public static Specification<Payment> hasUserEmailLike(String keyword) {
        return (root, query, criteriaBuilder) -> {
            Join<User, Payment> user = root.join("user");
            return criteriaBuilder.like(criteriaBuilder.lower(user.get("email")), "%" + keyword.toLowerCase() + "%");
        };
    }
}
