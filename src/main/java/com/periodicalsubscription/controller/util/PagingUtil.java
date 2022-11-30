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
import com.periodicalsubscription.service.dto.UserWithoutPasswordDto;
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

/**
 * class with methods for paging handling in controllers
 */
@Component
@RequiredArgsConstructor
public class PagingUtil {
    private final PaymentService paymentService;
    private final PeriodicalService periodicalService;
    private final SubscriptionService subscriptionService;
    private final UserService userService;

    /**
     * gets payment list from page, filters and search params of request
     *
     * @param page      page to be displayed
     * @param pageSize  amount of objects to be displayed on one page
     * @param keyword   word to be searched by (can be null)
     * @param model     model from the controller method
     * @param filterDto filter object to be filtered by
     * @return list of payments to be displayed
     */
    @LogInvocation
    public List<PaymentDto> getPaymentListFromPageAndRequestParams(Integer page, Integer pageSize, String keyword, Model model, PaymentFilterDto filterDto) {
        Pageable pageable = getPageableFromRequest(page, pageSize, PagingConstant.DEFAULT_SORTING_PAYMENT);
        Page<PaymentDto> paymentPage = getPaymentDtoPage(filterDto, keyword, model, pageable);
        setAttributesForPaginationDisplay(model, pageSize, paymentPage, "/payments/all");
        return paymentPage.toList();
    }

    /**
     * gets periodical list from page, filters and search params of request
     *
     * @param page      page to be displayed
     * @param pageSize  amount of objects to be displayed on one page
     * @param keyword   word to be searched by (can be null)
     * @param model     model from the controller method
     * @param filterDto filter object to be filtered by
     * @return list of periodicals to be displayed
     */
    @LogInvocation
    public List<PeriodicalDto> getPeriodicalListFromPageAndRequestParams(Integer page, Integer pageSize, String keyword, Model model, PeriodicalFilterDto filterDto) {
        Pageable pageable = getPageableFromRequest(page, pageSize, PagingConstant.DEFAULT_SORTING_PERIODICAL);
        Page<PeriodicalDto> periodicalPage = getPeriodicalDtoPage(filterDto, keyword, model, pageable);
        setAttributesForPaginationDisplay(model, pageSize, periodicalPage, "/periodicals/all");
        return periodicalPage.toList();
    }

    /**
     * gets subscription list from page, filters and search params of request
     *
     * @param page     page to be displayed
     * @param pageSize amount of objects to be displayed on one page
     * @param keyword  word to be searched by (can be null)
     * @param model    model from the controller method
     * @param status   subscription status to be filtered by (can be null)
     * @return list of subscriptions to be displayed
     */
    @LogInvocation
    public List<SubscriptionDto> getSubscriptionListFromPageAndRequestParams(Integer page, Integer pageSize, String keyword, Model model, String status) {
        Pageable pageable = getPageableFromRequest(page, pageSize, PagingConstant.DEFAULT_SORTING_SUBSCRIPTION);
        Page<SubscriptionDto> subscriptionPage = getSubscriptionDtoPage(keyword, status, model, pageable);
        setAttributesForPaginationDisplay(model, pageSize, subscriptionPage, "/subscriptions/all");
        return subscriptionPage.toList();
    }

    /**
     * gets subscription list by a user from page params of request
     *
     * @param page     page to be displayed
     * @param pageSize amount of objects to be displayed on one page
     * @param userId   id of a user
     * @param model    model from the controller method
     * @return list of subscriptions for a specific user to be displayed
     */
    @LogInvocation
    public List<SubscriptionDto> getSubscriptionByUserListFromPageAndRequestParams(Integer page, Integer pageSize, Long userId, Model model) {
        Page<SubscriptionDto> subscriptionPage = subscriptionService.findAllSubscriptionsByUserId(userId, getPageableFromRequest(page, pageSize, "id"));
        setAttributesForPaginationDisplay(model, pageSize, subscriptionPage, "/subscription/user/" + userId);
        return subscriptionPage.toList();
    }

