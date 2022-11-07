package com.periodicalsubscription.controller;

import com.periodicalsubscription.aspect.logging.annotation.LogInvocation;
import com.periodicalsubscription.constant.PagingConstant;
import com.periodicalsubscription.controller.util.PagingUtil;
import com.periodicalsubscription.dto.UserDto;
import com.periodicalsubscription.exceptions.user.UserAlreadyExistsException;
import com.periodicalsubscription.exceptions.user.UserNotFoundException;
import com.periodicalsubscription.constant.PageConstant;
import com.periodicalsubscription.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final PagingUtil pagingUtil;
    private final MessageSource messageSource;

    @LogInvocation
    @GetMapping(value = "/all")
    public String getAllUsers(@RequestParam(defaultValue = PagingConstant.FIRST_PAGE_STRING) Integer page,
                              @RequestParam(value = "page_size", defaultValue = PagingConstant.DEFAULT_PAGE_SIZE_STRING) Integer pageSize, Model model) {
        Page<UserDto> userPage = userService.findAll(pagingUtil.getPageableFromRequest(page, pageSize, "id"));
        pagingUtil.setAttributesForPagingDisplay(model, pageSize, userPage, "/user/all");
        List<UserDto> users = userPage.toList();

        if (users.isEmpty()) {
            model.addAttribute("message", messageSource.getMessage("msg.error.users.not.found", null,
                    LocaleContextHolder.getLocale()));
            return PageConstant.USERS;
        }
        model.addAttribute("users", users);
        return PageConstant.USERS;
    }

    @LogInvocation
    @GetMapping("/{id}")
    public String getUser(@PathVariable Long id, Model model) {
        UserDto user = userService.findById(id);
        model.addAttribute("user", user);
        return PageConstant.USER;
    }

    @LogInvocation
    @GetMapping("/create")
    public String createUserForm(HttpSession session) {
        if (session.getAttribute("user") != null) {
            return PageConstant.ALREADY_LOGGED_IN;
        }
        return PageConstant.SIGNUP;
    }

    @LogInvocation
    @PostMapping("/create")
    public String createUser(@Valid @ModelAttribute UserDto user, Errors errors, HttpSession session, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("errors", errors.getFieldErrors());
            return PageConstant.SIGNUP;
        }

        user.setRoleDto(UserDto.RoleDto.READER);
        UserDto createdUser = userService.save(user);

        session.setAttribute("user", createdUser);
        session.setAttribute("message", messageSource.getMessage("msg.success.user.created", null,
                LocaleContextHolder.getLocale()));
        return "redirect:/user/" + createdUser.getId();
    }

    @LogInvocation
    @GetMapping("/update/{id}")
    public String updateUserForm(@PathVariable Long id, Model model) {
        UserDto user = userService.findById(id);
        model.addAttribute("user", user);
        return PageConstant.UPDATE_USER;
    }

    @LogInvocation
    @PostMapping("/update")
    public String updateUser(@Valid @ModelAttribute UserDto user, Errors errors, Model model, HttpSession session) {
        if (errors.hasErrors()) {
            model.addAttribute("errors", errors.getFieldErrors());
            model.addAttribute("user", user);
            return PageConstant.UPDATE_USER;
        }

        UserDto updatedUser = userService.update(user);
        session.setAttribute("message", messageSource.getMessage("msg.success.user.updated", null,
                LocaleContextHolder.getLocale()));
        return "redirect:/user/" + updatedUser.getId();
    }

    @LogInvocation
    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, HttpSession session) {
        userService.deleteById(id);
        session.setAttribute("message", messageSource.getMessage("msg.success.user.deleted", null,
                LocaleContextHolder.getLocale()));
        return "redirect:/user/all";
    }

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleUserNotFoundException(UserNotFoundException e, Model model) {
        model.addAttribute("message", e.getMessage() + messageSource.getMessage("msg.error.action.user.not.found", null,
                LocaleContextHolder.getLocale()));
        return PageConstant.ERROR;
    }

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleUserAlreadyExistsException(UserAlreadyExistsException e, Model model) {
        model.addAttribute("message", e.getMessage() + messageSource.getMessage("msg.error.action.user.email.exists", null,
                LocaleContextHolder.getLocale()));
        return PageConstant.ERROR;
    }
}
