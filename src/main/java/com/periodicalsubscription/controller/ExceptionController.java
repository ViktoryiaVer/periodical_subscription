package com.periodicalsubscription.controller;

import com.periodicalsubscription.aspect.logging.annotation.LogInvocation;
import com.periodicalsubscription.exceptions.ImageUploadException;
import com.periodicalsubscription.exceptions.LoginException;
import com.periodicalsubscription.exceptions.ServiceException;
import com.periodicalsubscription.constant.PageConstant;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Arrays;

@ControllerAdvice
@Log4j2
public class ExceptionController {

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleServiceException(ServiceException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return PageConstant.ERROR;
    }

    @LogInvocation
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler
    public String handleLoginException(LoginException e, Model model) {
        model.addAttribute("message", e.getMessage() + ". Please, enter correct data.");
        return PageConstant.LOGIN;
    }

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleImageUploadingException(ImageUploadException e, Model model) {
        model.addAttribute("message", e.getMessage() + ". Reason: " + e.getCause().getMessage());
        return PageConstant.ERROR;
    }

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleFormatException(MethodArgumentTypeMismatchException e, Model model) {
        model.addAttribute("message", "Wrong format for URL address, please, use correct format.");
        return PageConstant.ERROR;
    }

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleRuntimeException(RuntimeException e, Model model) {
        model.addAttribute("message", "Something went wrong. Please, check data accuracy or contact the administrator. ");
        log.error("Error while running application: " + e + "\n" + Arrays.toString(e.getStackTrace()));
        return PageConstant.ERROR;
    }
}
