package com.periodicalsubscription.controller.util;

import com.periodicalsubscription.aspect.logging.annotation.LogInvocation;
import com.periodicalsubscription.constant.PagingConstant;
import com.periodicalsubscription.service.api.PaymentService;
import com.periodicalsubscription.service.api.PeriodicalService;
import com.periodicalsubscription.service.api.SubscriptionService;
import com.periodicalsubscription.service.api.UserService;
import com.periodicalsubscription.service.dto.PaymentDto;
import com.periodicalsubscription.service.dto.PeriodicalDto;
import com.periodicalsubscription.service.dto.SubscriptionDto;
import com.periodicalsubscription.service.dto.UserDto;
import com.periodicalsubscription.service.dto.filter.PaymentFilterDto;
import com.periodicalsubscription.service.dto.filter.PeriodicalFilterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PagingUtil {
    private final PaymentService paymentService;
    private final PeriodicalService periodicalService;
    private final SubscriptionService subscriptionService;
    private final UserService userService;

    @LogInvocation
    public List<PaymentDto> getPaymentListFromPageAndRequestParams(Integer page, Integer pageSize, String keyword, Model model, PaymentFilterDto filterDto) {
        Pageable pageable = getPageableFromRequest(page, pageSize, PagingConstant.DEFAULT_SORTING_PAYMENT);
        Page<PaymentDto> paymentPage = getPaymentDtoPage(filterDto, keyword, model, pageable);
        setAttributesForPagingDisplay(model, pageSize, paymentPage, "/payments/all");
        return paymentPage.toList();
    }

    @LogInvocation
    public List<PeriodicalDto> getPeriodicalListFromPageAndRequestParams(Integer page, Integer pageSize, String keyword, Model model, PeriodicalFilterDto filterDto) {
        Pageable pageable = getPageableFromRequest(page, pageSize, PagingConstant.DEFAULT_SORTING_PERIODICAL);
        Page<PeriodicalDto> periodicalPage = getPeriodicalDtoPage(filterDto, keyword, model, pageable);
        setAttributesForPagingDisplay(model, pageSize, periodicalPage, "/periodicals/all");
        return periodicalPage.toList();
    }

    @LogInvocation
    public List<SubscriptionDto> getSubscriptionListFromPageAndRequestParams(Integer page, Integer pageSize, String keyword, Model model, String status) {
        Pageable pageable = getPageableFromRequest(page, pageSize, PagingConstant.DEFAULT_SORTING_SUBSCRIPTION);
        Page<SubscriptionDto> subscriptionPage = getSubscriptionDtoPage(keyword, status, model, pageable);
        setAttributesForPagingDisplay(model, pageSize, subscriptionPage, "/subscriptions/all");
        return subscriptionPage.toList();
    }

    @LogInvocation
    public List<SubscriptionDto> getSubscriptionByUserListFromPageAndRequestParams(Integer page, Integer pageSize, Long userId, Model model) {
        Page<SubscriptionDto> subscriptionPage = subscriptionService.findAllSubscriptionsByUserId(userId, getPageableFromRequest(page, pageSize, "id"));
        setAttributesForPagingDisplay(model, pageSize, subscriptionPage, "/subscription/user/" + userId);
        return subscriptionPage.toList();
    }

    @LogInvocation
    public List<UserDto> getUserListFromPageAndRequestParams(Integer page, Integer pageSize, String keyword, Model model) {
        Pageable pageable = getPageableFromRequest(page, pageSize, PagingConstant.DEFAULT_SORTING_USER);
        Page<UserDto> userPage = getUserDtoPage(keyword, model, pageable);
        setAttributesForPagingDisplay(model, pageSize, userPage, "/users/all");
        return userPage.toList();
    }

    @LogInvocation
    private Pageable getPageableFromRequest(Integer page, Integer pageSize, String sortProp) {
        page = getCorrectPage(page);
        pageSize = getCorrectPageSize(pageSize);
        Sort sort = Sort.by(Sort.Direction.ASC, sortProp);
        return PageRequest.of(page - 1, pageSize, sort);
    }

    @LogInvocation
    private Integer getCorrectPage(Integer rawPage) {
        return rawPage <= 0 ? PagingConstant.FIRST_PAGE : rawPage;
    }

    @LogInvocation
    private Integer getCorrectPageSize(Integer rawPageSize) {
        int pageSize = rawPageSize <= 0 ? PagingConstant.DEFAULT_PAGE_SIZE : rawPageSize;
        pageSize = Math.min(pageSize, PagingConstant.MAX_PAGE_SIZE);
        return pageSize;
    }

    @LogInvocation
    private <T> void setAttributesForPagingDisplay(Model model, Integer pageSize, Page<T> page, String command) {
        model.addAttribute("currentPage", page.getPageable().getPageNumber() + 1);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("command", command);
        model.addAttribute("pageSize", pageSize);
    }

    @LogInvocation
    private Page<PaymentDto> getPaymentDtoPage(PaymentFilterDto filterDto, String keyword, Model model, Pageable pageable) {
        Page<PaymentDto> paymentPage;
        if (keyword != null) {
            paymentPage = paymentService.searchForPaymentByKeyword(keyword, pageable);
            model.addAttribute("search", keyword);
        } else if (filterDto.getPaymentMethod() != null || filterDto.getPaymentDate() != null) {
            paymentPage = paymentService.filterPayment(filterDto, pageable);
            model.addAttribute("paymentFilter", filterDto);
        } else {
            paymentPage = paymentService.findAll(pageable);
        }
        return paymentPage;
    }

    @LogInvocation
    private Page<PeriodicalDto> getPeriodicalDtoPage(PeriodicalFilterDto filterDto, String keyword, Model model, Pageable pageable) {
        Page<PeriodicalDto> periodicalPage;
        if (keyword != null) {
            periodicalPage = periodicalService.searchForPeriodicalByKeyword(keyword, pageable);
            model.addAttribute("search", keyword);
        } else if (filterDto.getCategory() != null || filterDto.getType() != null) {
            periodicalPage = periodicalService.filterPeriodical(filterDto, pageable);
            model.addAttribute("periodicalFilter", filterDto);
        } else {
            periodicalPage = periodicalService.findAll(pageable);
        }
        return periodicalPage;
    }

    @LogInvocation
    private Page<SubscriptionDto> getSubscriptionDtoPage(String keyword, String status, Model model, Pageable pageable) {
        Page<SubscriptionDto> subscriptionPage;
        if (keyword != null) {
            subscriptionPage = subscriptionService.searchForSubscriptionByKeyword(keyword, pageable);
            model.addAttribute("search", keyword);
        } else if (status != null) {
            subscriptionPage = subscriptionService.filterSubscription(status, pageable);
            model.addAttribute("subscriptionFilter", status);
        } else {
            subscriptionPage = subscriptionService.findAll(pageable);
        }
        return subscriptionPage;
    }

    @LogInvocation
    private Page<UserDto> getUserDtoPage(String keyword, Model model, Pageable pageable) {
        Page<UserDto> userPage;
        if (keyword != null) {
            userPage = userService.searchForUserByKeyword(keyword, pageable);
            model.addAttribute("search", keyword);
        } else {
            userPage = userService.findAll(pageable);
        }
        return userPage;
    }
}
