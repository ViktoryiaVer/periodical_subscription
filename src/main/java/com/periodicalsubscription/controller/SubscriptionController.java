package com.periodicalsubscription.controller;

import com.periodicalsubscription.dto.SubscriptionDto;
import com.periodicalsubscription.dto.UserDto;
import com.periodicalsubscription.manager.PageManager;
import com.periodicalsubscription.service.api.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/subscription")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @GetMapping(value = "/all")
    public String getAllSubscriptions(Model model) {
        List<SubscriptionDto> subscriptions = subscriptionService.findAll();

        if(subscriptions.isEmpty()) {
            model.addAttribute("message", "No subscriptions could be found");
            return PageManager.SUBSCRIPTIONS;
        }
        model.addAttribute("subscriptions", subscriptions);

        return PageManager.SUBSCRIPTIONS;
    }

    @GetMapping("/{id}")
    public String getSusbcription(Model model, @PathVariable Long id) {
        SubscriptionDto subscription = subscriptionService.findById(id);

        model.addAttribute("subscription", subscription);

        return PageManager.SUBSCRIPTION;
    }

    @PostMapping("/create")
    public String createSubscription(HttpSession session, Model model) {
        UserDto userDto = (UserDto) session.getAttribute("user");
        if(userDto == null) {
            model.addAttribute("message", "You need to login for creating subscription");
            return PageManager.LOGIN;
        }
        @SuppressWarnings("unchecked")
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
        SubscriptionDto subscription = subscriptionService.processSubscriptionInCart(userDto, cart);
        SubscriptionDto createdSubscription = subscriptionService.save(subscription);
        session.removeAttribute("cart");

        model.addAttribute("message", "Subscription was created successfully. Manager will confirm your subscription soon.");
        model.addAttribute("subscription", createdSubscription);

        return PageManager.SUBSCRIPTION;
    }

    @PostMapping("/update/{id}")
    public String updateSubscription(@PathVariable Long id, @RequestParam String statusDto, Model model) {
        SubscriptionDto updatedSubscription = subscriptionService.updateSubscriptionStatus(SubscriptionDto.StatusDto.valueOf(statusDto), id);

        model.addAttribute("message", "Subscription status was updated successfully");
        model.addAttribute("subscription", updatedSubscription);

        return PageManager.SUBSCRIPTION;
    }
}
