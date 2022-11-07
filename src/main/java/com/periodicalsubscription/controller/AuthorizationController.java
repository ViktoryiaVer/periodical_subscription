package com.periodicalsubscription.controller;

import com.periodicalsubscription.aspect.logging.annotation.LogInvocation;
import com.periodicalsubscription.service.dto.UserDto;
import com.periodicalsubscription.constant.PageConstant;
import com.periodicalsubscription.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class AuthorizationController {
    private final UserService userService;
    private final MessageSource messageSource;

    @LogInvocation
    @GetMapping("/login")
    public String loginForm(HttpSession session) {
        if (session.getAttribute("user") != null) {
            return PageConstant.ALREADY_LOGGED_IN;
        }
        return PageConstant.LOGIN;
    }

    @LogInvocation
    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            model.addAttribute("message", messageSource.getMessage("msg.error.login.data.not.specified", null,
                    LocaleContextHolder.getLocale()));
            return PageConstant.LOGIN;
        }

        UserDto userDto = userService.login(email, password);

        session.setAttribute("user", userDto);
        model.addAttribute("message", messageSource.getMessage("msg.success.user.logged.in", null,
                LocaleContextHolder.getLocale()));
        return PageConstant.HOME;
    }

    @LogInvocation
    @GetMapping("/logout")
    public String logout(HttpSession session, Model model) {
        if (session.getAttribute("user") != null) {
            session.removeAttribute("user");
        }

        model.addAttribute("message", messageSource.getMessage("msg.success.user.logged.out", null,
                LocaleContextHolder.getLocale()));
        return PageConstant.HOME;
    }
}
