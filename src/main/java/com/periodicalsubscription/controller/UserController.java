package com.periodicalsubscription.controller;

import com.periodicalsubscription.dto.UserDto;
import com.periodicalsubscription.manager.PageManager;
import com.periodicalsubscription.service.api.SubscriptionService;
import com.periodicalsubscription.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final SubscriptionService subscriptionService;

    @GetMapping(value = "/all")
    public String getAllUsers(Model model) {
        List<UserDto> users = userService.findAll();

        if(users.isEmpty()) {
            model.addAttribute("message", "No users could be found");
            return PageManager.USERS;
        }
        model.addAttribute("users", users);

        return PageManager.USERS;
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable Long id, Model model) {
        UserDto user = userService.findById(id);

        model.addAttribute("user", user);

        return PageManager.USER;
    }

    @GetMapping("/create")
    public String createUserForm(HttpSession session) {
        if(session.getAttribute("user") != null) {
            return PageManager.ALREADY_LOGGED_IN;
        }
        return PageManager.SIGNUP;
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute UserDto user, HttpSession session, Model model) {
        user.setRoleDto(UserDto.RoleDto.READER);
        UserDto createdUser = userService.save(user);
        session.setAttribute("user", createdUser);
        model.addAttribute("message", "You were signed up successfully");
        return PageManager.HOME;
    }

    @GetMapping("/update/{id}")
    public String updateUserForm(@PathVariable Long id, Model model) {
        UserDto user = userService.findById(id);
        model.addAttribute("user", user);
        return PageManager.UPDATE_USER;
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute UserDto user, Model model) {
        userService.update(user);
        model.addAttribute("message", "User was updated successfully");
        return PageManager.HOME;
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, Model model) {
        UserDto userDto = userService.findById(id);

        if(subscriptionService.checkIfSubscriptionExistsByUSer(userDto)) {
            throw new RuntimeException("User with subscriptions can't be deleted");
        }

        userService.delete(id);
        model.addAttribute("message", "User was deleted successfully");
        return PageManager.HOME;
    }
}
