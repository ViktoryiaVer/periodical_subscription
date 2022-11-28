package com.periodicalsubscription.controller;

import com.periodicalsubscription.aspect.logging.annotation.LogInvocation;
import com.periodicalsubscription.exceptions.ImageUploadException;
import com.periodicalsubscription.exceptions.ServiceException;
import com.periodicalsubscription.constant.PageConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Arrays;

@ControllerAdvice
@RequiredArgsConstructor
@Log4j2
public class ExceptionController {
    private final MessageSource messageSource;

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleServiceException(ServiceException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return PageConstant.ERROR;
    }

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleImageUploadingException(ImageUploadException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return PageConstant.ERROR;
    }

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleFormatException(MethodArgumentTypeMismatchException e, Model model) {
        model.addAttribute("message", messageSource.getMessage("msg.error.wrong.url.format", null,
                LocaleContextHolder.getLocale()));
        return PageConstant.ERROR;
    }

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleRuntimeException(RuntimeException e, Model model) {
        model.addAttribute("message",  messageSource.getMessage("msg.error.something.wrong", null,
                LocaleContextHolder.getLocale()));
        log.error("Error while running application: " + e + "\n" + Arrays.toString(e.getStackTrace()));
        return PageConstant.ERROR;
    }

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleAccessDeniedException(AccessDeniedException e, Model model) {
        model.addAttribute("message", messageSource.getMessage("msg.error.access.denied", null,
                LocaleContextHolder.getLocale()));
        log.warn( e + "\n" + Arrays.toString(e.getStackTrace()));
        return PageConstant.ERROR;
    }
}
