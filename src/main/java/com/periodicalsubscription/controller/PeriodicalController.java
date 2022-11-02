package com.periodicalsubscription.controller;

import com.periodicalsubscription.aspect.logging.annotation.LogInvocation;
import com.periodicalsubscription.dto.PeriodicalDto;
import com.periodicalsubscription.exceptions.periodical.PeriodicalAlreadyExistsException;
import com.periodicalsubscription.exceptions.periodical.PeriodicalNotFoundException;
import com.periodicalsubscription.manager.ErrorMessageManager;
import com.periodicalsubscription.manager.PageManager;
import com.periodicalsubscription.manager.SuccessMessageManager;
import com.periodicalsubscription.service.api.PeriodicalService;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/periodical")
public class PeriodicalController {
    private final PeriodicalService periodicalService;

    @LogInvocation
    @GetMapping(value = "/all")
    public String getAllPeriodicals(Model model) {
        List<PeriodicalDto> periodicals = periodicalService.findAll();

        if(periodicals.isEmpty()) {
            model.addAttribute("message", ErrorMessageManager.PERIODICALS_NOT_FOUND);
            return PageManager.PERIODICALS;
        }

        model.addAttribute("periodicals", periodicals);
        return PageManager.PERIODICALS;
    }

    @LogInvocation
    @GetMapping("/{id}")
    public String getPeriodical(@PathVariable Long id, Model model) {
        PeriodicalDto periodical = periodicalService.findById(id);
        model.addAttribute("periodical", periodical);
        return PageManager.PERIODICAL;
    }

    @LogInvocation
    @GetMapping("/create")
    public String createPeriodicalForm() {
        return PageManager.CREATE_PERIODICAL;
    }

    @LogInvocation
    @PostMapping("/create")
    public String createPeriodical(@ModelAttribute @Valid PeriodicalDto periodical, Errors errors, MultipartFile imageFile, Model model, HttpSession session) {
        if(errors.hasErrors()) {
            model.addAttribute("errors", errors.getFieldErrors());
            return PageManager.CREATE_PERIODICAL;
        }

        PeriodicalDto createdPeriodical = periodicalService.processPeriodicalCreation(periodical, imageFile);
        session.setAttribute("message", SuccessMessageManager.PERIODICAL_CREATED);
        return "redirect:/periodical/" + createdPeriodical.getId();
    }

    @LogInvocation
    @GetMapping("/update/{id}")
    public String updatePeriodicalForm(@PathVariable Long id, Model model) {
        PeriodicalDto periodical = periodicalService.findById(id);
        model.addAttribute("periodical", periodical);
        return PageManager.UPDATE_PERIODICAL;
    }

    @LogInvocation
    @PostMapping("/update")
    public String updatePeriodical(@ModelAttribute @Valid PeriodicalDto periodical, Errors errors, MultipartFile imageFile, Model model, HttpSession session) {
        if(errors.hasErrors()) {
            model.addAttribute("errors", errors);
            return PageManager.UPDATE_PERIODICAL;
        }

        PeriodicalDto updatedPeriodical = periodicalService.processPeriodicalUpdate(periodical, imageFile);
        session.setAttribute("message", SuccessMessageManager.PERIODICAL_UPDATED);
        return "redirect:/periodical/" + updatedPeriodical.getId();
    }

    @LogInvocation
    @PostMapping("/delete/{id}")
    public String deletePeriodical(@PathVariable Long id, HttpSession session) {
        periodicalService.deleteById(id);
        session.setAttribute("message", SuccessMessageManager.PERIODICAL_DELETED);
        return "redirect:/periodical/all";
    }

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handlePeriodicalNotFoundException(PeriodicalNotFoundException e, Model model) {
        model.addAttribute("message", e.getMessage() + " Please, enter correct periodical id or contact the administrator.");
        return PageManager.ERROR;
    }

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handlePeriodicalAlreadyExistsException(PeriodicalAlreadyExistsException e, Model model) {
        model.addAttribute("message", e.getMessage() + " Please, check periodical title and edit the catalog if needed.");
        return PageManager.ERROR;
    }
}
