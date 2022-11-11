package com.periodicalsubscription.controller;

import com.periodicalsubscription.aspect.logging.annotation.LogInvocation;
import com.periodicalsubscription.constant.PagingConstant;
import com.periodicalsubscription.controller.util.PagingUtil;
import com.periodicalsubscription.service.dto.PaymentDto;
import com.periodicalsubscription.exceptions.payment.PaymentNotFoundException;
import com.periodicalsubscription.constant.PageConstant;
import com.periodicalsubscription.service.api.PaymentService;
import com.periodicalsubscription.service.dto.filter.PaymentFilterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;
    private final PagingUtil pagingUtil;
    private final MessageSource messageSource;

    @LogInvocation
    @GetMapping("/all")
    public String getAllPayments(PaymentFilterDto filterDto, @RequestParam(required = false) String keyword,
                                 @RequestParam(defaultValue = PagingConstant.FIRST_PAGE_STRING) Integer page,
                                 @RequestParam(value = "page_size", defaultValue = PagingConstant.DEFAULT_PAGE_SIZE_STRING) Integer pageSize, Model model) {
        Pageable pageable = pagingUtil.getPageableFromRequest(page, pageSize, PagingConstant.DEFAULT_SORTING_PAYMENT);
        Page<PaymentDto> paymentPage = getPaymentDtoPage(filterDto, keyword, model, pageable);
        pagingUtil.setAttributesForPagingDisplay(model, pageSize, paymentPage, "/payment/all");
        List<PaymentDto> payments = paymentPage.toList();

        if (payments.isEmpty()) {
            model.addAttribute("message", messageSource.getMessage("msg.error.payments.not.found", null,
                    LocaleContextHolder.getLocale()));
            return PageConstant.PAYMENTS;
        }
        model.addAttribute("payments", payments);
        return PageConstant.PAYMENTS;
    }

    @LogInvocation
    private Page<PaymentDto> getPaymentDtoPage(PaymentFilterDto filterDto, String keyword, Model model, Pageable pageable) {
        Page<PaymentDto> paymentPage;
        if (keyword != null) {
            paymentPage = paymentService.searchForPaymentByKeyword(keyword, pageable);
            model.addAttribute("search", keyword);
        } else if (filterDto.getPaymentMethod() != null || filterDto.getPaymentDate() != null) {
            paymentPage = paymentService.filterPayment(filterDto, pageable);
            model.addAttribute("paymentFilter", filterDto);
        } else {
            paymentPage = paymentService.findAll(pageable);
        }
        return paymentPage;
    }

    @LogInvocation
    @GetMapping("/{id}")
    public String getPayment(@PathVariable Long id, Model model) {
        PaymentDto payment = paymentService.findById(id);
        model.addAttribute("payment", payment);
        return PageConstant.PAYMENT;
    }

    @LogInvocation
    @GetMapping("/register/{id}")
    public String createPaymentForm(@PathVariable("id") Long subscriptionId, Model model) {
        model.addAttribute("subscriptionId", subscriptionId);
        return PageConstant.CREATE_PAYMENT;
    }

    @LogInvocation
    @PostMapping("/register")
    public String createPayment(@RequestParam Long subscriptionId, @RequestParam String paymentTime, @RequestParam String paymentMethodDto, HttpSession session) {
        PaymentDto paymentDto = paymentService.processPaymentRegistration(subscriptionId, paymentTime, paymentMethodDto);
        session.setAttribute("message", messageSource.getMessage("msg.success.payment.registered", null,
                LocaleContextHolder.getLocale()));
        return "redirect:/payment/" + paymentDto.getId();
    }

    @LogInvocation
    @GetMapping("/update/{id}")
    public String updatePaymentForm(@PathVariable Long id, Model model) {
        PaymentDto paymentDto = paymentService.findById(id);
        model.addAttribute("payment", paymentDto);
        return PageConstant.UPDATE_PAYMENT;
    }

    @LogInvocation
    @PostMapping("/update")
    public String updatePayment(@RequestParam Long paymentId, String paymentTime, String paymentMethodDto, HttpSession session) {
        PaymentDto updatedPayment = paymentService.processPaymentUpdate(paymentId, paymentTime, paymentMethodDto);
        session.setAttribute("message", messageSource.getMessage("msg.success.payment.updated", null,
                LocaleContextHolder.getLocale()));
        return "redirect:/payment/" + updatedPayment.getId();
    }

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handlePaymentNotFoundException(PaymentNotFoundException e, Model model) {
        model.addAttribute("message", e.getMessage() + messageSource.getMessage("msg.error.action.payment.not.found", null,
                LocaleContextHolder.getLocale()));
        return PageConstant.ERROR;
    }
}
