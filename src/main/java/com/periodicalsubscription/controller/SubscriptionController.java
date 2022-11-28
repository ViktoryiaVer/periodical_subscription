package com.periodicalsubscription.controller;

import com.periodicalsubscription.aspect.logging.annotation.LogInvocation;
import com.periodicalsubscription.constant.PagingConstant;
import com.periodicalsubscription.controller.util.PagingUtil;
import com.periodicalsubscription.service.api.UserService;
import com.periodicalsubscription.service.dto.SubscriptionDto;
import com.periodicalsubscription.service.dto.UserDto;
import com.periodicalsubscription.exceptions.subscription.SubscriptionNotFoundException;
import com.periodicalsubscription.constant.PageConstant;
import com.periodicalsubscription.service.api.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RequestMapping("/subscriptions")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;
    private final UserService userService;
    private final PagingUtil pagingUtil;
    private final MessageSource messageSource;

    @Secured("ROLE_ADMIN")
    @LogInvocation
    @GetMapping(value = "/all")
    public String getAllSubscriptions(@RequestParam(required = false) String keyword, @RequestParam(required = false) String status,
                                      @RequestParam(defaultValue = PagingConstant.FIRST_PAGE_STRING) Integer page,
                                      @RequestParam(value = "page_size", defaultValue = PagingConstant.DEFAULT_PAGE_SIZE_STRING) Integer pageSize, Model model) {

        List<SubscriptionDto> subscriptions = pagingUtil.getSubscriptionListFromPageAndRequestParams(page, pageSize, keyword, model, status);

        if (subscriptions.isEmpty()) {
            model.addAttribute("message", messageSource.getMessage("msg.error.subscriptions.not.found", null,
                    LocaleContextHolder.getLocale()));
            return PageConstant.SUBSCRIPTIONS;
        }

        model.addAttribute("subscriptions", subscriptions);
        return PageConstant.SUBSCRIPTIONS;
    }

    @LogInvocation
    @GetMapping(value = "/user/{id}")
    public String getAllSubscriptionsByUser(@PathVariable("id") Long userId, @RequestParam(defaultValue = PagingConstant.FIRST_PAGE_STRING) Integer page,
                                            @RequestParam(value = "page_size", defaultValue = PagingConstant.DEFAULT_PAGE_SIZE_STRING) Integer pageSize, Model model) {

        userId = userService.getCorrectUserId(userId);
        List<SubscriptionDto> subscriptions = pagingUtil.getSubscriptionByUserListFromPageAndRequestParams(page, pageSize, userId, model);

        if (subscriptions.isEmpty()) {
            model.addAttribute("message", messageSource.getMessage("msg.error.subscriptions.user.not.found", null,
                    LocaleContextHolder.getLocale()));
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
        UserDto user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication()
                .getName());
        if (user == null) {
            model.addAttribute("message", messageSource.getMessage("msg.error.login.required.subscription", null,
                    LocaleContextHolder.getLocale()));
            return PageConstant.LOGIN;
        }

        @SuppressWarnings("unchecked")
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
        SubscriptionDto subscription = subscriptionService.createSubscriptionFromCart(user, cart);
        session.removeAttribute("cart");

        session.setAttribute("message", messageSource.getMessage("msg.success.subscription.created", null,
                LocaleContextHolder.getLocale()));
        return "redirect:/subscriptions/" + subscription.getId();
    }

    @LogInvocation
    @PostMapping("/update/{id}")
    public String updateSubscription(@PathVariable Long id, @RequestParam String statusDto, HttpSession session) {
        SubscriptionDto updatedSubscription = subscriptionService.updateSubscriptionStatus(SubscriptionDto.StatusDto.valueOf(statusDto), id);

        session.setAttribute("message", messageSource.getMessage("msg.success.subscription.status.updated", null,
                LocaleContextHolder.getLocale()));
        return "redirect:/subscriptions/" + updatedSubscription.getId();
    }

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleSubscriptionNotFoundException(SubscriptionNotFoundException e, Model model) {
        model.addAttribute("message", e.getMessage() + messageSource.getMessage("msg.error.action.subscription.not.found", null,
                LocaleContextHolder.getLocale()));
        return PageConstant.ERROR;
    }
}
