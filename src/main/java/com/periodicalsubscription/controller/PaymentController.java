package com.periodicalsubscription.controller;

import com.periodicalsubscription.dto.PaymentDto;
import com.periodicalsubscription.exceptions.payment.PaymentNotFoundException;
import com.periodicalsubscription.manager.ErrorMessageManager;
import com.periodicalsubscription.manager.PageManager;
import com.periodicalsubscription.manager.SuccessMessageManager;
import com.periodicalsubscription.service.api.PaymentService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/all")
    public String getAllPayments(Model model) {
        List<PaymentDto> payments = paymentService.findAll();

        if(payments.isEmpty()) {
            model.addAttribute("message", ErrorMessageManager.PAYMENTS_NOT_FOUND);
            return PageManager.PAYMENTS;
        }
        model.addAttribute("payments", payments);
        return PageManager.PAYMENTS;
    }

    @GetMapping("/{id}")
    public String getPayment(@PathVariable Long id, Model model) {
        PaymentDto payment = paymentService.findById(id);
        model.addAttribute("payment", payment);
        return PageManager.PAYMENT;
    }

    @GetMapping("/register/{id}")
    public String createPaymentForm(@PathVariable("id") Long subscriptionId, Model model) {
        model.addAttribute("subscriptionId", subscriptionId);
        return PageManager.CREATE_PAYMENT;
    }

    @PostMapping("/register")
    public String createPayment(@RequestParam Long subscriptionId, @RequestParam String paymentTime, @RequestParam String paymentMethodDto, HttpSession session) {
        PaymentDto paymentDto = paymentService.processPaymentRegistration(subscriptionId, paymentTime, paymentMethodDto);
        session.setAttribute("message", SuccessMessageManager.PAYMENT_REGISTERED);
        return "redirect:/payment/" + paymentDto.getId();
    }

    @GetMapping("/update/{id}")
    public String updatePaymentForm(@PathVariable Long id, Model model) {
        PaymentDto paymentDto = paymentService.findById(id);
        model.addAttribute("payment", paymentDto);
        return PageManager.UPDATE_PAYMENT;
    }

    @PostMapping("/update")
    public String updatePayment(@RequestParam Long paymentId, String paymentTime, String paymentMethodDto, HttpSession session) {
        PaymentDto updatedPayment = paymentService.processPaymentUpdate(paymentId, paymentTime, paymentMethodDto);
        session.setAttribute("message", SuccessMessageManager.PAYMENT_UPDATED);
        return "redirect:/payment/" + updatedPayment.getId();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handlePaymentNotFoundException(PaymentNotFoundException e, Model model) {
        model.addAttribute("message", e.getMessage() + " Please, enter correct payment id or check payments list.");
        return PageManager.ERROR;
    }
}
