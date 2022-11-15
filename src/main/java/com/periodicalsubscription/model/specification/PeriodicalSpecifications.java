package com.periodicalsubscription.model.specification;

import com.periodicalsubscription.aspect.logging.annotation.LogInvocation;
import com.periodicalsubscription.model.entity.Periodical;
import com.periodicalsubscription.model.entity.PeriodicalCategory;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;

public class PeriodicalSpecifications {
    @LogInvocation
    public static Specification<Periodical> hasCategory(String category) {
        return (root, query, criteriaBuilder) -> {
            Join<PeriodicalCategory, Periodical> periodicalCategories = root.join("categories");
            return criteriaBuilder.equal(periodicalCategories.get("category"), PeriodicalCategory.Category.valueOf(category));
        };
    }

    @LogInvocation
    public static Specification<Periodical> hastType(String type) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("type"), Periodical.Type.valueOf(type));
    }

    @LogInvocation
    public static Specification<Periodical> hasIdLike(String keyword) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.concat(root.get("id"), " "), "%" + keyword + "%");
    }

    @LogInvocation
    public static Specification<Periodical> hasTitleLike(String keyword) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + keyword.toLowerCase() + "%");
    }

    @LogInvocation
    public static Specification<Periodical> hasPublisherLike(String keyword) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("publisher")), "%" + keyword.toLowerCase() + "%");
    }

    @LogInvocation
    public static Specification<Periodical> hasDescriptionLike(String keyword) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + keyword.toLowerCase() + "%");
    }

    @LogInvocation
    public static Specification<Periodical> hasPriceLike(String keyword) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.concat(root.get("price"), " "), "%" + keyword + "%");
    }

    @LogInvocation
    public static Specification<Periodical> hasLanguageLike(String keyword) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("language")), "%" + keyword.toLowerCase() + "%");
    }
}
