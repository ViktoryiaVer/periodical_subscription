package com.periodicalsubscription.controller;

import com.periodicalsubscription.aspect.logging.annotation.LogInvocation;
import com.periodicalsubscription.constant.PagingConstant;
import com.periodicalsubscription.controller.util.PagingUtil;
import com.periodicalsubscription.service.dto.PeriodicalDto;
import com.periodicalsubscription.exceptions.periodical.PeriodicalAlreadyExistsException;
import com.periodicalsubscription.exceptions.periodical.PeriodicalNotFoundException;
import com.periodicalsubscription.constant.PageConstant;
import com.periodicalsubscription.service.api.PeriodicalService;
import com.periodicalsubscription.service.dto.filter.PeriodicalFilterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/periodical")
public class PeriodicalController {
    private final PeriodicalService periodicalService;
    private final PagingUtil pagingUtil;
    private final MessageSource messageSource;

    @LogInvocation
    @GetMapping(value = "/all")
    public String getAllPeriodicals(PeriodicalFilterDto filterDto, @RequestParam(required = false) String keyword,
                                    @RequestParam(defaultValue = PagingConstant.FIRST_PAGE_STRING) Integer page,
                                    @RequestParam(value = "page_size", defaultValue = PagingConstant.DEFAULT_PAGE_SIZE_STRING) Integer pageSize, Model model) {
        Pageable pageable = pagingUtil.getPageableFromRequest(page, pageSize, PagingConstant.DEFAULT_SORTING_PERIODICAL);
        Page<PeriodicalDto> periodicalPage = getPeriodicalDtoPage(filterDto, keyword, model, pageable);
        pagingUtil.setAttributesForPagingDisplay(model, pageSize, periodicalPage, "/periodical/all");
        List<PeriodicalDto> periodicals = periodicalPage.toList();

        if (periodicals.isEmpty()) {
            model.addAttribute("message", messageSource.getMessage("msg.error.periodicals.not.found", null,
                    LocaleContextHolder.getLocale()));
            return PageConstant.PERIODICALS;
        }

        model.addAttribute("periodicals", periodicals);
        return PageConstant.PERIODICALS;
    }

    @LogInvocation
    private Page<PeriodicalDto> getPeriodicalDtoPage(PeriodicalFilterDto filterDto, String keyword, Model model, Pageable pageable) {
        Page<PeriodicalDto> periodicalPage;
        if (keyword != null) {
            periodicalPage = periodicalService.searchForPeriodicalByKeyword(keyword, pageable);
            model.addAttribute("search", keyword);
        } else if (filterDto.getCategory() != null || filterDto.getType() != null) {
            periodicalPage = periodicalService.filterPeriodical(filterDto, pageable);
            model.addAttribute("periodicalFilter", filterDto);
        } else {
            periodicalPage = periodicalService.findAll(pageable);
        }
        return periodicalPage;
    }

    @LogInvocation
    @GetMapping("/{id}")
    public String getPeriodical(@PathVariable Long id, Model model) {
        PeriodicalDto periodical = periodicalService.findById(id);
        model.addAttribute("periodical", periodical);
        return PageConstant.PERIODICAL;
    }

    @LogInvocation
    @GetMapping("/create")
    public String createPeriodicalForm() {
        return PageConstant.CREATE_PERIODICAL;
    }

    @LogInvocation
    @PostMapping("/create")
    public String createPeriodical(@ModelAttribute @Valid PeriodicalDto periodical, Errors errors, MultipartFile imageFile, Model model, HttpSession session) {
        if (errors.hasErrors()) {
            model.addAttribute("errors", errors.getFieldErrors());
            return PageConstant.CREATE_PERIODICAL;
        }

        PeriodicalDto createdPeriodical = periodicalService.processPeriodicalCreation(periodical, imageFile);
        session.setAttribute("message", messageSource.getMessage("msg.success.periodical.created", null,
                LocaleContextHolder.getLocale()));
        return "redirect:/periodical/" + createdPeriodical.getId();
    }

    @LogInvocation
    @GetMapping("/update/{id}")
    public String updatePeriodicalForm(@PathVariable Long id, Model model) {
        PeriodicalDto periodical = periodicalService.findById(id);
        model.addAttribute("periodical", periodical);
        return PageConstant.UPDATE_PERIODICAL;
    }

    @LogInvocation
    @PostMapping("/update")
    public String updatePeriodical(@ModelAttribute @Valid PeriodicalDto periodical, Errors errors, MultipartFile imageFile, Model model, HttpSession session) {
        if (errors.hasErrors()) {
            model.addAttribute("errors", errors);
            model.addAttribute("periodical", periodical);
            return PageConstant.UPDATE_PERIODICAL;
        }

        PeriodicalDto updatedPeriodical = periodicalService.processPeriodicalUpdate(periodical, imageFile);
        session.setAttribute("message", messageSource.getMessage("msg.success.periodical.updated", null,
                LocaleContextHolder.getLocale()));
        return "redirect:/periodical/" + updatedPeriodical.getId();
    }

    @LogInvocation
    @PostMapping("/delete/{id}")
    public String deletePeriodical(@PathVariable Long id, HttpSession session) {
        periodicalService.deleteById(id);
        session.setAttribute("message", messageSource.getMessage("msg.success.periodical.deleted", null,
                LocaleContextHolder.getLocale()));
        return "redirect:/periodical/all";
    }

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handlePeriodicalNotFoundException(PeriodicalNotFoundException e, Model model) {
        model.addAttribute("message", e.getMessage() + messageSource.getMessage("msg.error.action.periodical.not.found", null,
                LocaleContextHolder.getLocale()));
        return PageConstant.ERROR;
    }

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handlePeriodicalAlreadyExistsException(PeriodicalAlreadyExistsException e, Model model) {
        model.addAttribute("message", e.getMessage() + messageSource.getMessage("msg.error.action.periodical.title.exists", null,
                LocaleContextHolder.getLocale()));
        return PageConstant.ERROR;
    }
}
