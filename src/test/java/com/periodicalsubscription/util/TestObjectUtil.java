package com.periodicalsubscription.util;

import com.periodicalsubscription.model.entity.Payment;
import com.periodicalsubscription.model.entity.Periodical;
import com.periodicalsubscription.model.entity.PeriodicalCategory;
import com.periodicalsubscription.model.entity.Subscription;
import com.periodicalsubscription.model.entity.SubscriptionDetail;
import com.periodicalsubscription.model.entity.User;
import com.periodicalsubscription.service.dto.PaymentDto;
import com.periodicalsubscription.service.dto.PeriodicalCategoryDto;
import com.periodicalsubscription.service.dto.PeriodicalDto;
import com.periodicalsubscription.service.dto.SubscriptionDetailDto;
import com.periodicalsubscription.service.dto.SubscriptionDto;
import com.periodicalsubscription.service.dto.UserDto;
import com.periodicalsubscription.util.constant.TestObjectConstant;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestObjectUtil {
    public static User getUserWithoutId() {
        User user = new User();
        user.setFirstName(TestObjectConstant.USER_FIRSTNAME);
        user.setLastName(TestObjectConstant.USER_LASTNAME);
        user.setEmail(TestObjectConstant.USER_EMAIL);
        user.setPassword(TestObjectConstant.USER_PASSWORD);
        user.setPhoneNumber(TestObjectConstant.USER_PHONE_NUMBER);
        user.setRole(User.Role.valueOf(TestObjectConstant.USER_ROLE));
        return user;
    }

    public static User getUserWithId() {
        User user = getUserWithoutId();
        user.setId(TestObjectConstant.USER_ID);
        return user;
    }

    public static UserDto getUserDtoWithoutId() {
        UserDto userDto = new UserDto();
        userDto.setFirstName(TestObjectConstant.USER_FIRSTNAME);
        userDto.setLastName(TestObjectConstant.USER_LASTNAME);
        userDto.setEmail(TestObjectConstant.USER_EMAIL);
        userDto.setPassword(TestObjectConstant.USER_PASSWORD);
        userDto.setPhoneNumber(TestObjectConstant.USER_PHONE_NUMBER);
        userDto.setRoleDto(UserDto.RoleDto.valueOf(TestObjectConstant.USER_ROLE));
        return userDto;
    }

    public static UserDto getUserDtoWithId() {
        UserDto userDto = getUserDtoWithoutId();
        userDto.setId(TestObjectConstant.USER_ID);
        return userDto;
    }

    public static Periodical getPeriodicalWithoutId() {
        Periodical periodical = new Periodical();
        periodical.setTitle(TestObjectConstant.PERIODICAL_TITLE);
        periodical.setPublisher(TestObjectConstant.PERIODICAL_PUBLISHER);
        periodical.setDescription(TestObjectConstant.PERIODICAL_DESCRIPTION);
        periodical.setPublicationDate(LocalDate.now());
        periodical.setIssuesAmountInYear(TestObjectConstant.PERIODICAL_ISSUES);
        periodical.setPrice(BigDecimal.valueOf(TestObjectConstant.PERIODICAL_PRICE));
        periodical.setLanguage(TestObjectConstant.PERIODICAL_LANGUAGE);
        periodical.setImagePath(TestObjectConstant.PERIODICAL_IMAGE);
        periodical.setType(Periodical.Type.valueOf(TestObjectConstant.PERIODICAL_TYPE));
        periodical.setStatus(Periodical.Status.valueOf(TestObjectConstant.PERIODICAL_STATUS));
        periodical.addCategory(getPeriodicalCategoryForTest());
        return periodical;
    }

    public static Periodical getPeriodicalWithId() {
        Periodical periodical = getPeriodicalWithoutId();
        periodical.setId(TestObjectConstant.PERIODICAL_ID);
        return periodical;
    }


    public static PeriodicalCategory getPeriodicalCategoryForTest() {
        PeriodicalCategory category = new PeriodicalCategory();
        category.setCategory(PeriodicalCategory.Category.valueOf(TestObjectConstant.PERIODICAL_CATEGORY));
        return category;
    }

    public static PeriodicalDto getPeriodicalDtoWithoutId() {
        PeriodicalDto periodical = new PeriodicalDto();
        periodical.setTitle(TestObjectConstant.PERIODICAL_TITLE);
        periodical.setPublisher(TestObjectConstant.PERIODICAL_PUBLISHER);
        periodical.setDescription(TestObjectConstant.PERIODICAL_DESCRIPTION);
        periodical.setPublicationDate(LocalDate.now());
        periodical.setIssuesAmountInYear(TestObjectConstant.PERIODICAL_ISSUES);
        periodical.setPrice(BigDecimal.valueOf(TestObjectConstant.PERIODICAL_PRICE));
        periodical.setLanguage(TestObjectConstant.PERIODICAL_LANGUAGE);
        periodical.setImagePath(TestObjectConstant.PERIODICAL_IMAGE);
        periodical.setTypeDto(PeriodicalDto.TypeDto.valueOf(TestObjectConstant.PERIODICAL_TYPE));
        periodical.setStatusDto(PeriodicalDto.StatusDto.valueOf(TestObjectConstant.PERIODICAL_STATUS));
        periodical.addCategoryDto(getPeriodicalCategoryDtoForTest());

        return periodical;
    }

    public static PeriodicalDto getPeriodicalDtoWithId() {
        PeriodicalDto periodical = getPeriodicalDtoWithoutId();
        periodical.setId(TestObjectConstant.PERIODICAL_ID);
        return periodical;
    }

    public static PeriodicalDto getPeriodicalDtoFromCreationForm() {
        PeriodicalDto periodical = new PeriodicalDto();
        periodical.setTitle(TestObjectConstant.PERIODICAL_TITLE);
        periodical.setPublisher(TestObjectConstant.PERIODICAL_PUBLISHER);
        periodical.setDescription(TestObjectConstant.PERIODICAL_DESCRIPTION);
        periodical.setPublicationDate(LocalDate.now());
        periodical.setIssuesAmountInYear(TestObjectConstant.PERIODICAL_ISSUES);
        periodical.setPrice(BigDecimal.valueOf(TestObjectConstant.PERIODICAL_PRICE));
        periodical.setLanguage(TestObjectConstant.PERIODICAL_LANGUAGE);
        periodical.setTypeDto(PeriodicalDto.TypeDto.valueOf(TestObjectConstant.PERIODICAL_TYPE));
        List<PeriodicalCategoryDto> categoryDtos = new ArrayList<>();
        categoryDtos.add(new PeriodicalCategoryDto());
        categoryDtos.get(0).setCategoryDto(PeriodicalCategoryDto.CategoryDto.valueOf(TestObjectConstant.PERIODICAL_CATEGORY));
        periodical.setCategoryDtos(categoryDtos);
        return periodical;
    }

    public static PeriodicalDto getPeriodicalDtoFromUpdateForm() {
        PeriodicalDto periodical = getPeriodicalDtoFromCreationForm();
        periodical.setId(TestObjectConstant.PERIODICAL_ID);
        return periodical;
    }


    public static PeriodicalCategoryDto getPeriodicalCategoryDtoForTest() {
        PeriodicalCategoryDto category = new PeriodicalCategoryDto();
        category.setCategoryDto(PeriodicalCategoryDto.CategoryDto.valueOf(TestObjectConstant.PERIODICAL_CATEGORY));
        return category;
    }

    public static Subscription getSubscriptionWithoutId() {
        Subscription subscription = new Subscription();
        subscription.setUser(getUserWithoutId());
        subscription.setTotalCost(BigDecimal.valueOf(TestObjectConstant.PERIODICAL_PRICE));
        subscription.setStatus(Subscription.Status.PENDING);

        subscription.addSubscriptionDetail(getSubscriptionDetailWithId());

        return subscription;
    }

    public static Subscription getSubscriptionWithId() {
        Subscription subscription = getSubscriptionWithoutId();
        subscription.setId(TestObjectConstant.SUBSCRIPTION_ID);
        subscription.setStatus(Subscription.Status.AWAITING_PAYMENT);
        return subscription;
    }

    public static Subscription getPayedSubscription() {
        Subscription subscription = getSubscriptionWithId();
        subscription.setStatus(Subscription.Status.PAYED);
        subscription.getSubscriptionDetails().get(0).setSubscriptionStartDate(TestObjectConstant.SUBSCRIPTION_DETAIL_START_DATE);
        subscription.getSubscriptionDetails().get(0).setSubscriptionEndDate(TestObjectConstant.SUBSCRIPTION_DETAIL_END_DATE);
        return subscription;

    }

    public static SubscriptionDetail getSubscriptionDetailWithoutId() {
        SubscriptionDetail subscriptionDetail = new SubscriptionDetail();
        subscriptionDetail.setPeriodical(getPeriodicalWithId());
        subscriptionDetail.setSubscriptionDurationInYears(TestObjectConstant.SUBSCRIPTION_DETAIL_DURATION);
        subscriptionDetail.setPeriodicalCurrentPrice(BigDecimal.valueOf(TestObjectConstant.PERIODICAL_PRICE));
        return subscriptionDetail;
    }

    public static SubscriptionDetail getSubscriptionDetailWithId() {
        SubscriptionDetail subscriptionDetail = getSubscriptionDetailWithoutId();
        subscriptionDetail.setId(TestObjectConstant.SUBSCRIPTION_DETAIL_ID);
        return subscriptionDetail;
    }

    public static SubscriptionDto getSubscriptionDtoWithoutId() {
        SubscriptionDto subscription = new SubscriptionDto();
        subscription.setUserDto(getUserDtoWithoutId());
        subscription.setTotalCost(BigDecimal.valueOf(TestObjectConstant.PERIODICAL_PRICE));
        subscription.setStatusDto(SubscriptionDto.StatusDto.PENDING);

        subscription.addSubscriptionDetailDto(getSubscriptionDetailDtoWithId());
        return subscription;
    }

    public static SubscriptionDto getSubscriptionDtoWithId() {
        SubscriptionDto subscription = getSubscriptionDtoWithoutId();
        subscription.setId(TestObjectConstant.SUBSCRIPTION_ID);
        subscription.setStatusDto(SubscriptionDto.StatusDto.AWAITING_PAYMENT);
        return subscription;
    }

    public static SubscriptionDto getPayedSubscriptionDto() {
        SubscriptionDto subscription = getSubscriptionDtoWithId();
        subscription.setStatusDto(SubscriptionDto.StatusDto.PAYED);
        subscription.getSubscriptionDetailDtos().get(0).setSubscriptionStartDate(TestObjectConstant.SUBSCRIPTION_DETAIL_START_DATE);
        subscription.getSubscriptionDetailDtos().get(0).setSubscriptionEndDate(TestObjectConstant.SUBSCRIPTION_DETAIL_END_DATE);
        return subscription;
    }

    public static SubscriptionDetailDto getSubscriptionDetailDtoWithoutId() {
        SubscriptionDetailDto subscriptionDetail = new SubscriptionDetailDto();
        subscriptionDetail.setPeriodicalDto(getPeriodicalDtoWithId());
        subscriptionDetail.setSubscriptionDurationInYears(TestObjectConstant.SUBSCRIPTION_DETAIL_DURATION);
        subscriptionDetail.setPeriodicalCurrentPrice(BigDecimal.valueOf(TestObjectConstant.PERIODICAL_PRICE));
        return subscriptionDetail;
    }

    public static SubscriptionDetailDto getSubscriptionDetailDtoWithId() {
        SubscriptionDetailDto subscriptionDetail = getSubscriptionDetailDtoWithoutId();
        subscriptionDetail.setId(TestObjectConstant.SUBSCRIPTION_DETAIL_ID);
        return subscriptionDetail;
    }

    public static Payment getPaymentWithOutId() {
        Payment payment = new Payment();
        payment.setUser(getUserWithoutId());
        payment.setPaymentTime(TestObjectConstant.PAYMENT_DATETIME);
        payment.setPaymentMethod(Payment.PaymentMethod.valueOf(TestObjectConstant.PAYMENT_METHOD));
        payment.setSubscription(getSubscriptionWithId());

        return payment;
    }

    public static PaymentDto getPaymentDtoWithoutId() {
        PaymentDto payment = new PaymentDto();
        payment.setUserDto(getUserDtoWithoutId());
        payment.setPaymentTime(TestObjectConstant.PAYMENT_DATETIME);
        payment.setPaymentMethodDto(PaymentDto.PaymentMethodDto.valueOf(TestObjectConstant.PAYMENT_METHOD));
        payment.setSubscriptionDto(getSubscriptionDtoWithId());

        return payment;
    }

    public static PaymentDto getPaymentDtoWithId() {
        PaymentDto payment = getPaymentDtoWithoutId();
        payment.setId(TestObjectConstant.PAYMENT_ID);
        return payment;
    }

    public static Map<Long, Integer> getCartWithOneItem() {
        Long periodicalId = TestObjectConstant.PERIODICAL_ID;
        Integer subscriptionDurationInYears = TestObjectConstant.SUBSCRIPTION_DETAIL_DURATION;
        Map<Long, Integer> cartWithOneItem = new HashMap<>();
        cartWithOneItem.put(periodicalId, subscriptionDurationInYears);
        return cartWithOneItem;
    }

    public static Map<Long, Integer> getCartWithTwoItems() {
        Long secondPeriodicalId = TestObjectConstant.PERIODICAL_ID_SECOND;
        Integer subscriptionDurationInYears = TestObjectConstant.SUBSCRIPTION_DETAIL_DURATION;
        Map<Long, Integer> cartWithTwoItems = getCartWithOneItem();
        cartWithTwoItems.put(secondPeriodicalId, subscriptionDurationInYears);
        return cartWithTwoItems;
    }
}
