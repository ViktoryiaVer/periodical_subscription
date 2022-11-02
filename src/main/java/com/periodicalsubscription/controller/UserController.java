package com.periodicalsubscription.controller;

import com.periodicalsubscription.aspect.logging.annotation.LogInvocation;
import com.periodicalsubscription.dto.UserDto;
import com.periodicalsubscription.exceptions.user.UserAlreadyExistsException;
import com.periodicalsubscription.exceptions.user.UserNotFoundException;
import com.periodicalsubscription.manager.ErrorMessageManager;
import com.periodicalsubscription.manager.PageManager;
import com.periodicalsubscription.manager.SuccessMessageManager;
import com.periodicalsubscription.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @LogInvocation
    @GetMapping(value = "/all")
    public String getAllUsers(Model model) {
        List<UserDto> users = userService.findAll();

        if(users.isEmpty()) {
            model.addAttribute("message", ErrorMessageManager.USERS_NOT_FOUND);
            return PageManager.USERS;
        }
        model.addAttribute("users", users);
        return PageManager.USERS;
    }

    @LogInvocation
    @GetMapping("/{id}")
    public String getUser(@PathVariable Long id, Model model) {
        UserDto user = userService.findById(id);
        model.addAttribute("user", user);
        return PageManager.USER;
    }

    @LogInvocation
    @GetMapping("/create")
    public String createUserForm(HttpSession session) {
        if(session.getAttribute("user") != null) {
            return PageManager.ALREADY_LOGGED_IN;
        }
        return PageManager.SIGNUP;
    }

    @LogInvocation
    @PostMapping("/create")
    public String createUser(@Valid @ModelAttribute UserDto user, Errors errors, HttpSession session, Model model) {
        if(errors.hasErrors()) {
            model.addAttribute("errors", errors.getFieldErrors());
            return PageManager.SIGNUP;
        }

        user.setRoleDto(UserDto.RoleDto.READER);
        UserDto createdUser = userService.save(user);

        session.setAttribute("user", createdUser);
        session.setAttribute("message", SuccessMessageManager.USER_CREATED);
        return "redirect:/user/" + createdUser.getId();
    }

    @LogInvocation
    @GetMapping("/update/{id}")
    public String updateUserForm(@PathVariable Long id, Model model) {
        UserDto user = userService.findById(id);
        model.addAttribute("user", user);
        return PageManager.UPDATE_USER;
    }

    @LogInvocation
    @PostMapping("/update")
    public String updateUser(@Valid @ModelAttribute UserDto user, Errors errors, Model model, HttpSession session) {
        if(errors.hasErrors()) {
            model.addAttribute("errors", errors.getFieldErrors());
            model.addAttribute("user", user);
            return PageManager.UPDATE_USER;
        }

        UserDto updatedUser = userService.update(user);
        session.setAttribute("message", SuccessMessageManager.USER_UPDATED);
        return "redirect:/user/" + updatedUser.getId();
    }

    @LogInvocation
    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, HttpSession session) {
        userService.deleteById(id);
        session.setAttribute("message", SuccessMessageManager.USER_DELETED);
        return "redirect:/user/all";
    }

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleUserNotFoundException(UserNotFoundException e, Model model) {
        model.addAttribute("message", e.getMessage() + " Please, enter correct user id or sign up.");
        return PageManager.ERROR;
    }

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleUserAlreadyExistsException(UserAlreadyExistsException e, Model model) {
        model.addAttribute("message", e.getMessage() + " Please, specify a unique email address.");
        return PageManager.ERROR;
    }
}
