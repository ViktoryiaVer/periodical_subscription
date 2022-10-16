package com.periodicalsubscription.mapper;

import com.periodicalsubscription.dto.*;
import com.periodicalsubscription.model.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ObjectMapper {
    public UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setRoleDto(UserDto.RoleDto.valueOf(user.getRole().toString()));

        return dto;
    }

    public User toEntity(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setRole(User.Role.valueOf(dto.getRoleDto().toString()));

        return user;
    }

    public SubscriptionDto toDto(Subscription subscription) {
        SubscriptionDto dto = new SubscriptionDto();
        dto.setId(subscription.getId());
        dto.setUserDto(toDto(subscription.getUser()));
        dto.setTotalCost(subscription.getTotalCost());
        dto.setStatusDto(SubscriptionDto.StatusDto.valueOf(subscription.getStatus().toString()));

        for (SubscriptionDetail detail : subscription.getSubscriptionDetails()) {
            dto.addSubscriptionDetailDto(toDto(detail));
        }
        return dto;
    }

    public Subscription toEntity(SubscriptionDto dto) {
        Subscription subscription = new Subscription();
        subscription.setId(dto.getId());
        subscription.setUser(toEntity(dto.getUserDto()));
        subscription.setTotalCost(dto.getTotalCost());
        subscription.setStatus(Subscription.Status.valueOf(dto.getStatusDto().toString()));

        for (SubscriptionDetailDto detailDto : dto.getSubscriptionDetailDtos()) {
            subscription.addSubscriptionDetail(toEntity(detailDto));
        }

        return subscription;
    }

    public SubscriptionDetailDto toDto(SubscriptionDetail detail) {
        SubscriptionDetailDto dto = new SubscriptionDetailDto();
        dto.setId(detail.getId());
        dto.setPeriodicalDto(toDto(detail.getPeriodical()));
        dto.setSubscriptionDurationInYears(detail.getSubscriptionDurationInYears());
        dto.setPeriodicalCurrentPrice(detail.getPeriodicalCurrentPrice());

        return  dto;
    }

    public SubscriptionDetail toEntity(SubscriptionDetailDto dto) {
        SubscriptionDetail detail = new SubscriptionDetail();
        detail.setId(dto.getId());
        detail.setPeriodical(toEntity(dto.getPeriodicalDto()));
        detail.setSubscriptionDurationInYears(dto.getSubscriptionDurationInYears());
        detail.setPeriodicalCurrentPrice(dto.getPeriodicalCurrentPrice());

        return detail;
    }

    public PeriodicalDto toDto (Periodical periodical) {
        PeriodicalDto dto = new PeriodicalDto();
        dto.setId(periodical.getId());
        dto.setTitle(periodical.getTitle());
        dto.setPublisher(periodical.getPublisher());
        dto.setDescription(periodical.getDescription());
        dto.setPublicationDate(periodical.getPublicationDate());
        dto.setIssuesAmountInYear(periodical.getIssuesAmountInYear());
        dto.setPrice(periodical.getPrice());
        dto.setLanguage(periodical.getLanguage());
        dto.setImagePath(periodical.getImagePath());
        dto.setTypeDto(PeriodicalDto.TypeDto.valueOf(periodical.getType().toString()));
        dto.setStatusDto(PeriodicalDto.StatusDto.valueOf(periodical.getStatus().toString()));

        for (PeriodicalCategory category : periodical.getCategories()) {
            dto.addCategoryDto(toDto(category));
        }

        return dto;
    }

    public Periodical toEntity (PeriodicalDto dto) {
        Periodical periodical = new Periodical();
        periodical.setId(dto.getId());
        periodical.setTitle(dto.getTitle());
        periodical.setPublisher(dto.getPublisher());
        periodical.setDescription(dto.getDescription());
        periodical.setPublicationDate(dto.getPublicationDate());
        periodical.setIssuesAmountInYear(dto.getIssuesAmountInYear());
        periodical.setPrice(dto.getPrice());
        periodical.setLanguage(dto.getLanguage());
        periodical.setImagePath(dto.getImagePath());
        periodical.setType(Periodical.Type.valueOf(dto.getTypeDto().toString()));
        periodical.setStatus(Periodical.Status.valueOf(dto.getStatusDto().toString()));

        for (PeriodicalCategoryDto categoryDto : dto.getCategoryDtos()) {
            periodical.addCategory(toEntity(categoryDto));
        }

        return periodical;
    }

    public PeriodicalCategoryDto toDto(PeriodicalCategory category) {
        PeriodicalCategoryDto dto = new PeriodicalCategoryDto();
        dto.setId(category.getId());
        dto.setCategoryDto(PeriodicalCategoryDto.CategoryDto.valueOf(category.getCategory().toString()));

        return dto;
    }

    public PeriodicalCategory toEntity(PeriodicalCategoryDto dto) {
        PeriodicalCategory category = new PeriodicalCategory();
        category.setId(dto.getId());
        category.setCategory(PeriodicalCategory.Category.valueOf(dto.getCategoryDto().toString()));

        return category;
    }

    public PaymentDto toDto(Payment payment) {
        PaymentDto dto = new PaymentDto();
        dto.setId(payment.getId());
        dto.setUserDto(toDto(payment.getUser()));
        dto.setSubscriptionDto(toDto(payment.getSubscription()));
        dto.setPaymentTime(payment.getPaymentTime());
        dto.setPaymentMethodDto(PaymentDto.PaymentMethodDto.valueOf(payment.getPaymentMethod().toString()));

        return dto;
    }

    public Payment toEntity(PaymentDto dto) {
        Payment payment = new Payment();
        payment.setId(dto.getId());
        payment.setUser(toEntity(dto.getUserDto()));
        payment.setSubscription(toEntity(dto.getSubscriptionDto()));
        payment.setPaymentTime(dto.getPaymentTime());
        payment.setPaymentMethod(Payment.PaymentMethod.valueOf(dto.getPaymentMethodDto().toString()));

        return payment;
    }
}
