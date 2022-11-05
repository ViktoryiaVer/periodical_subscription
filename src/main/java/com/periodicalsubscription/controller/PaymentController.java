package com.periodicalsubscription.controller;

import com.periodicalsubscription.aspect.logging.annotation.LogInvocation;
import com.periodicalsubscription.controller.util.PagingUtil;
import com.periodicalsubscription.dto.PaymentDto;
import com.periodicalsubscription.exceptions.payment.PaymentNotFoundException;
import com.periodicalsubscription.constant.ErrorMessageConstant;
import com.periodicalsubscription.constant.PageConstant;
import com.periodicalsubscription.constant.SuccessMessageConstant;
import com.periodicalsubscription.service.api.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    @LogInvocation
    @GetMapping("/all")
    public String getAllPayments(@RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(value = "page_size", defaultValue = "10") Integer pageSize, Model model) {
        Page<PaymentDto> paymentPage = paymentService.findAll(pagingUtil.getPageableFromRequest(page, pageSize, "id"));
        pagingUtil.setAttributesForPagingDisplay(model, pageSize, paymentPage, "/payment/all");
        List<PaymentDto> payments = paymentPage.toList();

        if (payments.isEmpty()) {
            model.addAttribute("message", ErrorMessageConstant.PAYMENTS_NOT_FOUND);
            return PageConstant.PAYMENTS;
        }
        model.addAttribute("payments", payments);
        return PageConstant.PAYMENTS;
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
        session.setAttribute("message", SuccessMessageConstant.PAYMENT_REGISTERED);
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
        session.setAttribute("message", SuccessMessageConstant.PAYMENT_UPDATED);
        return "redirect:/payment/" + updatedPayment.getId();
    }

    @LogInvocation
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handlePaymentNotFoundException(PaymentNotFoundException e, Model model) {
        model.addAttribute("message", e.getMessage() + " Please, enter correct payment id or check payments list.");
        return PageConstant.ERROR;
    }
}