    /**
     * gets user list from page and search params of request
     *
     * @param page     page to be displayed
     * @param pageSize amount of objects to be displayed on one page
     * @param keyword  word to be searched by (can be null)
     * @param model    model from the controller method
     * @return list of users to be displayed
     */
    @LogInvocation
    public List<UserWithoutPasswordDto> getUserListFromPageAndRequestParams(Integer page, Integer pageSize, String keyword, Model model) {
        Pageable pageable = getPageableFromRequest(page, pageSize, PagingConstant.DEFAULT_SORTING_USER);
        Page<UserWithoutPasswordDto> userPage = getUserDtoPage(keyword, model, pageable);
        setAttributesForPaginationDisplay(model, pageSize, userPage, "/users/all");
        return userPage.toList();
    }

    /**
     * gets Pageable object from request
     *
     * @param page     page to be displayed
     * @param pageSize amount of objects to be displayed on one page
     * @param sortProp property the objects to be sorted by
     * @return Pageable object
     */
    @LogInvocation
    private Pageable getPageableFromRequest(Integer page, Integer pageSize, String sortProp) {
        page = getCorrectPage(page);
        pageSize = getCorrectPageSize(pageSize);
        Sort sort = Sort.by(Sort.Direction.ASC, sortProp);
        return PageRequest.of(page - 1, pageSize, sort);
    }

    /**
     * gets correct page number
     *
     * @param rawPage page number received from request
     * @return correct page number
     */
    @LogInvocation
    private Integer getCorrectPage(Integer rawPage) {
        return rawPage <= 0 ? PagingConstant.FIRST_PAGE : rawPage;
    }

    /**
     * gets correct page size
     *
     * @param rawPageSize page size received from request
     * @return correct page size
     */
    @LogInvocation
    private Integer getCorrectPageSize(Integer rawPageSize) {
        int pageSize = rawPageSize <= 0 ? PagingConstant.DEFAULT_PAGE_SIZE : rawPageSize;
        pageSize = Math.min(pageSize, PagingConstant.MAX_PAGE_SIZE);
        return pageSize;
    }

    /**
     * sets attributes for pagination into model object
     *
     * @param model    model for pagination params to be set into
     * @param pageSize amount of objects to be displayed on one page
     * @param page     page to be displayed
     * @param command  name of the controller method
     * @param <T>      generic for page object
     */
    @LogInvocation
    private <T> void setAttributesForPaginationDisplay(Model model, Integer pageSize, Page<T> page, String command) {
        model.addAttribute("currentPage", page.getPageable().getPageNumber() + 1);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("command", command);
        model.addAttribute("pageSize", pageSize);
    }

    /**
     * gets paymentDto page object
     *
     * @param filterDto filter object to be filtered by
     * @param keyword   word to be searched by
     * @param model     model from the controller method
     * @param pageable  Pageable object for finding paymentDto page
     * @return paymentDto page object
     */
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

    /**
     * gets periodicalDto page object
     *
     * @param filterDto filter object to be filtered by
     * @param keyword   word to be searched by
     * @param model     model from the controller method
     * @param pageable  Pageable object for finding periodicalDto page
     * @return periodicalDto page object
     */
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

    /**
     * gets subscriptionDto page object
     *
     * @param keyword  word to be searched by
     * @param status   subscription status to be filtered by
     * @param model    model from the controller method
     * @param pageable Pageable object for finding subscriptionDto page
     * @return subscriptionDto page object
     */
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

    /**
     * gets userDtoWithoutPassword page object
     *
     * @param keyword  word to be searched by
     * @param model    model from the controller method
     * @param pageable Pageable object for finding userDtoWithoutPassword page
     * @return userDtoWithoutPassword page object
     */
    @LogInvocation
    private Page<UserWithoutPasswordDto> getUserDtoPage(String keyword, Model model, Pageable pageable) {
        Page<UserWithoutPasswordDto> userPage;
        if (keyword != null) {
            userPage = userService.searchForUserByKeyword(keyword, pageable);
            model.addAttribute("search", keyword);
        } else {
            userPage = userService.findAll(pageable);
        }
        return userPage;
    }
}
