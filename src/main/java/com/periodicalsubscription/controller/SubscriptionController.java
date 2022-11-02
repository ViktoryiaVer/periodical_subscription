package com.periodicalsubscription.controller;

import com.periodicalsubscription.aspect.logging.annotation.LogInvocation;
import com.periodicalsubscription.dto.SubscriptionDto;
import com.periodicalsubscription.dto.UserDto;
import com.periodicalsubscription.exceptions.subscription.SubscriptionNotFoundException;
import com.periodicalsubscription.manager.ErrorMessageManager;
import com.periodicalsubscription.manager.PageManager;
import com.periodicalsubscription.manager.SuccessMessageManager;
import com.periodicalsubscription.service.api.SubscriptionService;
import com.periodicalsubscription.service.api.UserService;
import lombok.RequiredArgsConstructor;
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
    private final UserService userService;

    @LogInvocation
    @GetMapping(value = "/all")
    public String getAllSubscriptions(Model model) {
        List<SubscriptionDto> subscriptions = subscriptionService.findAll();

        if(subscriptions.isEmpty()) {
            model.addAttribute("message", ErrorMessageManager.SUBSCRIPTIONS_NOT_FOUND);
            return PageManager.SUBSCRIPTIONS;
        }
        model.addAttribute("subscriptions", subscriptions);
        return PageManager.SUBSCRIPTIONS;
    }

    @LogInvocation
    @GetMapping(value = "/user/{id}")
    public String getAllSubscriptionsByUser(@PathVariable("id") Long userId, Model model) {
        UserDto userDto = userService.findById(userId);
        List<SubscriptionDto> subscriptions = subscriptionService.findAllSubscriptionsByUser(userDto);

        if(subscriptions.isEmpty()) {
            model.addAttribute("message", ErrorMessageManager.SUBSCRIPTIONS_USER_NOT_FOUND);
            return PageManager.SUBSCRIPTIONS;
        }
        model.addAttribute("subscriptions", subscriptions);
        return PageManager.SUBSCRIPTIONS;
    }

    @LogInvocation
    @GetMapping("/{id}")
    public String getSubscription(Model model, @PathVariable Long id) {
        SubscriptionDto subscription = subscriptionService.findById(id);

        model.addAttribute("subscription", subscription);
        return PageManager.SUBSCRIPTION;
    }

    @LogInvocation
    @PostMapping("/create")
    public String createSubscription(HttpSession session, Model model) {
        UserDto userDto = (UserDto) session.getAttribute("user");
        if(userDto == null) {
            model.addAttribute("message", ErrorMessageManager.LOGIN_REQUIRED_SUBSCRIPTION);
            return PageManager.LOGIN;
        }

        @SuppressWarnings("unchecked")
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
        SubscriptionDto subscription = subscriptionService.createSubscriptionFromCart(userDto, cart);
        session.removeAttribute("cart");

        session.setAttribute("message", SuccessMessageManager.SUBSCRIPTION_CREATED);
        return "redirect:/subscription/" + subscription.getId();
    }

    @LogInvocation
    @PostMapping("/update/{id}")
    public String updateSubscription(@PathVariable Long id, @RequestParam String statusDto, HttpSession session) {
        SubscriptionDto updatedSubscription = subscriptionService.updateSubscriptionStatus(SubscriptionDto.StatusDto.valueOf(statusDto), id);

        session.setAttribute("message", SuccessMessageManager.SUBSCRIPTION_STATUS_UPDATED);
        return "redirect:/subscription/" + updatedSubscription.getId();
    }

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleSubscriptionNotFoundException(SubscriptionNotFoundException e, Model model) {
        model.addAttribute("message", e.getMessage() + " Please, enter correct subscription id or check subscriptions list.");
        return PageManager.ERROR;
    }
}
