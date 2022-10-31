package com.periodicalsubscription.controller;

import com.periodicalsubscription.dto.SubscriptionDto;
import com.periodicalsubscription.dto.UserDto;
import com.periodicalsubscription.manager.PageManager;
import com.periodicalsubscription.manager.SuccessMessageManager;
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
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final SubscriptionService subscriptionService;

    @PostMapping("/add/{id}")
    public String addToCart(@PathVariable("id") Long periodicalId, @RequestParam Integer subscriptionDurationInYears, HttpSession session) {
        @SuppressWarnings("unchecked")
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
        if(cart == null) {
            cart = new HashMap<>();
        }

        cart.put(periodicalId, subscriptionDurationInYears);
        session.setAttribute("cart", cart);
        session.setAttribute("message", SuccessMessageManager.CART_ADDED);
        return "redirect:/periodical/" + periodicalId;
    }

    @GetMapping("/show")
    public String getCart(HttpSession session, Model model) {
        @SuppressWarnings("unchecked")
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
        if(cart == null) {
            model.addAttribute("message", SuccessMessageManager.CART_EMPTY);
            return PageManager.CART;
        }

        UserDto user = (UserDto) session.getAttribute("user");
        SubscriptionDto processedSubscription = subscriptionService.processSubscriptionInCart(user, cart);
        model.addAttribute("cart", processedSubscription);
        return PageManager.CART;
    }

    @PostMapping("/delete/{id}")
    public String deleteFromCart(@PathVariable("id") Long periodicalId, HttpSession session) {
        @SuppressWarnings("unchecked")
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
        if(cart == null) {
            cart = new HashMap<>();
        }

        cart.remove(periodicalId);

        if(cart.isEmpty()) {
            session.removeAttribute("cart");
            session.setAttribute("message", SuccessMessageManager.CART_EMPTY);
        } else {
            session.setAttribute("cart", cart);
        }
        return "redirect:/cart/show";
    }

    @PostMapping("/delete/all")
    public String deleteAllFromCart(HttpSession session, Model model) {
        session.removeAttribute("cart");
        model.addAttribute("message", SuccessMessageManager.CART_EMPTY);
        return PageManager.CART;
    }
}
