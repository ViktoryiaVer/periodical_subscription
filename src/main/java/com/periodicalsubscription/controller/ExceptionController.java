package com.periodicalsubscription.controller;

import com.periodicalsubscription.exceptions.ImageUploadingException;
import com.periodicalsubscription.exceptions.LoginException;
import com.periodicalsubscription.exceptions.ServiceException;
import com.periodicalsubscription.manager.PageManager;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleServiceException(ServiceException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return PageManager.ERROR;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler
    public String handleLoginException(LoginException e, Model model) {
        model.addAttribute("message", e.getMessage() + ". Please, enter correct data.");
        return PageManager.LOGIN;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleImageUploadingException(ImageUploadingException e, Model model) {
        model.addAttribute("message", e.getMessage() + ". Reason: " + e.getCause().getMessage());
        return PageManager.ERROR;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleFormatException(MethodArgumentTypeMismatchException e, Model model) {
        model.addAttribute("message", "Wrong format for URL address, please, use correct format.");
        return PageManager.ERROR;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleRuntimeException(RuntimeException e, Model model) {
        model.addAttribute("message", e.getClass().getSimpleName() + ". Please, check data accuracy or contact the administrator. ");
        return PageManager.ERROR;
    }
}
