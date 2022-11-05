package com.periodicalsubscription.controller;

import com.periodicalsubscription.aspect.logging.annotation.LogInvocation;
import com.periodicalsubscription.controller.util.PagingUtil;
import com.periodicalsubscription.dto.SubscriptionDto;
import com.periodicalsubscription.dto.UserDto;
import com.periodicalsubscription.exceptions.subscription.SubscriptionNotFoundException;
import com.periodicalsubscription.constant.ErrorMessageConstant;
import com.periodicalsubscription.constant.PageConstant;
import com.periodicalsubscription.constant.SuccessMessageConstant;
import com.periodicalsubscription.service.api.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/subscription")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;
    private final PagingUtil pagingUtil;

    @LogInvocation
    @GetMapping(value = "/all")
    public String getAllSubscriptions(@RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(value = "page_size", defaultValue = "10") Integer pageSize, Model model) {
        Page<SubscriptionDto> subscriptionPage = subscriptionService.findAll(pagingUtil.getPageableFromRequest(page, pageSize, "id"));
        pagingUtil.setAttributesForPagingDisplay(model, pageSize, subscriptionPage, "/subscription/all");
        List<SubscriptionDto> subscriptions = subscriptionPage.toList();

        if (subscriptions.isEmpty()) {
            model.addAttribute("message", ErrorMessageConstant.SUBSCRIPTIONS_NOT_FOUND);
            return PageConstant.SUBSCRIPTIONS;
        }
        model.addAttribute("subscriptions", subscriptions);
        return PageConstant.SUBSCRIPTIONS;
    }

    @LogInvocation
    @GetMapping(value = "/user/{id}")
    public String getAllSubscriptionsByUser(@PathVariable("id") Long userId, @RequestParam(defaultValue = "1") Integer page,
                                            @RequestParam(value = "page_size", defaultValue = "10") Integer pageSize, Model model) {
        Page<SubscriptionDto> subscriptionPage = subscriptionService.findAllSubscriptionsByUserId(userId, pagingUtil.getPageableFromRequest(page, pageSize, "id"));
        pagingUtil.setAttributesForPagingDisplay(model, pageSize, subscriptionPage, "/subscription/user/" + userId);
        List<SubscriptionDto> subscriptions = subscriptionPage.toList();

        if (subscriptions.isEmpty()) {
            model.addAttribute("message", ErrorMessageConstant.SUBSCRIPTIONS_USER_NOT_FOUND);
            return PageConstant.SUBSCRIPTIONS;
        }
        model.addAttribute("subscriptions", subscriptions);
        return PageConstant.SUBSCRIPTIONS;
    }

    @LogInvocation
    @GetMapping("/{id}")
    public String getSubscription(Model model, @PathVariable Long id) {
        SubscriptionDto subscription = subscriptionService.findById(id);

        model.addAttribute("subscription", subscription);
        return PageConstant.SUBSCRIPTION;
    }

    @LogInvocation
    @PostMapping("/create")
    public String createSubscription(HttpSession session, Model model) {
        UserDto userDto = (UserDto) session.getAttribute("user");
        if (userDto == null) {
            model.addAttribute("message", ErrorMessageConstant.LOGIN_REQUIRED_SUBSCRIPTION);
            return PageConstant.LOGIN;
        }

        @SuppressWarnings("unchecked")
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
        SubscriptionDto subscription = subscriptionService.createSubscriptionFromCart(userDto, cart);
        session.removeAttribute("cart");

        session.setAttribute("message", SuccessMessageConstant.SUBSCRIPTION_CREATED);
        return "redirect:/subscription/" + subscription.getId();
    }

    @LogInvocation
    @PostMapping("/update/{id}")
    public String updateSubscription(@PathVariable Long id, @RequestParam String statusDto, HttpSession session) {
        SubscriptionDto updatedSubscription = subscriptionService.updateSubscriptionStatus(SubscriptionDto.StatusDto.valueOf(statusDto), id);

        session.setAttribute("message", SuccessMessageConstant.SUBSCRIPTION_STATUS_UPDATED);
        return "redirect:/subscription/" + updatedSubscription.getId();
    }

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleSubscriptionNotFoundException(SubscriptionNotFoundException e, Model model) {
        model.addAttribute("message", e.getMessage() + " Please, enter correct subscription id or check subscriptions list.");
        return PageConstant.ERROR;
    }
}
