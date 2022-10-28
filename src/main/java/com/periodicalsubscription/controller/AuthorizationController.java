package com.periodicalsubscription.controller;

import com.periodicalsubscription.dto.UserDto;
import com.periodicalsubscription.manager.PageManager;
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

    @GetMapping("/login")
    public String loginForm(HttpSession session) {
        if(session.getAttribute("user") != null) {
            return PageManager.ALREADY_LOGGED_IN;
        }
        return PageManager.LOGIN;
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        //TODO rewrite for validator
        if(email == null || email.isBlank() || password == null || password.isBlank()) {
            throw new RuntimeException("Email or password is not specified");
        }
        UserDto userDto = userService.login(email, password);
        session.setAttribute("user", userDto);
        model.addAttribute("message", "You were logged in successfully");
        return PageManager.HOME;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, Model model) {
        if(session.getAttribute("user") != null) {
            session.removeAttribute("user");
        }

        model.addAttribute("message", "You were logged out successfully");
        return PageManager.HOME;
    }
}
