package com.periodicalsubscription;

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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestObjectUtil {
    public static User getUserWithoutId() {
        User user = new User();
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setEmail("Test@mail.ru");
        user.setPassword("TestTest1234!");
        user.setPhoneNumber("+375333333333");
        user.setRole(User.Role.READER);
        return user;
    }

    public static User getUserWithId() {
        User user = getUserWithoutId();
        user.setId(1L);
        return user;
    }

    public static UserDto getUserDtoWithoutId() {
        UserDto userDto = new UserDto();
        userDto.setFirstName("Test");
        userDto.setLastName("Test");
        userDto.setEmail("Test@mail.ru");
        userDto.setPassword("TestTest1234!");
        userDto.setPhoneNumber("+375333333333");
        userDto.setRoleDto(UserDto.RoleDto.READER);
        return userDto;
    }

    public static UserDto getUserDtoWithId() {
        UserDto userDto = getUserDtoWithoutId();
        userDto.setId(1L);
        return userDto;
    }

    public static Periodical getPeriodicalWithoutId() {
        Periodical periodical = new Periodical();
        periodical.setTitle("Test periodical");
        periodical.setPublisher("Test publisher");
        periodical.setDescription("Test periodical description");
        periodical.setPublicationDate(LocalDate.now());
        periodical.setIssuesAmountInYear(51);
        periodical.setPrice(BigDecimal.valueOf(123.12));
        periodical.setLanguage("English");
        periodical.setImagePath("test_periodical.jpg");
        periodical.setType(Periodical.Type.MAGAZINE);
        periodical.setStatus(Periodical.Status.AVAILABLE);
        periodical.addCategory(getPeriodicalCategoryForTest());
        return periodical;
    }

    public static Periodical getPeriodicalWithId() {
        Periodical periodical = getPeriodicalWithoutId();
        periodical.setId(1L);
        return periodical;
    }


    public static PeriodicalCategory getPeriodicalCategoryForTest() {
        PeriodicalCategory category = new PeriodicalCategory();
        category.setCategory(PeriodicalCategory.Category.SCIENCE);
        return category;
    }

    public static PeriodicalDto getPeriodicalDtoWithoutId() {
        PeriodicalDto periodical = new PeriodicalDto();
        periodical.setTitle("Test periodical");
        periodical.setPublisher("Test publisher");
        periodical.setDescription("Test periodical description");
        periodical.setPublicationDate(LocalDate.now());
        periodical.setIssuesAmountInYear(51);
        periodical.setPrice(BigDecimal.valueOf(123.12));
        periodical.setLanguage("English");
        periodical.setImagePath("test_periodical.jpg");
        periodical.setTypeDto(PeriodicalDto.TypeDto.MAGAZINE);
        periodical.setStatusDto(PeriodicalDto.StatusDto.AVAILABLE);
        periodical.addCategoryDto(getPeriodicalCategoryDtoForTest());

        return periodical;
    }

    public static PeriodicalDto getPeriodicalDtoWithId() {
        PeriodicalDto periodical = getPeriodicalDtoWithoutId();
        periodical.setId(1L);
        return periodical;
    }

    public static PeriodicalDto getPeriodicalDtoFromCreationForm() {
        PeriodicalDto periodical = new PeriodicalDto();
        periodical.setTitle("Test periodical");
        periodical.setPublisher("Test publisher");
        periodical.setDescription("Test periodical description");
        periodical.setPublicationDate(LocalDate.now());
        periodical.setIssuesAmountInYear(51);
        periodical.setPrice(BigDecimal.valueOf(123.12));
        periodical.setLanguage("English");
        periodical.setTypeDto(PeriodicalDto.TypeDto.MAGAZINE);
        List<PeriodicalCategoryDto> categoryDtos = new ArrayList<>();
        categoryDtos.add(new PeriodicalCategoryDto());
        categoryDtos.get(0).setCategoryDto(PeriodicalCategoryDto.CategoryDto.SCIENCE);
        periodical.setCategoryDtos(categoryDtos);
        return periodical;
    }

    public static PeriodicalDto getPeriodicalDtoFromUpdateForm() {
        PeriodicalDto periodical = getPeriodicalDtoFromCreationForm();
        periodical.setId(1L);
        return periodical;
    }


    public static PeriodicalCategoryDto getPeriodicalCategoryDtoForTest() {
        PeriodicalCategoryDto category = new PeriodicalCategoryDto();
        category.setCategoryDto(PeriodicalCategoryDto.CategoryDto.SCIENCE);
        return category;
    }

    public static Subscription getSubscriptionWithoutId() {
        Subscription subscription = new Subscription();
        subscription.setUser(getUserWithoutId());
        subscription.setTotalCost(BigDecimal.valueOf(123.12));
        subscription.setStatus(Subscription.Status.PENDING);

        subscription.addSubscriptionDetail(getSubscriptionDetailWithId());

        return subscription;
    }

    public static Subscription getSubscriptionWithId() {
        Subscription subscription = getSubscriptionWithoutId();
        subscription.setId(1L);
        subscription.setStatus(Subscription.Status.AWAITING_PAYMENT);
        return subscription;
    }

    public static Subscription getPayedSubscription() {
        Subscription subscription = getSubscriptionWithId();
        subscription.setStatus(Subscription.Status.PAYED);
        subscription.getSubscriptionDetails().get(0).setSubscriptionStartDate(LocalDate.of(2020, 1, 1));
        subscription.getSubscriptionDetails().get(0).setSubscriptionEndDate(LocalDate.of(2020, 12, 31));
        return subscription;

    }

    public static SubscriptionDetail getSubscriptionDetailWithoutId() {
        SubscriptionDetail subscriptionDetail = new SubscriptionDetail();
        subscriptionDetail.setPeriodical(getPeriodicalWithId());
        subscriptionDetail.setSubscriptionDurationInYears(1);
        subscriptionDetail.setPeriodicalCurrentPrice(BigDecimal.valueOf(123.12));
        return subscriptionDetail;
    }

    public static SubscriptionDetail getSubscriptionDetailWithId() {
        SubscriptionDetail subscriptionDetail = getSubscriptionDetailWithoutId();
        subscriptionDetail.setId(1L);
        return subscriptionDetail;
    }

    public static SubscriptionDto getSubscriptionDtoWithoutId() {
        SubscriptionDto subscription = new SubscriptionDto();
        subscription.setUserDto(getUserDtoWithoutId());
        subscription.setTotalCost(BigDecimal.valueOf(123.12));
        subscription.setStatusDto(SubscriptionDto.StatusDto.PENDING);

        subscription.addSubscriptionDetailDto(getSubscriptionDetailDtoWithId());
        return subscription;
    }

    public static SubscriptionDto getSubscriptionDtoWithId() {
        SubscriptionDto subscription = getSubscriptionDtoWithoutId();
        subscription.setId(1L);
        subscription.setStatusDto(SubscriptionDto.StatusDto.AWAITING_PAYMENT);
        return subscription;
    }

    public static SubscriptionDto getPayedSubscriptionDto() {
        SubscriptionDto subscription = getSubscriptionDtoWithId();
        subscription.setStatusDto(SubscriptionDto.StatusDto.PAYED);
        subscription.getSubscriptionDetailDtos().get(0).setSubscriptionStartDate(LocalDate.of(2020, 1, 1));
        subscription.getSubscriptionDetailDtos().get(0).setSubscriptionEndDate(LocalDate.of(2020, 12, 31));
        return subscription;
    }

    public static SubscriptionDetailDto getSubscriptionDetailDtoWithoutId() {
        SubscriptionDetailDto subscriptionDetail = new SubscriptionDetailDto();
        subscriptionDetail.setPeriodicalDto(getPeriodicalDtoWithId());
        subscriptionDetail.setSubscriptionDurationInYears(1);
        subscriptionDetail.setPeriodicalCurrentPrice(BigDecimal.valueOf(123.12));
        return subscriptionDetail;
    }

    public static SubscriptionDetailDto getSubscriptionDetailDtoWithId() {
        SubscriptionDetailDto subscriptionDetail = getSubscriptionDetailDtoWithoutId();
        subscriptionDetail.setId(1L);
        return subscriptionDetail;
    }

    public static Payment getPaymentWithOutId() {
        Payment payment = new Payment();
        payment.setUser(getUserWithoutId());
        payment.setPaymentTime(LocalDateTime.of(2020, 1, 15, 15, 0));
        payment.setPaymentMethod(Payment.PaymentMethod.CREDIT_OR_DEBIT_CARD);
        payment.setSubscription(getSubscriptionWithId());

        return payment;
    }

    public static PaymentDto getPaymentDtoWithoutId() {
        PaymentDto payment = new PaymentDto();
        payment.setUserDto(getUserDtoWithoutId());
        payment.setPaymentTime(LocalDateTime.of(2020, 1, 15, 15, 0));
        payment.setPaymentMethodDto(PaymentDto.PaymentMethodDto.CREDIT_OR_DEBIT_CARD);
        payment.setSubscriptionDto(getSubscriptionDtoWithId());

        return payment;
    }

    public static PaymentDto getPaymentDtoWithId() {
        PaymentDto payment = getPaymentDtoWithoutId();
        payment.setId(1L);
        return payment;
    }

    public static Map<Long, Integer> getCartWithOneItem() {
        Long periodicalId = 1L;
        Integer subscriptionDurationInYears = 1;
        Map<Long, Integer> cartWithOneItem = new HashMap<>();
        cartWithOneItem.put(periodicalId, subscriptionDurationInYears);
        return cartWithOneItem;
    }

    public static Map<Long, Integer> getCartWithTwoItems() {
        Long secondPeriodicalId = 2L;
        Integer subscriptionDurationInYears = 1;
        Map<Long, Integer> cartWithTwoItems = getCartWithOneItem();
        cartWithTwoItems.put(secondPeriodicalId, subscriptionDurationInYears);
        return cartWithTwoItems;
    }

    public static List<PaymentDto> getPaymentListDto() {
        List<PaymentDto> paymentDtos = new ArrayList<>();
        paymentDtos.add(getPaymentDtoWithId());
        paymentDtos.add(getPaymentDtoWithId());
        paymentDtos.add(getPaymentDtoWithId());
        return paymentDtos;
    }
}
