package com.periodicalsubscription.controller;

import com.periodicalsubscription.aspect.logging.annotation.LogInvocation;
import com.periodicalsubscription.dto.UserDto;
import com.periodicalsubscription.manager.ErrorMessageManager;
import com.periodicalsubscription.manager.PageManager;
import com.periodicalsubscription.manager.SuccessMessageManager;
import com.periodicalsubscription.service.api.UserService;
import lombok.RequiredArgsConstructor;
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

    @LogInvocation
    @GetMapping("/login")
    public String loginForm(HttpSession session) {
        if(session.getAttribute("user") != null) {
            return PageManager.ALREADY_LOGGED_IN;
        }
        return PageManager.LOGIN;
    }

    @LogInvocation
    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        if(email == null || email.isBlank() || password == null || password.isBlank()) {
            model.addAttribute("message", ErrorMessageManager.LOGIN_DATA_NOT_SPECIFIED);
            return PageManager.LOGIN;
        }

        UserDto userDto = userService.login(email, password);

        session.setAttribute("user", userDto);
        model.addAttribute("message", SuccessMessageManager.USER_LOGGED_IN);
        return PageManager.HOME;
    }

    @LogInvocation
    @GetMapping("/logout")
    public String logout(HttpSession session, Model model) {
        if(session.getAttribute("user") != null) {
            session.removeAttribute("user");
        }

        model.addAttribute("message", SuccessMessageManager.USER_LOGGED_OUT);
        return PageManager.HOME;
    }
}
